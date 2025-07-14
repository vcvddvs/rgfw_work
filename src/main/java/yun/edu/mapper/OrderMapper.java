package yun.edu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import yun.edu.pojo.Order;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders(userId, businessId, orderDate, orderTotal, daId, orderState, businessName, businessImg) VALUES " +
            "(#{userId}, #{businessId}, #{orderDate}, #{orderTotal}, #{daId}, #{orderState}, #{businessName}, #{businessImg})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void insertOrder(Order order);
}