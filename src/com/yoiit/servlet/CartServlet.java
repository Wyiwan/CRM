package com.yoiit.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiit.domain.Cart;
import com.yoiit.domain.CartItem;
import com.yoiit.domain.Product;
import com.yoiit.service.ProductService;
import com.yoiit.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       

    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 调用 remove() 方法移除
		getCart(request).clear();
				
		// 2. 转发页面
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}
	
	
	
	/**
	 * 移除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 获取 pid 值
		String pid = request.getParameter("pid");
		
		// 2. 调用 remove() 方法移除
		getCart(request).remove(pid);
		
		// 3. 转发页面
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}
    
    /**
     * 商场已经准备好购物车了，我们如果要去购物的话，需要去自己推一辆购物车去购物
     * */
    
    // 1. 获取购物车
	public Cart getCart(HttpServletRequest request) throws Exception {

		// 1. 假设已经有车了，商场（session）
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		// 2. 做判断
		if(cart == null){
			// 新建购物车
			cart = new Cart();
			// 添加到 session 
			request.getSession().setAttribute("cart", cart);
		}
		
		return cart;
	}
    
    // 2. 添加商品到购物车
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接受参数
		String pid = request.getParameter("pid");
		Integer count = Integer.parseInt(request.getParameter("count"));
		
		// 2. 根据 pid 获取对应的商品
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product pro = ps.getProById(pid);
		
		// 3. Map<String, CartItem>
		CartItem cartItem = new CartItem(pro, count);
		
		// 4. 获取购物车，然后添加商品到购物车
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		
		// 5. 跳转页面
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}

}
