package com.yoiit.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiit.domain.Category;
import com.yoiit.service.CategoryService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.UUIDUtils;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收参数
		String cid = request.getParameter("cid");
		
		System.out.println("cid----------" + cid);
		
		// 2. 调用 service 执行删除操作
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		
		cs.delete(cid);
		
		// 3. 刷新页面
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
    
    
    
	/**
	 * 根据指定的 id 获取分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getCategoryById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 获取我们需要更新的分类名字 --- cid
		String cid = request.getParameter("cid");
		
		// 2. 调用 CategoryService 获取对应的分类数据，然后做更新操作
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		
		Category c = cs.getCategoryById(cid);
		
		// 3. 存放到 request 域中
		request.setAttribute("bean", c);
		
		// 4. 跳转 edit.jsp 页面，进行更新
		return "/admin/category/edit.jsp";
	}
    
	/**
	 * 更新分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收 cid 和 cname
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		
		cname = new String(cname.getBytes("iso8859-1"), "utf-8");
		
		// 2. 封装数据
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		
		// 3. 调用 service 去执行更新操作
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		cs.update(c);
		
		// 4. 刷新页面，让我们看到所有已存在的分类
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
	
	
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// 1. 获取参数，也就是我们需要添加的分类名称
		// request.setCharacterEncoding("utf-8");
		String cname = request.getParameter("cname");
		
		// 转字符编码
		cname = new String(cname.getBytes("iso8859-1"), "utf-8");
		
		// 2. 封装数据   实例 --- 记录
		Category c = new Category();
		
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		
		// 3. 调用 CategoryService 完成添加操作
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		cs.add(c);
		
		// 4. 刷新页面，让我们看到所有已存在的分类
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
    
    
	/**
	 * 添加分类的页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/admin/category/add.jsp";
	}
    
	/**
	 * 展示所有的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 调用 CategoryService 中的 findAll() 获取所有的分类
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		
		List<Category> list = cs.findAll();
		
		// 2. 存放到 request 域中
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";
	}

}
