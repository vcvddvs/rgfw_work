package yun.edu.service;

import yun.edu.pojo.OrderDetailDTO;

import java.util.List;

public interface OrderHistoryService {
    List<OrderDetailDTO> findAllOrderDetails();
}