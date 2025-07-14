package yun.edu.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.order.mapper.OrderDetailMapper;
import yun.edu.order.pojo.OrderDetail;
import yun.edu.order.service.OrderDetailService;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailMapper orderDetailMapper;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public int createOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.saveOrderDetail(orderDetail);
    }
    
    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailMapper.listOrderDetailsByOrderId(orderId);
    }
} 