package com.yoiit.service;

import com.yoiit.domain.Order;
import com.yoiit.domain.PageBean;
import com.yoiit.domain.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

	Order getOrderByOid(String oid) throws Exception;

	void update(Order o) throws Exception;

}
