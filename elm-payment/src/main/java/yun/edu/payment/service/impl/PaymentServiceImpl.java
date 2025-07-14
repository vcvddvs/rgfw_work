package yun.edu.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.payment.mapper.PaymentMapper;
import yun.edu.payment.pojo.Payment;
import yun.edu.payment.service.PaymentService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentMapper paymentMapper;
    
    @Override
    public Payment getPaymentByOrderId(Integer orderId) {
        return paymentMapper.getPaymentByOrderId(orderId);
    }
    
    @Override
    public Payment getPaymentById(Integer paymentId) {
        return paymentMapper.getPaymentById(paymentId);
    }
    
    @Override
    public int createPayment(Payment payment) {
        payment.setCreateTime(new Date());
        payment.setPaymentStatus("PENDING");
        return paymentMapper.createPayment(payment);
    }
    
    @Override
    public int updatePaymentStatus(Payment payment) {
        return paymentMapper.updatePaymentStatus(payment);
    }
    
    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentMapper.getPaymentsByStatus(status);
    }
    
    @Override
    public Payment processPayment(Integer orderId, String paymentMethod) {
        // 这里应该调用订单服务获取订单信息，包括金额
        // 简化处理，使用模拟金额
        BigDecimal amount = new BigDecimal("100.00");
        
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        payment.setCreateTime(new Date());
        
        paymentMapper.createPayment(payment);
        
        // 模拟支付处理
        simulatePaymentProcessing(payment);
        
        return payment;
    }
    
    @Override
    public boolean verifyPayment(Integer paymentId) {
        Payment payment = paymentMapper.getPaymentById(paymentId);
        return payment != null && "COMPLETED".equals(payment.getPaymentStatus());
    }
    
    // 模拟支付处理
    private void simulatePaymentProcessing(Payment payment) {
        // 实际应用中，这里会调用第三方支付API
        // 简化处理，直接更新状态
        payment.setPaymentStatus("COMPLETED");
        payment.setPaymentTime(new Date());
        payment.setTransactionId(UUID.randomUUID().toString());
        
        paymentMapper.updatePaymentStatus(payment);
    }
} 