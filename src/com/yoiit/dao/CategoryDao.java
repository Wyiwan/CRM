package com.yoiit.dao;

import java.util.List;

import com.yoiit.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void add(Category c) throws Exception;

	Category getCategoryById(String cid) throws Exception;

	void update(Category c) throws Exception;

	void delete(String cid) throws Exception;

}
