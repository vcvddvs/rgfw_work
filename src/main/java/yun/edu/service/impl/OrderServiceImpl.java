package yun.edu.service.impl;

import org.springframework.stereotype.Service;
import yun.edu.mapper.OrderMapper;
import yun.edu.pojo.Order;
import yun.edu.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public void createOrder(Order order) {
        orderMapper.insertOrder(order);
    }
}