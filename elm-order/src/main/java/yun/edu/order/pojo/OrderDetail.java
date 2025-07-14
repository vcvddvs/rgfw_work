package yun.edu.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Integer odId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;
    private BigDecimal price;
    
    // 新增字段，保存食品名称和图片
    private String foodName;
    private String foodImg;
    
    // 手动添加getter/setter方法
    public Integer getOdId() {
        return odId;
    }
    
    public void setOdId(Integer odId) {
        this.odId = odId;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    // 新增字段的getter/setter方法
    public String getFoodName() {
        return foodName;
    }
    
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    
    public String getFoodImg() {
        return foodImg;
    }
    
    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }
} 