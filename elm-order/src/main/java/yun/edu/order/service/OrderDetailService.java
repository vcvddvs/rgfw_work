package yun.edu.order.service;

import yun.edu.order.pojo.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    
    int createOrderDetail(OrderDetail orderDetail);
    
    List<OrderDetail> getOrderDetailsByOrderId(Integer orderId);
} 