package com.yoiit.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.yoiit.dao.UserDao;
import com.yoiit.domain.User;
import com.yoiit.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * 注册用户
	 */
	public Integer register(User user) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";

		return qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getCode(), user.getState());

	}

	public User getUserByCode(String code) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from user where code = ? limit 1";

		return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	public void update(User user) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update user set state = ? where uid = ?";

		qr.update(sql, user.getState(), user.getUid());

	}

	public User login(String name, String pass) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "SELECT * FROM USER WHERE NAME = ? AND PASSWORD = ? ";
		
		return qr.query(sql, new BeanHandler<>(User.class), name, pass);
	}

}
