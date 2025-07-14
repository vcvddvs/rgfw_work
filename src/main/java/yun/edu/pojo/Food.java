// Food.java
package yun.edu.pojo;

import lombok.Data;

@Data
public class Food {
    private int foodId;      // 食品编号
    private String foodName; // 食品名称
    private String foodExplain; // 食品介绍
    private String foodImg; // 食品图片
    private double foodPrice; // 食品价格
    private int businessId; // 所属商家编号
    private String remarks; // 备注
}