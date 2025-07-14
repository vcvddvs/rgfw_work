package yun.edu.service;

import yun.edu.pojo.Food;

import java.util.List;

public interface FoodService {
    List<Food> getFoodsByBusinessId(int businessId);
}