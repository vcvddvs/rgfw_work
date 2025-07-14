package yun.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.pojo.Cart;
import yun.edu.service.CartService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("elm_servlet_war_exploded/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, Object>> checkout(@RequestBody List<Cart> carts) {
        for (Cart cart : carts) {
            cartService.addCart(cart);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Checkout successful");
        return ResponseEntity.ok(response);
    }
}