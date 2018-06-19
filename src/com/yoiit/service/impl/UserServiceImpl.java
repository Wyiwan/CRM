package com.yoiit.service.impl;

import com.yoiit.dao.UserDao;
import com.yoiit.dao.impl.UserDaoImpl;
import com.yoiit.domain.User;
import com.yoiit.service.UserService;
import com.yoiit.utils.MailUtils;

public class UserServiceImpl implements UserService {
	
	private static UserDao userDao = new UserDaoImpl();

	/**
	 * 注册用户
	 * */
	@Override
	public Integer register(User user) throws Exception {
		
		// 调用 UserDao 类去与数据库交互，然后完成注册操作：register();
		int i = userDao.register(user);
		
		// 激活邮件的内容
		String emailStr = "欢迎您来购物，多买多送哦，<a href='http://localhost:8080/ShopPro/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		
		// 使用 MailUtil 发送邮件
		MailUtils.sendMail(user.getEmail(), emailStr);
	
		return i;
	}

	/**
	 * 激活用户
	 * @param code
	 * @return
	 */
	public User active(String code) throws Exception {
		
		// 1. 通过 code 去获取一个用户
		User user = userDao.getUserByCode(code);
		
		// 2. 判断
		if(user == null){
			return null;
		}
		
		// 3. 设置状态
		user.setState(1);
		
		// 4. 更新用户的数据（数据库）
		userDao.update(user);
	
		return user;
	}

	public User login(String name, String pass) throws Exception {

		//  UserDao 的 login(name, pass) 方法与数据库交互，然后执行登录。
		return userDao.login(name, pass);
	}

}
