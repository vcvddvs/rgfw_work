package yun.edu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import yun.edu.pojo.Cart;

@Mapper
public interface CartMapper {
    @Insert("INSERT INTO cart(foodId, businessId, userId, quantity) VALUES (#{foodId}, #{businessId}, #{userId}, #{quantity})")
    void insertCart(Cart cart);
}