package com.yoiit.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiit.domain.Category;
import com.yoiit.service.CategoryService;
import com.yoiit.service.impl.CategoryServiceImpl;
import com.yoiit.utils.JsonUtil;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public CategoryServlet() {
		super();
	}

	/**
	 * 获取所有的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 获取 CategoryService
		CategoryService cs = new CategoryServiceImpl();

		// 2. 查找所有的分类信息
		List<Category> cList = cs.findAll();

		// 3. 将获取的数据，设置到 request 域中
		// request.setAttribute("clist", cList);
		
		// 用 JSON 的格式返回数据到页面
		String json = JsonUtil.list2json(cList);
		
		// 4. 写回页面
		// 我们这里已经通过响应的方式，直接把数据写回到浏览器中，即可显示
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(json);
		
		return null;
	}
}
