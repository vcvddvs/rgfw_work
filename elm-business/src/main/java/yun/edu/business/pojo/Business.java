package yun.edu.business.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Business {
    private int businessId;        
    private String businessName;   
    private String businessAddress;
    private String businessExplain;
    private String businessImg; // 商家图片，Base64编码的字符串或URL
    private int orderTypeId;
    private double starPrice;
    private double deliveryPrice;
    private String remarks;
    private double distance; // 添加距离字段
    
    // 显式添加getter和setter方法
    public int getBusinessId() {
        return businessId;
    }
    
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
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
} 