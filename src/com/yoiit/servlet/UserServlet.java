package com.yoiit.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.yoiit.contant.Contant;
import com.yoiit.domain.User;
import com.yoiit.service.impl.UserServiceImpl;
import com.yoiit.utils.DateConverter;
import com.yoiit.utils.MD5Utils;
import com.yoiit.utils.UUIDUtils;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static UserServiceImpl userServiceimpl = new UserServiceImpl();
 
    public UserServlet() {
        super();
    }
    
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response)throws Exception{

		// 1. 获取 session，删除用户
		// invalidate：解除关联
		request.getSession().invalidate();
		
		// 2. 跳回 index.jsp 
		response.sendRedirect(request.getContextPath() + "/");
		
		return null;
	}
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)throws Exception{

		// 1. 获取用户名和密码
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		
		// 因为在注册的时候，我们加密过密码，所以，此时需要再使用 MD5Utils 转化
		pass = MD5Utils.md5(pass);
		
		// 2. 调用 UserService 的 login(name, pass) 方法执行登录，获取 User 对象
		User user = userServiceimpl.login(name, pass);
		
		// 3. 判断 User 是否为空
		if (user != null){
			
			// 先判断 user 是否已经激活
			if(Contant.USER_IS_ACTIVE != user.getState()){
				request.setAttribute("msg", "您还未激活哦...");
				
				// 重新去登录
				return "/jsp/login.jsp";
			}
			
			// 如果用户登录成功之后，需要把用户设置到 session 域中。
			request.getSession().setAttribute("user", user);
			
			// 如果非空，则跳转首页展示商品
			// 方式1： return "/jsp/index.jsp";
			// 方式2：
			response.sendRedirect(request.getContextPath() + "/");
			
		} else {
			
			// 如果为空，则跳回 login.jsp 页面并提示，重新登录
			request.setAttribute("msg", "哥们儿，用户名和密码不对哦...");
			return "/jsp/login.jsp";
		}
		
		return null;
	}
    
	/**
	 * 登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response)throws Exception{

		return "/jsp/login.jsp";
	}
    
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)throws Exception{

		// 1. 获取验证码
		String code = request.getParameter("code");
		// System.out.println(code);
		
		// 2. 调用 service 完成激活
		User user = userServiceimpl.active(code);
		
		// 3. 判断
		if(user != null){
			
			request.setAttribute("msg", "恭喜你，激活成功，赶紧去买买买...");
		} else {
			
			request.setAttribute("msg", "抱歉，激活失败...");
			
		}
		
		// 4. 跳转页面
		return "/jsp/msg.jsp";
	}
    
    
	/**
	 * 注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registerUI(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		return "/jsp/register.jsp";
	}
	
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String register(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		// 0. 在封装数据之前，必须先把时间相关的内容进行转换，然后再去封装
		ConvertUtils.register(new DateConverter(), Date.class);
		
		// 1. 封装数据
		User user = new User();
		BeanUtils.populate(user, request.getParameterMap());
		
		// 1.1 设置 id 值
		user.setUid(UUIDUtils.getId());
		
		// 1.2 设置密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		// 1.3 设置 code
		// user.setCode(UUIDUtils.getCode());
			
		// 2. 调用 UserService 类去完成注册操作：register();
		int i = userServiceimpl.register(user);
		
		// 3. 判断
		if (i == 1){	
			// 提示
			request.setAttribute("msg", "大爷，恭喜你注册成功了，要常来玩哦...");
			
		} else {
			request.setAttribute("msg", "大爷，表着急，慢慢来...");
		}
		
		return "/jsp/msg.jsp";
	}
}
