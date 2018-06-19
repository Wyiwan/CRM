package com.yoiit.service;

import java.util.List;

import com.yoiit.domain.PageBean;
import com.yoiit.domain.Product;

public interface ProductService {

	List<Product> findHotPro() throws Exception;

	Product getProById(String pid) throws Exception;

	PageBean<Product> findProByPage(int currPage, int pageSize, String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product pro) throws Exception;

}
