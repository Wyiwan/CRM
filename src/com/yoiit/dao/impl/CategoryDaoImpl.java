package com.yoiit.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.yoiit.dao.CategoryDao;
import com.yoiit.domain.Category;
import com.yoiit.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from category";

		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public void add(Category c) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "insert into category values (?, ?)";

		qr.update(sql, c.getCid(), c.getCname());

	}

	@Override
	public Category getCategoryById(String cid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from category where cid = ?";
		
		return qr.query(sql, new BeanHandler<>(Category.class), cid);
	}
	
	@Override
	public void update(Category c) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update category set cname = ? where cid = ?";
		
		qr.update(sql, c.getCname(), c.getCid());
		
	}
	
	
	@Override
	public void delete(String cid) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "delete from category where cid = ?";
		
		qr.update(sql, cid);
	}

}
