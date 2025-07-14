package yun.edu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yun.edu.common.Result;
import yun.edu.order.feign.fallback.PaymentClientFallback;

import java.util.Map;

@FeignClient(value = "elm-payment", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    
    @GetMapping("/payment/order/{orderId}")
    Result getPaymentByOrderId(@PathVariable("orderId") Integer orderId);
    
    @PostMapping("/payment/process")
    Result processPayment(@RequestBody Map<String, Object> paymentInfo);
} 