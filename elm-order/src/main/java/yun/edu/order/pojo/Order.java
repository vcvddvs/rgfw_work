package yun.edu.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderId;
    private String userId;
    private Integer businessId;
    private String orderDate;
    private BigDecimal orderTotal;
    private Integer daId;
    private Integer orderState; // 0：未支付； 1：已支付
    
    // 新增字段，保存商家名称和图片
    private String businessName;
    private String businessImg;
    
    // 关联对象，非数据库字段
    private User user;
    private Business business;
    private DeliveryAddress deliveryAddress;
    private List<OrderDetail> orderDetailList;
    
    // 手动添加getter/setter方法
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
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public BigDecimal getOrderTotal() {
        return orderTotal;
    }
    
    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    public Integer getDaId() {
        return daId;
    }
    
    public void setDaId(Integer daId) {
        this.daId = daId;
    }
    
    public Integer getOrderState() {
        return orderState;
    }
    
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
    
    // 新增字段的getter/setter方法
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Business getBusiness() {
        return business;
    }
    
    public void setBusiness(Business business) {
        this.business = business;
    }
    
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }
    
    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
} 