package com.yoiit.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yoiit.dao.ProductDao;
import com.yoiit.domain.Product;
import com.yoiit.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHotPro() throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from product limit 9";

		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public Product getProById(String pid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from product where pid = ?";
		
		return qr.query(sql, new BeanHandler<>(Product.class), pid);
	}

	@Override
	public List<Product> findProByPage(int currPage, int pageSize, String cid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from product where cid = ? limit ? , ?";	
		
		return qr.query(sql, new BeanListHandler<>(Product.class), cid, (currPage - 1) * pageSize, pageSize);
	}
	
	@Override
	public void updateCid(String cid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update product set cid = null where cid = ?";		
		
		qr.update(sql, cid);
	}

	@Override
	public int getTotalCount(String cid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select count(*) from product where cid = ?";
		
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}
	
	@Override
	public List<Product> findAll() throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from product";
		
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
	
	@Override
	public void add(Product p) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
		
		qr.update(sql, p.getPid(), p.getPname(), p.getMarket_price(), p.getShop_price(), 
				p.getPimage(), p.getPdate(), p.getIs_hot(), p.getPdesc(), p.getPflag(),p.getCategory().getCid());
		
	}

}
