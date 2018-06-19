package com.yoiit.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/base")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BaseServlet() {
        super();
    }

	/**
	 * 通用的 BaseServlet
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("BBBBBBBBBBBB");
		
		try {
			// 1. 获取相应的子类。
			@SuppressWarnings("all")
			// this 的作用
			// 1）代表自己当前类。
			// 2）如果有子类的话，那么当子类来调用它的时候，就代表子类。
			Class clazz = this.getClass();  // UserServlet
			
			// 2. 获取请求方法 
			String mm = request.getParameter("method"); // user?method=add
			
			// 3. 先获取子类中对应的方法，等下做匹配   // UserServlet  -> add()
			Method method = clazz.getMethod(mm, HttpServletRequest.class, HttpServletResponse.class);
			
			// 4. 执行方法  // add()
			String str = (String)method.invoke(this, request, response);
			
			// 5. 转发子类
			if(str != null){
				request.getRequestDispatcher(str).forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
