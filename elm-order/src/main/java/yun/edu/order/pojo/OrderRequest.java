package yun.edu.order.pojo;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Order order;
    private List<OrderDetail> details;
    
    // 手动添加getter/setter方法
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public List<OrderDetail> getDetails() {
        return details;
    }
    
    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
} 