package yun.edu.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.order.mapper.CartMapper;
import yun.edu.order.pojo.Cart;
import yun.edu.order.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public int addCart(Cart cart) {
        return cartMapper.saveCart(cart);
    }
    
    @Override
    public List<Cart> getCartsByUserId(String userId) {
        return cartMapper.listCartsByUserId(userId);
    }
    
    @Override
    public List<Cart> getCartsByUserIdAndBusinessId(String userId, int businessId) {
        return cartMapper.listCartsByUserIdAndBusinessId(userId, businessId);
    }
    
    @Override
    public int clearCart(String userId) {
        return cartMapper.clearCartByUserId(userId);
    }
} 