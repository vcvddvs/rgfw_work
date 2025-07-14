package yun.edu.pojo;

import lombok.Data;

@Data
public class Cart {
    private int cartId;
    private int foodId;
    private int businessId;
    private String userId;
    private int quantity;
}