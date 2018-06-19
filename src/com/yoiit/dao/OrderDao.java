package com.yoiit.dao;

import java.util.List;

import com.yoiit.domain.Order;
import com.yoiit.domain.OrderItem;

public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem item) throws Exception;

	List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception;

	int getTotalCount(String uid) throws Exception;

	Order getOrderByOid(String oid) throws Exception;

	void update(Order o) throws Exception;

}
