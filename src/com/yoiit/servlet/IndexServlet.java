package com.yoiit.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiit.domain.Category;
import com.yoiit.domain.Product;
import com.yoiit.service.CategoryService;
import com.yoiit.service.ProductService;
import com.yoiit.service.impl.CategoryServiceImpl;
import com.yoiit.service.impl.ProductServiceImpl;
import com.yoiit.utils.BeanFactory;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexServlet() {
        super();
    }
    
	/**
	 * 首页信息展示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String index(HttpServletRequest request, HttpServletResponse response)throws Exception{

		// 加载商品的数据，然后显示
		
		// 1. 调用 ProductServiceImpl 的 findHotPro() 展示热门商品
		// 第一种：ProductService ps = new ProductServiceImpl();  // 军队
		// 第二种：雇佣兵，安保团队
		ProductService ps = (ProductService)BeanFactory.getBean("ProductService");
		
		// 第一种，会先去堆内存中开辟一个空间，然后实例化我们的对象。
		// 第二种，如果你要用的时候，它才会去根据反射的原理去找对象实例，如果没有的话，再进行实例化。
		// 模式：懒加载（用到的时候，再去加载）
		
		// 2. 获取数据
		List<Product> hotList = ps.findHotPro();
		
		// 3. 判断
		if(hotList == null){
			request.setAttribute("msg", "加载数据失败！");
		}
		
		// 4. 把数据加入到 request 域中
		request.setAttribute("hotList", hotList);

		return "/jsp/index.jsp";
	}
}
