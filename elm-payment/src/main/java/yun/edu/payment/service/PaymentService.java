package yun.edu.payment.service;

import yun.edu.payment.pojo.Payment;

import java.util.List;

public interface PaymentService {
    
    Payment getPaymentByOrderId(Integer orderId);
    
    Payment getPaymentById(Integer paymentId);
    
    int createPayment(Payment payment);
    
    int updatePaymentStatus(Payment payment);
    
    List<Payment> getPaymentsByStatus(String status);
    
    // 处理支付请求
    Payment processPayment(Integer orderId, String paymentMethod);
    
    // 验证支付状态
    boolean verifyPayment(Integer paymentId);
} 