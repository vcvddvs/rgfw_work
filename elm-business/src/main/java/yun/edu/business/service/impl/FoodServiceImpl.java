package yun.edu.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.business.mapper.FoodMapper;
import yun.edu.business.pojo.Food;
import yun.edu.business.service.FoodService;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    private final FoodMapper foodMapper;

    @Autowired
    public FoodServiceImpl(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    @Override
    public List<Food> getFoodsByBusinessId(int businessId) {
        logger.debug("Service: Getting foods for businessId={}", businessId);
        List<Food> foods = foodMapper.getFoodsByBusinessId(businessId);
        logger.debug("Service: Retrieved {} foods for businessId={}", foods.size(), businessId);
        
        // 检查返回的食品列表是否包含有效数据
        if (foods.isEmpty()) {
            logger.warn("Service: No foods found for businessId={}", businessId);
        } else {
            // 检查第一个食品对象的属性
            Food firstFood = foods.get(0);
            logger.debug("Service: First food details - id={}, name={}, price={}, hasImg={}", 
                firstFood.getFoodId(), 
                firstFood.getFoodName(), 
                firstFood.getFoodPrice(), 
                firstFood.getFoodImg() != null && !firstFood.getFoodImg().isEmpty());
        }
        
        return foods;
    }
    
    @Override
    public Food getFoodById(int foodId) {
        logger.debug("Service: Getting food with id={}", foodId);
        Food food = foodMapper.getFoodById(foodId);
        if (food != null) {
            logger.debug("Service: Retrieved food with id={}, name={}", foodId, food.getFoodName());
        } else {
            logger.warn("Service: No food found with id={}", foodId);
        }
        return food;
    }
} 