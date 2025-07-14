package yun.edu.service.impl;

import org.springframework.stereotype.Service;
import yun.edu.mapper.FoodMapper;
import yun.edu.pojo.Food;
import yun.edu.service.FoodService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodMapper foodMapper;

    @Override
    public List<Food> getFoodsByBusinessId(int businessId) {
        return foodMapper.getFoodsByBusinessId(businessId);
    }
}