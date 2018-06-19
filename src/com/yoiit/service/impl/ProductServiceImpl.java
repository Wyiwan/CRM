package com.yoiit.service.impl;

import java.util.List;

import com.yoiit.dao.ProductDao;
import com.yoiit.dao.impl.ProductDaoImpl;
import com.yoiit.domain.PageBean;
import com.yoiit.domain.Product;
import com.yoiit.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private static ProductDao dao = new ProductDaoImpl();

	@Override
	public List<Product> findHotPro() throws Exception {
		
		return dao.findHotPro();
	}

	@Override
	public Product getProById(String pid) throws Exception {
		
		return dao.getProById(pid);
	}

	@Override
	public PageBean<Product> findProByPage(int currPage, int pageSize, String cid) throws Exception {

		// 1. 获取当前页的所有商品数据
		List<Product> proList = dao.findProByPage(currPage, pageSize, cid);
		
		// 2. 获取数据的总条数
		int totalCount = dao.getTotalCount(cid);
		
		// 3. 获取分页
		return new PageBean<>(proList, currPage, pageSize, totalCount);
	}
	
	@Override
	public List<Product> findAll() throws Exception {
		
		return dao.findAll();
	}

	@Override
	public void add(Product pro) throws Exception {
		
		dao.add(pro);
		
	}
}
