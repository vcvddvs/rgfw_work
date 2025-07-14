package yun.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yun.edu.mapper.CartMapper;
import yun.edu.pojo.Cart;
import yun.edu.service.CartService;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Override
    public void addCart(Cart cart) {
        cartMapper.insertCart(cart);
    }
}