package yun.edu.order.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Food {
    private Integer foodId;
    private String foodName;
    private String foodExplain;
    private String foodImg;
    private BigDecimal foodPrice;
    private Integer businessId;
    private String remarks;
    
    // Explicit getter and setter methods
    public Integer getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    
    public String getFoodName() {
        return foodName;
    }
    
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    
    public String getFoodExplain() {
        return foodExplain;
    }
    
    public void setFoodExplain(String foodExplain) {
        this.foodExplain = foodExplain;
    }
    
    public String getFoodImg() {
        return foodImg;
    }
    
    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }
    
    public BigDecimal getFoodPrice() {
        return foodPrice;
    }
    
    public void setFoodPrice(BigDecimal foodPrice) {
        this.foodPrice = foodPrice;
    }
    
    public Integer getBusinessId() {
        return businessId;
    }
    
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
} 