package yun.edu.order.client.fallback;

import org.springframework.stereotype.Component;
import yun.edu.common.pojo.Result;
import yun.edu.order.client.DeliveryClient;
import yun.edu.order.pojo.DeliveryAddress;

@Component("clientDeliveryClientFallback")
public class DeliveryClientFallback implements DeliveryClient {
    
    @Override
    public Result<DeliveryAddress> getDeliveryAddressById(Integer daId) {
        // 返回默认配送地址信息
        DeliveryAddress address = new DeliveryAddress();
        address.setDaId(daId);
        address.setContactName("默认联系人");
        address.setAddress("服务暂时不可用，无法获取详细地址");
        return Result.success(address);
    }
} 