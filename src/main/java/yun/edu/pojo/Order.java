package yun.edu.pojo;

import lombok.Data;

@Data
public class Order {
    private Integer orderId;
    private String userId;
    private Integer businessId;
    private String orderDate;
    private Double orderTotal;
    private Integer daId;
    private Integer orderState;
    
    // 新增字段，保存商家名称和图片
    private String businessName;
    private String businessImg;
}