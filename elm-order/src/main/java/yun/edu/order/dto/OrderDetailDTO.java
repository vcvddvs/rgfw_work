package yun.edu.order.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
public class OrderDetailDTO {
    private Integer orderId;
    private String businessName;
    private String businessImg;
    private String foodImgs; // This will hold the comma-separated string from the database
    private String foodNames; // This will hold the comma-separated string of food names
    private String quantities; // This will hold the comma-separated string from the database
    private double orderTotal;
    private String orderDate;
    private BigDecimal deliveryPrice = BigDecimal.ZERO; // Default value for display
    
    // Explicit getter and setter methods
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getBusinessImg() {
        return businessImg;
    }
    
    public void setBusinessImg(String businessImg) {
        this.businessImg = businessImg;
    }
    
    public String getFoodImgs() {
        return foodImgs;
    }
    
    public void setFoodImgs(String foodImgs) {
        this.foodImgs = foodImgs;
    }
    
    public String getFoodNames() {
        return foodNames;
    }
    
    public void setFoodNames(String foodNames) {
        this.foodNames = foodNames;
    }
    
    public String getQuantities() {
        return quantities;
    }
    
    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }
    
    public double getOrderTotal() {
        return orderTotal;
    }
    
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }
    
    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
    
    // Helper methods to convert the comma-separated strings to lists for the front-end
    public List<String> getFoodImgsList() {
        if (foodImgs == null || foodImgs.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(foodImgs.split(","));
    }
    
    public List<String> getFoodNamesList() {
        if (foodNames == null || foodNames.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(foodNames.split(","));
    }
    
    public List<Integer> getQuantitiesList() {
        if (quantities == null || quantities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        for (String quantity : quantities.split(",")) {
            try {
                result.add(Integer.parseInt(quantity.trim()));
            } catch (NumberFormatException e) {
                // Skip invalid numbers
            }
        }
        return result;
    }
} 