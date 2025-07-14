package yun.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.pojo.Order;
import yun.edu.pojo.OrderDetail;
import yun.edu.pojo.OrderRequest;
import yun.edu.service.OrderDetailService;
import yun.edu.service.OrderService;
import yun.edu.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("elm_servlet_war_exploded/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final DeliveryAddressService deliveryAddressService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        // 打印请求信息以调试
        System.out.println("Received request: " + request);
        Order order = request.getOrder();
        List<OrderDetail> details = request.getDetails();

        // 检查并设置配送地址
        if (order.getDaId() == null) {
            // 如果没有提供配送地址，返回错误
            return ResponseEntity.badRequest().body("配送地址不能为空");
        }

        // 设置订单状态为1（新创建）
        order.setOrderState(1);

        // 创建订单，数据库会自动生成 orderId
        orderService.createOrder(order);

        // 使用生成的 orderId 创建订单明细
        Integer generatedOrderId = order.getOrderId();
        for (OrderDetail detail : details) {
            detail.setOrderId(generatedOrderId);
            orderDetailService.createOrderDetail(detail);
        }
        
        return ResponseEntity.ok("Order created successfully");
    }
}