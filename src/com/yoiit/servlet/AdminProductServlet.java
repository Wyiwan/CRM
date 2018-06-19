package com.yoiit.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.yoiit.domain.Category;
import com.yoiit.domain.Product;
import com.yoiit.service.CategoryService;
import com.yoiit.service.ProductService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.UUIDUtils;
import com.yoiit.utils.UploadUtils;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/adminProduct")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private static ProductService ps = (ProductService)BeanFactory.getBean("ProductService");
       
    public AdminProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * 添加商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 目的：添加商品 Product 
		
		// 1. 创建一个 map，用来接收前台传入的数据
		HashMap<String, Object> map = new HashMap<>();
		
		// 2. 创建磁盘文件工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 3. 创建上传的核心对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// 4. 解析 request，因为是从页面传递到服务器
		// FileItem 主要是用来获取我们的文件相关的一些信息，类型、名字、大小等等
		List<FileItem> list = upload.parseRequest(request);
		
		// 5. 遍历循环
		for (FileItem item : list) {
			
			// 判断是否一个简单的上传组件
			if(item.isFormField()){
				
				// key 就是组件的名字，value 就是组件中的值
				map.put(item.getFieldName(), item.getString("utf-8"));  
				
			} else {
				
				// 1）也就是类型为 file 的组件：主要是一个文件
				// a. 获取上传文件的名字，是 one 文件的全路径
				String name = item.getName();
				
				// b. 获取上传文件的全部名字  one.png
				String realName = UploadUtils.getRealName(name);
				
				// c. 设置随机名字
				String uuidName = UploadUtils.getUUIDName(realName);
				
				// d. 指定存放的地方
				// String path = this.getServletContext().getContextPath() + "/products/file";
				String path = this.getServletContext().getRealPath("/products/file");
				
				// 2）获取文件流
				// 输入流
				InputStream is = item.getInputStream();
				
				// 输出流
				// ShopPro/products/file + /one.png
				File file = new File(path, realName);
				FileOutputStream os = new FileOutputStream(file);
				
				// IOUtils 对拷流
				IOUtils.copy(is, os);
				
				// 关闭资源
				os.close();
				is.close();
				
				
				// 3）删除临时文件
				item.delete();
				
				// 4）把处理好的内容，存储到 map 中了，还在 map 中设置文件的路径
				map.put(item.getFieldName(), "products/file/" + uuidName);
				
			}
		}
		
		// 封装数据  ---- Product
		Product pro = new Product();
		
		// 因为添加商品的时候，我们不能指定id、时间
		pro.setPid(UUIDUtils.getId());
		// 时间格式转化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(new Date());
		Date date = sdf.parse(str);
		pro.setPdate(date);
		
		// 还要指定商品对应的分类
		Category c = new Category();
		// 需要把上传文件中指定的分类绑定到 c 中
		c.setCid((String)map.get("cid"));
		
		// 给商品设置绑定分类
		pro.setCategory(c);
		
		BeanUtils.populate(pro, map);
		
		// 调用 service 完成商品的添加操作
		ps.add(pro);
		
		// 刷新页面，展示添加好的商品
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
 
   
	/**
	 * 跳转添加商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		
		List<Category> list = cs.findAll();

		request.setAttribute("list", list);
		
		return "/admin/product/add.jsp";
	}
    
	/**
	 * 查找所有的商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 调用 service 的 findAll() 方法
		List<Product> list = ps.findAll();
		
		// 存入到 request 域中
		request.setAttribute("list", list);
		
		// 跳转页面展示
		return "/admin/product/list.jsp";
	}
}
