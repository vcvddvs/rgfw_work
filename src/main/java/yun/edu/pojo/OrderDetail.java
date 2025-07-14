package yun.edu.pojo;

import lombok.Data;

@Data
public class OrderDetail {
    private Integer odId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;
    
    // 新增字段，保存食品名称、图片和价格
    private String foodName;
    private String foodImg;
    private Double price;
}
