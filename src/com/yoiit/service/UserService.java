package com.yoiit.service;

import com.yoiit.domain.User;

public interface UserService {

	public Integer register(User user) throws Exception;
	
	public User active(String code) throws Exception;
	
	public User login(String name, String pass) throws Exception;
}
