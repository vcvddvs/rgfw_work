package yun.edu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.common.pojo.Result;
import yun.edu.order.pojo.Order;
import yun.edu.order.pojo.OrderDetail;
import yun.edu.order.pojo.OrderRequest;
import yun.edu.order.dto.OrderDetailDTO;
import yun.edu.order.service.OrderDetailService;
import yun.edu.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.createOrderWithDetails(orderRequest);
            return ResponseEntity.ok(Result.success("订单创建成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.failure("订单创建失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(Result.success(orders));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(Result.success(order));
        } else {
            return ResponseEntity.status(404).body(Result.failure("订单不存在"));
        }
    }
    
    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Integer orderId) {
        List<OrderDetail> details = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(Result.success(details));
    }

    @GetMapping("/detail/{orderId}")
    public List<OrderDetail> listOrderDetailByOrderId(@PathVariable Integer orderId) {
        return orderService.listOrderDetailByOrderId(orderId);
    }
    
    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory() {
        try {
            List<OrderDetailDTO> orderDetails = orderService.findAllOrderDetails();
            return ResponseEntity.ok(Result.success(orderDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.failure("获取订单历史失败: " + e.getMessage()));
        }
    }
} 