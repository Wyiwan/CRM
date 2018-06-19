package com.yoiit.dao;

import java.util.List;

import com.yoiit.domain.Product;

public interface ProductDao {

	List<Product> findHotPro() throws Exception;

	Product getProById(String pid) throws Exception;

	List<Product> findProByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

	void updateCid(String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product pro) throws Exception;

}
