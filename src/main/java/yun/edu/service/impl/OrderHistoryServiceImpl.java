package yun.edu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import yun.edu.pojo.OrderDetailDTO;
import yun.edu.mapper.OrderExMapper;
import yun.edu.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private static final Logger log = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);
    private final OrderExMapper orderExMapper;

    @Override
    public List<OrderDetailDTO> findAllOrderDetails() {
        try {
            log.info("Attempting to fetch order details from database");
            List<OrderDetailDTO> orderDetails = orderExMapper.findAllOrderDetails();
            log.info("Successfully fetched {} order details", orderDetails.size());
            return orderDetails;
        } catch (Exception e) {
            log.error("Error fetching order details from database", e);
            // Return empty list as a fallback
            return new ArrayList<>();
        }
    }
}