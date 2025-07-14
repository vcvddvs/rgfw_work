package yun.edu.order.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.order.pojo.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    @Insert("insert into orders(userId,businessId,orderDate,orderTotal,daId,orderState,businessName,businessImg) " +
            "values(#{userId},#{businessId},#{orderDate},#{orderTotal},#{daId},#{orderState},#{businessName},#{businessImg})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int saveOrder(Order order);
    
    @Select("select * from orders where userId=#{userId}")
    List<Order> listOrdersByUserId(String userId);
    
    @Select("select * from orders where userId=#{userId}")
    List<Order> getOrdersByUserId(String userId);
    
    @Select("select * from orders where orderId=#{orderId}")
    Order getOrderById(Integer orderId);
    
    @Update("update orders set orderState=#{orderState} where orderId=#{orderId}")
    int updateOrderState(Order order);
    
    @Select("select * from orders order by orderDate desc")
    List<Order> getAllOrders();
} 