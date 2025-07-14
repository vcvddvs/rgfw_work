package yun.edu.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer cartId;
    private Integer foodId;
    private Integer businessId;
    private String userId;
    private Integer quantity;
    private String orderId;
    
    // 手动添加getter/setter方法
    public Integer getCartId() {
        return cartId;
    }
    
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    
    public Integer getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    
    public Integer getBusinessId() {
        return businessId;
    }
    
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
} 