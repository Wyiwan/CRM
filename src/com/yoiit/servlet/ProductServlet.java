package com.yoiit.servlet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiit.domain.PageBean;
import com.yoiit.domain.Product;
import com.yoiit.service.ProductService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.CookUtils;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public ProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 清空浏览记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 获取指定的 cookie
		Cookie cookie = CookUtils.getCookieByName("ids", request.getCookies());
		
		// 2. 判断是否为空
		if (cookie != null){
			
			cookie = new Cookie("ids", "");
			cookie.setPath(request.getContextPath() + "/");
			// 先设置 cookie 的生命
			cookie.setMaxAge(0);
			// 写回浏览器中
			response.addCookie(cookie);
		}
		
		// 3. 获取请求参数
		String cid = request.getParameter("cid");
		String currpage = request.getParameter("currpage");
		
		response.sendRedirect(request.getContextPath() + "/product?method=findProByPage&cid="+cid+"&currPage=" + currpage);
		
		return null;
	}

	/**
	 * 根据分页查询并展示数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findProByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收参数 cid、currPage
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));

		// 2. 设置每一页显示多少个商品
		int pageSize = 12;

		// 3. 调用 ProductServiceImpl 的 findProByPage() 方法 → 得到 PageBean 分页实例
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		// 先根据分类去查找所有对应的商品，然后从第一页开始展示，每页展示多少个商品
		PageBean<Product> pb = ps.findProByPage(currPage, pageSize, cid);

		// 4. 将获取的结果存入 request 域中
		request.setAttribute("pb", pb);

		// 5. 请求转发
		return "/jsp/product_list.jsp";
	}

	/**
	 * 根据商品的 id 查询商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getProById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收 pid 参数
		String pid = request.getParameter("pid");

		// 2. 调用 ProductService 的 getProById(pid) 实现查询单个商品的操作
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product pro = ps.getProById(pid);

		// 3. 把查询出来的 product 转发页面
		if (pro == null) {
			return null;
		}

		request.setAttribute("bean", pro);
		
		/////////////////////// 以前写过的 Cookie 代码 ///////////////////////

		// 4. 当我们浏览了商品之后，顺便把浏览记录，保留到名字为 ids 的 cookie 中

		// 4.1 先去获取 Cookie
		Cookie cookie = CookUtils.getCookieByName("ids", request.getCookies());

		// 4.2 判断获取的 cookie 是否为空
		String ids = "";

		if (cookie == null) {
			// 若 cookie 为空，需要将商品 id 放入到 ids 中
			ids = pid;
		} else {

			// 若不为空，则说明 ids 中已有 id
			// 获取该 id 值
			ids = cookie.getValue(); // "2362525677"，所以在后面我们添加 cookie 时会加上 -
										// 分割开，"2-3-4"

			// 获取每一个 id 值
			String[] arr = ids.split("-");

			// 将数组转成集合
			List<String> idList = Arrays.asList(arr);

			// 再转 linkedList
			LinkedList<String> idLinkedlist = new LinkedList<>(idList);

			if (idLinkedlist.contains(pid)) {

				// 若 ids 中包含 id 则删除，添加到最前面（因为这是最新浏览的商品，要放第一个位置）
				idLinkedlist.remove(pid);
				idLinkedlist.addFirst(pid);
			} else {

				// 若 ids 不包含 id，继续判断长度是否大于 2
				// 从 0 开始
				if (idLinkedlist.size() > 5) {

					// 长度 >= 6 移除最后一个，将当前的放第一位
					idLinkedlist.removeLast();
					idLinkedlist.addFirst(pid);

				} else {

					// 长度 <6 将当前放入最前面
					idLinkedlist.addFirst(pid);
				}
			}

			ids = "";

			// 把每个 id 获取出来，加上一个分隔符
			for (String str : idLinkedlist) {

				ids += (str + "-");
			}

			// 把全部 id 存储到 ids 中
			ids = ids.substring(0, ids.length() - 1);
		}

		// 5. 将 ids 写回去
		cookie = new Cookie("ids", ids);

		// 设置访问路径
		cookie.setPath(request.getContextPath() + "/");

		// 设置存活时间
		cookie.setMaxAge(3600);

		// 写回浏览器
		response.addCookie(cookie);

		return "/jsp/product_info.jsp";
	}

}
