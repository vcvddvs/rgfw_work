// OrderRequest.java
package yun.edu.pojo;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Order order;
    private List<OrderDetail> details;
}