package yun.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.mapper.BusinessMapper;
import yun.edu.pojo.Business;
import yun.edu.service.BusinessService;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessMapper businessMapper;

    @Autowired
    public BusinessServiceImpl(BusinessMapper businessMapper) {
        this.businessMapper = businessMapper;
    }

    @Override
    public List<Business> getBusinessesByPage(int page, int size) {
        int offset = (page - 1) * size;
        return businessMapper.getBusinessesByPage(offset, size);
    }
}