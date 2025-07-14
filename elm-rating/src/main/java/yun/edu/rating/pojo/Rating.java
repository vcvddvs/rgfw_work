package yun.edu.rating.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private Integer ratingId;
    private Integer orderId;
    private String userId;
    private Integer businessId;
    private Integer foodId;
    private Integer deliveryRating; // 配送评分1-5
    private Integer foodRating; // 食品评分1-5
    private String content; // 评价内容
    private Date createTime;
    private Integer status; // 0-待审核，1-已通过，2-已拒绝
    
    // 手动添加getter/setter方法
    public Integer getRatingId() {
        return ratingId;
    }
    
    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Integer getBusinessId() {
        return businessId;
    }
    
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    
    public Integer getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    
    public Integer getDeliveryRating() {
        return deliveryRating;
    }
    
    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }
    
    public Integer getFoodRating() {
        return foodRating;
    }
    
    public void setFoodRating(Integer foodRating) {
        this.foodRating = foodRating;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
} 