package yun.edu.order.service;

import yun.edu.order.pojo.Order;
import yun.edu.order.pojo.OrderDetail;
import yun.edu.order.pojo.OrderRequest;
import yun.edu.order.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {
    
    Order createOrder(Order order);
    
    void createOrderWithDetails(OrderRequest orderRequest);
    
    List<Order> getOrdersByUserId(String userId);
    
    Order getOrderById(Integer orderId);
    
    List<Order> listOrdersByUserId(String userId);
    
    int updateOrderState(Integer orderId, Integer orderState);
    
    List<OrderDetail> listOrderDetailByOrderId(Integer orderId);
    
    List<OrderDetailDTO> findAllOrderDetails();
} 