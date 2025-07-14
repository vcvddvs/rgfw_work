package yun.edu.order.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.order.pojo.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    
    @Insert("insert into cart(foodId,businessId,userId,quantity,orderId) values(#{foodId},#{businessId},#{userId},#{quantity},#{orderId})")
    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    int saveCart(Cart cart);
    
    @Select("select * from cart where userId=#{userId}")
    List<Cart> listCartsByUserId(String userId);
    
    @Select("select * from cart where userId=#{userId} and businessId=#{businessId}")
    List<Cart> listCartsByUserIdAndBusinessId(@Param("userId") String userId, @Param("businessId") Integer businessId);
    
    @Select("select * from cart where userId=#{userId} and orderId=#{orderId}")
    List<Cart> listCartsByUserIdAndOrderId(@Param("userId") String userId, @Param("orderId") String orderId);
    
    @Delete("delete from cart where cartId=#{cartId}")
    int removeCart(Integer cartId);
    
    @Delete("delete from cart where userId=#{userId} and foodId=#{foodId}")
    int removeCartByUserIdAndFoodId(@Param("userId") String userId, @Param("foodId") Integer foodId);
    
    @Delete("delete from cart where userId=#{userId}")
    int clearCartByUserId(String userId);
    
    @Update("update cart set quantity=#{quantity} where cartId=#{cartId}")
    int updateCartQuantity(@Param("cartId") Integer cartId, @Param("quantity") Integer quantity);
} 