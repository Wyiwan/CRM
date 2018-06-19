package com.yoiit.service.impl;

import java.util.List;

import com.yoiit.dao.OrderDao;
import com.yoiit.domain.Order;
import com.yoiit.domain.OrderItem;
import com.yoiit.domain.PageBean;
import com.yoiit.domain.User;
import com.yoiit.service.OrderService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {
	
	private static OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");

	@Override
	public void add(Order order) throws Exception {
		
		try {
			// 1. 开启事务
			DataSourceUtils.startTransaction();
			
			// 把订单中的数据添加到数据库
			dao.add(order);
			
			// int i = 10 / 0;
			
			// 处理订单项，循环获取每一个订单项
			for (OrderItem item : order.getList()) {
				dao.addItem(item);
			}
			
			// 2. 提交事务
			DataSourceUtils.commitAndClose();

		} catch (Exception e) {
			
			e.printStackTrace();
			// 3. 回滚数据
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		
		// list
		List<Order> list = dao.findAllByPage(currPage, pageSize, user.getUid());
		
		// totalCount
		int totalCount = dao.getTotalCount(user.getUid());
		
		// pageBean
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

	@Override
	public Order getOrderByOid(String oid) throws Exception {
		
		return dao.getOrderByOid(oid);
	}

	@Override
	public void update(Order o) throws Exception {
		
		dao.update(o);
	
	}

}
