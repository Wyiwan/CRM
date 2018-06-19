package com.yoiit.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;

import com.yoiit.domain.Cart;
import com.yoiit.domain.CartItem;
import com.yoiit.domain.Order;
import com.yoiit.domain.OrderItem;
import com.yoiit.domain.PageBean;
import com.yoiit.domain.User;
import com.yoiit.service.OrderService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.DateConverter;
import com.yoiit.utils.PaymentUtil;
import com.yoiit.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
/**
 * @author hp
 *
 */
@WebServlet("/order")
public class OrderServlet extends BaseServlet {

	/*
	 * 如果，我们在写 XXXServlet 类的时候，忘记了继承 BaseServlet 就会报 405
	 * 异常：访问不到指定的资源，访问的方式不对了（后台接收不到你的请求） 常见情况：没有 doPost、doGet、service 方法
	 */
	private static final long serialVersionUID = 1L;

	public OrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 支付的回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getOrderByOid(r6_Order);
			
			//修改订单状态为 已支付
			order.setState(1);
			
			s.update(order);
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		
		
		return "/jsp/msg.jsp";
	}

	/**
	 * 在线支付 String request String response
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收参数
		String name = request.getParameter("name");
		String addr = request.getParameter("address");
		String tel = request.getParameter("telephone");
		String oid = request.getParameter("oid");

		// 2. 通过 oid 获取对应的订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");

		Order o = os.getOrderByOid(oid);
		o.setAddress(addr);
		o.setTelephone(tel);
		o.setName(name);

		// 3. 更新 Order（注意，我们在上面设置值之后，不可能直接更新数据库，必须要去 update）
		os.update(o);

		////////////////// 支付 /////////////////
		String pd_FrpId = "pd_FrpId"; // 银行编码
		String p0_Cmd = "Buy"; // 业务类型
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");// 商户编号
		String p2_Order = oid; // 商户订单号
		String p3_Amt = "0.01"; // 支付金额
		String p4_Cur = "CNY"; // 交易币种
		String p5_Pid = "abc"; // 商品名称
		String p6_Pcat = "zhonglei"; // 商品种类
		String p7_Pdesc = "miaoshu"; // 商品描述
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");// 商户接收支付成功数据的地址
		String p9_SAF = "jsdx"; // 送货地址
		String pa_MP = "你猜"; // 商户扩展信息

		String pr_NeedResponse = "1"; // 应答机制，固定值 1，success
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");// 商户密钥

		// 调用工具类来获取 hmac
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		// 发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");

		// node?p0_Cmd=p0_Cmd&
		sb.append("p0_Cmd=").append("p0_Cmd").append("&");
		sb.append("p1_MerId=").append("p1_MerId").append("&");
		sb.append("p2_Order=").append("p2_Order").append("&");
		sb.append("p3_Amt=").append("p3_Amt").append("&");
		sb.append("p4_Cur=").append("p4_Cur").append("&");
		sb.append("p5_Pid=").append("p5_Pid").append("&");
		sb.append("p6_Pcat=").append("p6_Pcat").append("&");
		sb.append("p7_Pdesc=").append("p7_Pdesc").append("&");
		sb.append("p8_Url=").append("p8_Url").append("&");
		sb.append("p9_SAF=").append("p9_SAF").append("&");
		sb.append("pa_MP=").append("pa_MP").append("&");
		sb.append("pd_FrpId=").append("pd_FrpId").append("&");
		sb.append("pr_NeedResponse=").append("pr_NeedResponse").append("&");
		sb.append("hmac=").append("hmac").append("&");

		// 重定向，发送当前配置好的请求路径到支付平台
		response.sendRedirect(sb.toString());

		return null;
	}

	/**
	 * 根据订单 id 获取订单 String request String response
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收参数
		String oid = request.getParameter("oid");

		// 2. 调用 OrderService 的方法获取对应的订单操作
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");

		Order order = os.getOrderByOid(oid);

		// 3. 存储到 request 域中
		request.setAttribute("order", order);

		return "/jsp/order_info.jsp";
	}

	/**
	 * 订单分页 String request String response
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 接收参数：currPage -> int
		int currPage = Integer.parseInt(request.getParameter("currPage"));

		// 2. 设置每页固定显示的条数
		int pageSize = 3;

		// 3. 获取用户，然后判断用户 -> session
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {

			request.setAttribute("msg", "您还未登录，请先登录！");

			return "/jsp/msg.jsp";
		}

		// 4. 调用 OrderService 的方法执行分页操作，得到一个 PageBean<Order>
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");

		PageBean<Order> bean = os.findAllByPage(currPage, pageSize, user);

		// 5. 存放到 request 域中，到页面中展示分页
		request.setAttribute("pb", bean);

		return "/jsp/order_list.jsp";
	}

	/**
	 * 生成订单 String request String response
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 转化时间格式
		// ConvertUtils.register(new DateConverter(), Date.class);

		// 0. 当用户登录之后，则存放在 session 中的
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			request.setAttribute("msg", "想购物，必先登录...");
			return "/jsp/login.jsp";
		}

		// 1. 封装数据
		Order order = new Order();

		// 2. 设置随机 id
		order.setOid(UUIDUtils.getId());

		// 3. 设置下单的时间
		// 设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// 转化时间格式
		Date date = new Date();
		String dd = sdf.format(date);
		// 设置下单的时间
		order.setOrdertime(sdf.parse(dd));

		// 4. 获取 session 中的 Cart 购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		// 5. 获取总金额
		order.setTotal(cart.getTotal());

		// 6. 获取详细信息
		Collection<CartItem> cartItems = cart.getItem();

		for (CartItem item : cartItems) {

			// 把 cartItem 中获取的详细信息出来
			// 给当前订单的字段设置值
			// 显示到页面
			OrderItem oi = new OrderItem();

			// 设置 id
			oi.setItemid(UUIDUtils.getId());

			// 设置数量
			oi.setCount(item.getCount());

			// 设置小计
			oi.setSubtotal(item.getSubtotal());

			// 设置 pro 关联
			oi.setProduct(item.getProduct());

			// 设置 order 关联
			oi.setOrder(order);

			// 添加到 list 中
			order.getList().add(oi);
		}

		// 7. 设置 User
		order.setUser(user);

		// 8. 调用 OrderService 生成订单操作
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);

		// 9. 存入 request 域中
		request.setAttribute("order", order);

		// 10. 下完订单之后，同时清空购物车
		request.getSession().removeAttribute("cart");

		return "/jsp/order_info.jsp";
	}
}
