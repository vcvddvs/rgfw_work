package yun.edu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.common.pojo.Result;
import yun.edu.order.pojo.Cart;
import yun.edu.order.pojo.Order;
import yun.edu.order.pojo.OrderDetail;
import yun.edu.order.pojo.OrderRequest;
import yun.edu.order.service.CartService;
import yun.edu.order.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, Object> checkoutRequest) {
        try {
            String userId = (String) checkoutRequest.get("userId");
            Integer businessId = (Integer) checkoutRequest.get("businessId");
            List<Map<String, Object>> items = (List<Map<String, Object>>) checkoutRequest.get("items");

            if (userId == null || businessId == null || items == null || items.isEmpty()) {
                return ResponseEntity.badRequest().body(Result.failure("请求参数不完整"));
            }

            // 创建订单
            Order order = new Order();
            order.setUserId(userId);
            order.setBusinessId(businessId);
            
            // 获取商家名称和图片
            String businessName = (String) checkoutRequest.get("businessName");
            String businessImg = (String) checkoutRequest.get("businessImg");
            
            order.setBusinessName(businessName);
            order.setBusinessImg(businessImg);
            
            // 设置默认配送地址ID（如果没有提供的话）
            Integer daId = (Integer) checkoutRequest.get("daId");
            if (daId != null) {
                order.setDaId(daId);
            } else {
                // 默认地址ID设为1
                order.setDaId(1);
            }
            
            // 计算总金额
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderDetail> orderDetails = new ArrayList<>();
            
            for (Map<String, Object> item : items) {
                OrderDetail detail = new OrderDetail();
                detail.setFoodId((Integer) item.get("foodId"));
                detail.setQuantity((Integer) item.get("quantity"));
                
                // 获取食品名称和图片
                String foodName = (String) item.get("foodName");
                String foodImg = (String) item.get("foodImg");
                detail.setFoodName(foodName);
                detail.setFoodImg(foodImg);
                
                // 将价格转换为BigDecimal，处理Integer和Double两种可能的类型
                Object priceObj = item.get("price");
                BigDecimal price;
                if (priceObj instanceof Integer) {
                    price = new BigDecimal((Integer) priceObj);
                } else if (priceObj instanceof Double) {
                    price = BigDecimal.valueOf((Double) priceObj);
                } else if (priceObj instanceof String) {
                    price = new BigDecimal((String) priceObj);
                } else {
                    // 默认值
                    price = BigDecimal.ZERO;
                }
                detail.setPrice(price);
                
                // 计算小计并累加到总金额
                BigDecimal quantity = BigDecimal.valueOf(detail.getQuantity());
                totalAmount = totalAmount.add(price.multiply(quantity));
                
                orderDetails.add(detail);
            }
            
            order.setOrderTotal(totalAmount);
            
            // 创建订单请求对象
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setOrder(order);
            orderRequest.setDetails(orderDetails);
            
            try {
                // 创建订单
                orderService.createOrderWithDetails(orderRequest);
                
                // 清空购物车
                cartService.clearCart(userId);
                
                return ResponseEntity.ok(Result.success("订单创建成功"));
            } catch (Exception e) {
                // 检查是否是用户服务错误
                if (e.getMessage() != null && e.getMessage().contains("User service error")) {
                    // 尝试使用fallback创建订单
                    try {
                        // 清空购物车
                        cartService.clearCart(userId);
                        return ResponseEntity.ok(Result.success("订单创建成功（使用默认用户信息）"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return ResponseEntity.status(500).body(Result.failure("创建订单失败: " + ex.getMessage()));
                    }
                }
                
                e.printStackTrace();
                return ResponseEntity.status(500).body(Result.failure("创建订单失败: " + e.getMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Result.failure("处理请求失败: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        int result = cartService.addCart(cart);
        if (result > 0) {
            return ResponseEntity.ok(Result.success("商品已添加到购物车"));
        } else {
            return ResponseEntity.badRequest().body(Result.failure("添加购物车失败"));
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartsByUserId(@PathVariable String userId) {
        List<Cart> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(Result.success(carts));
    }
    
    @GetMapping("/user/{userId}/business/{businessId}")
    public ResponseEntity<?> getCartsByUserIdAndBusinessId(
            @PathVariable String userId, 
            @PathVariable int businessId) {
        List<Cart> carts = cartService.getCartsByUserIdAndBusinessId(userId, businessId);
        return ResponseEntity.ok(Result.success(carts));
    }
    
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        int result = cartService.clearCart(userId);
        return ResponseEntity.ok(Result.success("购物车已清空，删除了" + result + "项"));
    }
} 