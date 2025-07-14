package yun.edu.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.business.pojo.Food;
import yun.edu.business.service.FoodService;
import yun.edu.common.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/listByBusinessId/{businessId}")
    public Result<List<Food>> getFoodsByBusinessId(@PathVariable int businessId) {
        logger.info("Fetching foods for businessId={}", businessId);
        try {
            List<Food> foods = foodService.getFoodsByBusinessId(businessId);
            logger.info("Successfully retrieved {} foods for businessId={}", foods.size(), businessId);
            // 添加调试日志，检查每个Food对象的内容
            for (int i = 0; i < foods.size(); i++) {
                Food food = foods.get(i);
                logger.debug("Food[{}]: id={}, name={}, price={}, img={}", 
                    i, food.getFoodId(), food.getFoodName(), food.getFoodPrice(), 
                    (food.getFoodImg() != null && food.getFoodImg().length() > 30) ? 
                        food.getFoodImg().substring(0, 30) + "..." : food.getFoodImg());
            }
            return Result.success(foods);
        } catch (Exception e) {
            logger.error("Error fetching foods for businessId={}: {}", businessId, e.getMessage(), e);
            return Result.failure("获取食品列表失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodById(@PathVariable int foodId) {
        logger.info("Fetching food with id={}", foodId);
        try {
            Food food = foodService.getFoodById(foodId);
            if (food != null) {
                logger.info("Successfully retrieved food with id={}", foodId);
                logger.debug("Food details: {}", food);
                return ResponseEntity.ok(Result.success(food));
            } else {
                logger.warn("Food with id={} not found", foodId);
                return ResponseEntity.status(404).body(Result.failure("食品不存在"));
            }
        } catch (Exception e) {
            logger.error("Error fetching food with id={}: {}", foodId, e.getMessage(), e);
            return ResponseEntity.status(500).body(Result.failure("获取食品详情失败: " + e.getMessage()));
        }
    }
} 