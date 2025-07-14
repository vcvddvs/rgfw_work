package yun.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import yun.edu.pojo.OrderDetailDTO;
import yun.edu.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/elm_servlet_war_exploded/api")
public class OrderHistoryController {

    private static final Logger log = LoggerFactory.getLogger(OrderHistoryController.class);
    private final OrderHistoryService orderHistoryService;

    @GetMapping("/order-history")
    public ResponseEntity<?> getOrderHistory() {
        try {
            log.info("Fetching order history");
            List<OrderDetailDTO> orderDetails = orderHistoryService.findAllOrderDetails();
            log.info("Received order history data: {} items", orderDetails.size());
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            log.error("Error fetching order history", e);
            return ResponseEntity.status(500).body("Error fetching order history: " + e.getMessage());
        }
    }
}