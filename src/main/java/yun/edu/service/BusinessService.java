package yun.edu.service;

import yun.edu.pojo.Business;

import java.util.List;

public interface BusinessService {
    List<Business> getBusinessesByPage(int page, int size);
}