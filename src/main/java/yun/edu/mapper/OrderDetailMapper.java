package yun.edu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yun.edu.pojo.OrderDetail;

@Mapper
public interface OrderDetailMapper {
    @Insert("INSERT INTO orderdetailet (orderId, foodId, quantity, foodName, foodImg, price) VALUES (#{orderId}, #{foodId}, #{quantity}, #{foodName}, #{foodImg}, #{price})")
    void insertOrderDetail(OrderDetail orderDetail);
}