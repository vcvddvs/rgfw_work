package yun.edu.order.feign.fallback;

import org.springframework.stereotype.Component;
import yun.edu.common.Result;
import yun.edu.order.feign.PaymentClient;

import java.util.Map;

@Component("feignPaymentClientFallback")
public class PaymentClientFallback implements PaymentClient {
    
    @Override
    public Result getPaymentByOrderId(Integer orderId) {
        return Result.fail("支付服务不可用，请稍后再试");
    }
    
    @Override
    public Result processPayment(Map<String, Object> paymentInfo) {
        return Result.fail("支付服务不可用，请稍后再试");
    }
} 