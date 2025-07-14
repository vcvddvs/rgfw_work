package yun.edu.order.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.order.pojo.OrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    
    @Select("select * from orderdetailet where orderId=#{orderId}")
    List<OrderDetail> listOrderDetailsByOrderId(Integer orderId);
    
    @Select("select * from orderdetailet where orderId=#{orderId}")
    List<OrderDetail> listDetailsByOrderId(Integer orderId);
    
    @Insert("insert into orderdetailet(orderId,foodId,quantity,price,foodName,foodImg) values(#{orderId},#{foodId},#{quantity},#{price},#{foodName},#{foodImg})")
    @Options(useGeneratedKeys = true, keyProperty = "odId")
    int saveOrderDetail(OrderDetail orderDetail);
} 