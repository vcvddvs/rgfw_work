package yun.edu.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yun.edu.payment.pojo.Payment;
import yun.edu.payment.service.PaymentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable Integer orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }
    
    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable Integer paymentId) {
        return paymentService.getPaymentById(paymentId);
    }
    
    @PostMapping("/")
    public int createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }
    
    @PutMapping("/")
    public int updatePaymentStatus(@RequestBody Payment payment) {
        return paymentService.updatePaymentStatus(payment);
    }
    
    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable String status) {
        return paymentService.getPaymentsByStatus(status);
    }
    
    @PostMapping("/process")
    public Payment processPayment(@RequestBody Map<String, Object> paymentInfo) {
        Integer orderId = (Integer) paymentInfo.get("orderId");
        String paymentMethod = (String) paymentInfo.get("paymentMethod");
        return paymentService.processPayment(orderId, paymentMethod);
    }
    
    @GetMapping("/verify/{paymentId}")
    public boolean verifyPayment(@PathVariable Integer paymentId) {
        return paymentService.verifyPayment(paymentId);
    }
} 