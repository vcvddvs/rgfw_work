package yun.edu.service.impl;

import org.springframework.stereotype.Service;
import yun.edu.mapper.OrderDetailMapper;
import yun.edu.pojo.OrderDetail;
import yun.edu.service.OrderDetailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailMapper orderDetailMapper;

    @Override
    public void createOrderDetail(OrderDetail orderDetail) {
        orderDetailMapper.insertOrderDetail(orderDetail);
    }
}