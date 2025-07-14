package yun.edu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import yun.edu.pojo.OrderDetailDTO;

import java.util.List;

@Mapper
public interface OrderExMapper {
    @Select("SELECT " +
            "o.orderTotal, " +
            "o.orderDate, " +
            "b.businessName, " +
            "b.businessImg, " +
            "GROUP_CONCAT(DISTINCT f.foodImg) as foodImgs, " +
            "GROUP_CONCAT(od.quantity) as quantities " +
            "FROM orders o " +
            "LEFT JOIN business b ON o.businessId = b.businessId " +
            "LEFT JOIN orderdetailet od ON o.orderId = od.orderId " +
            "LEFT JOIN food f ON od.foodId = f.foodId " +
            "GROUP BY o.orderId")
    List<OrderDetailDTO> findAllOrderDetails();
}