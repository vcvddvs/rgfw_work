package yun.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.pojo.Food;
import yun.edu.service.FoodService;
import yun.edu.service.CartService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("elm_servlet_war_exploded/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<Food>> getFoodsByBusinessId(@RequestParam int businessId) {
        List<Food> foods = foodService.getFoodsByBusinessId(businessId);
        return ResponseEntity.ok(foods);
    }
}