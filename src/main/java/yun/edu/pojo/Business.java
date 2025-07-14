package yun.edu.pojo;

import lombok.Data;

@Data
public class Business {
    private int businessId;
    private String businessName;
    private String businessAddress;
    private String businessExplain;
    private String businessImg; // 假设这里是Base64编码的字符串
    private int orderTypeId;
    private double starPrice;
    private double deliveryPrice;
    private String remarks;
}