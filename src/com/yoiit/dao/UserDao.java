package com.yoiit.dao;

import com.yoiit.domain.User;

public interface UserDao {

	public Integer register(User user) throws Exception;
	
	public User getUserByCode(String code) throws Exception;
	
	public void update(User user) throws Exception;
	
	public User login(String name, String pass) throws Exception;
}
