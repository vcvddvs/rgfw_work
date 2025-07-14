package yun.edu.order.service;

import yun.edu.order.pojo.Cart;
import java.util.List;

public interface CartService {
    
    int addCart(Cart cart);
    
    List<Cart> getCartsByUserId(String userId);
    
    List<Cart> getCartsByUserIdAndBusinessId(String userId, int businessId);
    
    int clearCart(String userId);
} 