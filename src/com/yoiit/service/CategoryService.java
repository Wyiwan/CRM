package com.yoiit.service;

import java.util.List;

import com.yoiit.domain.Category;

public interface CategoryService {

	List<Category> findAll() throws Exception;

	void add(Category c) throws Exception;

	Category getCategoryById(String cid) throws Exception;

	void update(Category c) throws Exception;

	void delete(String cid) throws Exception;

}
