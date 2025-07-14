package yun.edu.business.service;

import yun.edu.business.pojo.Business;
import java.util.List;

public interface BusinessService {
    
    List<Business> getBusinessesByPage(int page, int size);
    
    Business getBusinessById(int businessId);
} 