package yun.edu.order.client.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import yun.edu.common.pojo.Result;
import yun.edu.order.client.BusinessClient;
import yun.edu.order.pojo.Business;
import yun.edu.order.pojo.Food;

import java.math.BigDecimal;

@Component("clientBusinessClientFallback")
public class BusinessClientFallback implements BusinessClient {
    
    @Override
    public ResponseEntity<Result<Business>> getBusinessById(Integer businessId) {
        // 返回默认商家信息
        Business business = new Business();
        business.setBusinessId(businessId);
        business.setBusinessName("默认商家");
        business.setBusinessAddress("服务暂时不可用，无法获取详细地址");
        business.setStarPrice(new BigDecimal("0.00"));
        business.setDeliveryPrice(new BigDecimal("0.00"));
        
        Result<Business> result = Result.success(business);
        return ResponseEntity.ok(result);
    }
    
    @Override
    public ResponseEntity<Result<Food>> getFoodById(Integer foodId) {
        // 返回默认食品信息
        Food food = new Food();
        food.setFoodId(foodId);
        food.setFoodName("默认食品");
        food.setFoodExplain("服务暂时不可用，无法获取详细信息");
        food.setFoodImg("/assets/img/default-food.png");
        food.setFoodPrice(new BigDecimal("0.00"));
        
        Result<Food> result = Result.success(food);
        return ResponseEntity.ok(result);
    }
} 