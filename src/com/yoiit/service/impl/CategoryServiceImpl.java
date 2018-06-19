package com.yoiit.service.impl;

import java.io.InputStream;
import java.util.List;

import com.yoiit.dao.CategoryDao;
import com.yoiit.dao.ProductDao;
import com.yoiit.dao.impl.CategoryDaoImpl;
import com.yoiit.domain.Category;
import com.yoiit.service.CategoryService;
import com.yoiit.utils.BeanFactory;
import com.yoiit.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {
	
	private static CategoryDao dao = new CategoryDaoImpl();

	@Override
	public List<Category> findAll() throws Exception {
		
		// 1. 获取缓存管理器
		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		
		CacheManager manager = CacheManager.create(is);
		
		// 2. 根据缓存的名字，来获取指定的缓存
		Cache cache = manager.getCache("categoryCache");
		
		// 3. 然后在缓存中找对应的数据
		// List<Category> clist = dao.findAll();
		List<Category> clist = null;
		
		// 4. 先去缓存池中获取数据
		// 因为我们的缓存池中保存的数据都叫 Element
		Element ele = cache.get("clist");
		
		// 5. 判断
		if (ele != null){
			
			// 直接返回数据
			clist = (List<Category>) ele.getObjectValue();
			
			// 提示
			System.out.println("缓存池中，真的有你想要的数据！");
			
		} else {
			
			// 如果没有数据，则查询数据，然后添加到缓存池中
			clist = dao.findAll();
			
			// 把获取到的数据，存入缓存池中
			// 先把获取的数据，转成 Element
			cache.put(new Element("clist", clist));
			
			// 提示
			System.out.println("客官，稍等，数据马上就来！");
		}
		
		// 将获取好的数据，返回给调用者使用
		return clist;
	}

	@Override
	public void add(Category c) throws Exception {
		
		dao.add(c);
		
		// 因为我们之前添加分类的时候，会把加载好的数据丢到缓存中
		// 如果我们需要显示新的数据，则先去刷新缓存
		
		// 获取缓存管理器
		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		CacheManager manager = CacheManager.create(is);
		
		// 获取已经存在的缓存
		Cache cache = manager.getCache("categoryCache");
		
		// 直接干掉，然后让它重新生成即可
		cache.remove("clist");
	}

	@Override
	public Category getCategoryById(String cid) throws Exception {
		
		return dao.getCategoryById(cid);
	}
	
	@Override
	public void update(Category c) throws Exception {
		
		dao.update(c);
		
		// 获取缓存管理器
		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		CacheManager manager = CacheManager.create(is);
		
		// 获取已经存在的缓存
		Cache cache = manager.getCache("categoryCache");
		
		// 直接干掉，然后让它重新生成即可
		cache.remove("clist");
		
	}
	
	@Override
	public void delete(String cid) throws Exception {
		
		try {
			
			// 开启事务
			DataSourceUtils.startTransaction();
			
			// 1. 如果我们需要删除分类的话，首先要保证分类下没有关联的商品才行。
			// 1）删掉所有的商品
			// 2）把商品的分类换掉 cid ---> null 或者是其他的
			ProductDao pdao = (ProductDao)BeanFactory.getBean("ProductDao");
			pdao.updateCid(cid);
			
			// 2. 删除分类
			dao.delete(cid);
			
			// 提交事务
			DataSourceUtils.commitAndClose();
			
			// 3. 刷新缓存
			// 获取缓存管理器
			InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
			CacheManager manager = CacheManager.create(is);
			
			// 获取已经存在的缓存
			Cache cache = manager.getCache("categoryCache");
			
			// 直接干掉，然后让它重新生成即可
			cache.remove("clist");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// 回滚数据
			DataSourceUtils.rollbackAndClose();
			
			throw e;
		}
		
	}

}
