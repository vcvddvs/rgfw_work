package yun.edu.business.service;

import yun.edu.business.pojo.Food;
import java.util.List;

public interface FoodService {
    
    List<Food> getFoodsByBusinessId(int businessId);
    
    Food getFoodById(int foodId);
} 