package yun.edu.order.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.edu.common.exception.ServiceException;
import yun.edu.common.pojo.Result;
import yun.edu.order.client.BusinessClient;
import yun.edu.order.client.DeliveryClient;
import yun.edu.order.client.UserClient;
import yun.edu.order.mapper.CartMapper;
import yun.edu.order.mapper.OrderDetailMapper;
import yun.edu.order.mapper.OrderMapper;
import yun.edu.order.pojo.*;
import yun.edu.order.dto.OrderDetailDTO;
import yun.edu.order.service.OrderDetailService;
import yun.edu.order.service.OrderService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderDetailService orderDetailService;
    private final OrderDetailMapper orderDetailMapper;
    private final CartMapper cartMapper;
    private final UserClient userClient;
    private final BusinessClient businessClient;
    private final DeliveryClient deliveryClient;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailService orderDetailService, OrderDetailMapper orderDetailMapper, CartMapper cartMapper, UserClient userClient, BusinessClient businessClient, DeliveryClient deliveryClient) {
        this.orderMapper = orderMapper;
        this.orderDetailService = orderDetailService;
        this.orderDetailMapper = orderDetailMapper;
        this.cartMapper = cartMapper;
        this.userClient = userClient;
        this.businessClient = businessClient;
        this.deliveryClient = deliveryClient;
    }

    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "getOrderByIdFallback")
    public Order getOrderById(Integer orderId) {
        Order order = orderMapper.getOrderById(orderId);
        if (order != null) {
            // 获取订单明细
            List<OrderDetail> detailList = orderDetailMapper.listDetailsByOrderId(orderId);
            order.setOrderDetailList(detailList);
            
            // 获取商家信息
            ResponseEntity<Result<Business>> businessResponse = businessClient.getBusinessById(order.getBusinessId());
            if (businessResponse != null && businessResponse.getBody() != null && businessResponse.getBody().getData() != null) {
                order.setBusiness(businessResponse.getBody().getData());
            }
            
            // 获取配送地址
            Result<DeliveryAddress> deliveryAddressResult = deliveryClient.getDeliveryAddressById(order.getDaId());
            if (deliveryAddressResult != null && deliveryAddressResult.getData() != null) {
                order.setDeliveryAddress(deliveryAddressResult.getData());
            }
            
            // 获取用户信息
            ResponseEntity<User> userResponse = userClient.getUserById(order.getUserId());
            if (userResponse != null && userResponse.getBody() != null) {
                order.setUser(userResponse.getBody());
            }
        }
        return order;
    }

    public Order getOrderByIdFallback(Integer orderId, Exception e) {
        // 降级处理
        Order fallbackOrder = new Order();
        fallbackOrder.setOrderId(orderId);
        fallbackOrder.setOrderState(0);
        fallbackOrder.setOrderTotal(null);
        return fallbackOrder;
    }

    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "listOrdersByUserIdFallback")
    public List<Order> listOrdersByUserId(String userId) {
        List<Order> orderList = orderMapper.listOrdersByUserId(userId);
        // 对每个订单，获取商家信息和配送地址信息
        for (Order order : orderList) {
            ResponseEntity<Result<Business>> businessResponse = businessClient.getBusinessById(order.getBusinessId());
            if (businessResponse != null && businessResponse.getBody() != null && businessResponse.getBody().getData() != null) {
                order.setBusiness(businessResponse.getBody().getData());
            }
            
            Result<DeliveryAddress> deliveryAddressResult = deliveryClient.getDeliveryAddressById(order.getDaId());
            if (deliveryAddressResult != null && deliveryAddressResult.getData() != null) {
                order.setDeliveryAddress(deliveryAddressResult.getData());
            }
        }
        return orderList;
    }

    public List<Order> listOrdersByUserIdFallback(String userId, Exception e) {
        // 降级处理
        return new ArrayList<>();
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "orderService", fallbackMethod = "createOrderFallback")
    @Retry(name = "orderService")
    public Order createOrder(Order order) {
        try {
        // 验证用户是否存在
        ResponseEntity<User> userResponse = userClient.getUserById(order.getUserId());
        if (userResponse == null || userResponse.getBody() == null) {
            throw new ServiceException("用户不存在");
        }
        User user = userResponse.getBody();
        
        // 验证商家是否存在
        ResponseEntity<Result<Business>> businessResponse = businessClient.getBusinessById(order.getBusinessId());
        if (businessResponse == null || businessResponse.getBody() == null || businessResponse.getBody().getData() == null) {
            throw new ServiceException("商家不存在");
        }
        Business business = businessResponse.getBody().getData();
        
        // 验证配送地址是否存在
        if (order.getDaId() != null) {
            Result<DeliveryAddress> addressResult = deliveryClient.getDeliveryAddressById(order.getDaId());
            if (addressResult == null || addressResult.getData() == null) {
                throw new ServiceException("配送地址不存在");
            }
        }
        
        // 设置订单时间和状态
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setOrderState(0); // 未支付
        
        // 保存订单
        orderMapper.saveOrder(order);
        
        // 处理订单明细
        if (order.getOrderDetailList() != null && !order.getOrderDetailList().isEmpty()) {
            for (OrderDetail detail : order.getOrderDetailList()) {
                detail.setOrderId(order.getOrderId());
                orderDetailMapper.saveOrderDetail(detail);
                
                // 从购物车中删除已下单的商品
                cartMapper.removeCartByUserIdAndFoodId(order.getUserId(), detail.getFoodId());
            }
        }
        
        return order;
        } catch (RuntimeException e) {
            // 检查是否是我们自定义的User服务错误
            if (e instanceof feign.FeignException && e.getMessage().contains("500") && e.getMessage().contains("UserClient")) {
                // 使用默认用户信息
                User defaultUser = new User();
                defaultUser.setUserId(order.getUserId());
                defaultUser.setUserName("临时用户");
                
                // 设置订单时间和状态
                order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                order.setOrderState(0); // 未支付
                
                // 保存订单
                orderMapper.saveOrder(order);
                
                // 处理订单明细
                if (order.getOrderDetailList() != null && !order.getOrderDetailList().isEmpty()) {
                    for (OrderDetail detail : order.getOrderDetailList()) {
                        detail.setOrderId(order.getOrderId());
                        orderDetailMapper.saveOrderDetail(detail);
                        
                        // 从购物车中删除已下单的商品
                        cartMapper.removeCartByUserIdAndFoodId(order.getUserId(), detail.getFoodId());
                    }
                }
                
                return order;
            } else if (e.getMessage() != null && e.getMessage().contains("User service error, using fallback")) {
                // 这是我们预期的错误，使用fallback处理
                // 设置订单时间和状态
                order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                order.setOrderState(0); // 未支付
                
                // 保存订单
                orderMapper.saveOrder(order);
                
                return order;
            }
            
            throw e; // 重新抛出其他类型的异常
        }
    }

    public Order createOrderFallback(Order order, Exception e) {
        // 降级处理
        throw new ServiceException("创建订单失败，请稍后重试");
    }

    @Override
    @Transactional
    public int updateOrderState(Integer orderId, Integer orderState) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(orderState);
        return orderMapper.updateOrderState(order);
    }

    @Override
    @Transactional
    public void createOrderWithDetails(OrderRequest orderRequest) {
        try {
        Order order = orderRequest.getOrder();
        List<OrderDetail> details = orderRequest.getDetails();
        
        // 创建订单
        createOrder(order);
        
        // 使用生成的orderId创建订单明细
        Integer generatedOrderId = order.getOrderId();
            if (generatedOrderId == null) {
                throw new ServiceException("订单创建失败，未生成订单ID");
            }
            
        for (OrderDetail detail : details) {
            detail.setOrderId(generatedOrderId);
            orderDetailService.createOrderDetail(detail);
            }
        } catch (RuntimeException e) {
            // 检查是否是我们自定义的User服务错误
            if (e.getMessage() != null && e.getMessage().contains("User service error, using fallback")) {
                // 这是我们预期的错误，使用fallback处理，不需要重新抛出
                // 记录日志但继续执行
                e.printStackTrace();
                
                // 不抛出异常，让订单继续创建
                return;
            }
            
            // 记录异常
            e.printStackTrace();
            // 抛出业务异常，让上层处理
            throw new ServiceException("创建订单失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderMapper.getOrdersByUserId(userId);
    }

    @Override
    public List<OrderDetail> listOrderDetailByOrderId(Integer orderId) {
        return orderDetailMapper.listDetailsByOrderId(orderId);
    }
    
    @Override
    public List<OrderDetailDTO> findAllOrderDetails() {
        try {
            // 获取所有订单
            List<Order> allOrders = orderMapper.getAllOrders();
            List<OrderDetailDTO> result = new ArrayList<>();
            
            // 处理每个订单
            for (Order order : allOrders) {
                // 获取订单的所有明细
                List<OrderDetail> details = orderDetailMapper.listDetailsByOrderId(order.getOrderId());
                
                // 创建DTO对象
                OrderDetailDTO dto = new OrderDetailDTO();
                dto.setOrderId(order.getOrderId());
                dto.setOrderTotal(order.getOrderTotal() != null ? order.getOrderTotal().doubleValue() : 0);
                dto.setOrderDate(order.getOrderDate());
                
                // 直接使用订单表中存储的商家信息
                dto.setBusinessName(order.getBusinessName());
                dto.setBusinessImg(order.getBusinessImg());
                
                // 如果订单表中没有商家信息，尝试从商家服务获取
                if ((dto.getBusinessName() == null || dto.getBusinessImg() == null) && order.getBusinessId() != null) {
                    try {
                        ResponseEntity<Result<Business>> businessResponse = businessClient.getBusinessById(order.getBusinessId());
                        if (businessResponse != null && businessResponse.getBody() != null && businessResponse.getBody().getData() != null) {
                            Business business = businessResponse.getBody().getData();
                            
                            // 只有在订单表中没有数据时才使用商家服务的数据
                            if (dto.getBusinessName() == null) {
                                dto.setBusinessName(business.getBusinessName());
                            }
                            
                            if (dto.getBusinessImg() == null) {
                                dto.setBusinessImg(business.getBusinessImg());
                            }
                            
                            dto.setDeliveryPrice(business.getDeliveryPrice());
                        }
                    } catch (Exception e) {
                        // 如果获取商家信息失败，记录错误但继续处理
                        e.printStackTrace();
                    }
                }
                
                // 收集食品图片、名称和数量
                List<String> foodImagesList = new ArrayList<>();
                List<String> foodNamesList = new ArrayList<>();
                List<String> quantitiesList = new ArrayList<>();
                
                // 处理每个订单明细
                for (OrderDetail detail : details) {
                    // 使用订单明细表中存储的食品名称和图片
                    String foodName = detail.getFoodName();
                    String foodImg = detail.getFoodImg();
                    
                    // 如果订单明细表中没有数据，尝试从食品服务获取
                    if ((foodName == null || foodImg == null) && detail.getFoodId() != null) {
                        try {
                            ResponseEntity<Result<Food>> foodResponse = businessClient.getFoodById(detail.getFoodId());
                            if (foodResponse != null && foodResponse.getBody() != null && foodResponse.getBody().getData() != null) {
                                Food food = foodResponse.getBody().getData();
                                
                                if (foodName == null) {
                                    foodName = food.getFoodName();
                                }
                                
                                if (foodImg == null) {
                                    foodImg = food.getFoodImg();
                                }
                            }
                        } catch (Exception e) {
                            // 如果获取食品信息失败，记录错误但继续处理
                            e.printStackTrace();
                        }
                    }
                    
                    // 添加食品名称（如果仍然为空，使用默认值）
                    foodNamesList.add(foodName != null ? foodName : "未知食品");
                    
                    // 添加食品图片（如果仍然为空，使用默认值）
                    foodImagesList.add(foodImg != null ? foodImg : "/assets/img/sp01.png");
                    
                    // 添加数量
                    quantitiesList.add(String.valueOf(detail.getQuantity()));
                }
                
                // 将列表转换为逗号分隔的字符串
                dto.setFoodImgs(String.join(",", foodImagesList));
                dto.setFoodNames(String.join(",", foodNamesList));
                dto.setQuantities(String.join(",", quantitiesList));
                
                result.add(dto);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
} 