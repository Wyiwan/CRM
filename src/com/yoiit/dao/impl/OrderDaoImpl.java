package com.yoiit.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yoiit.dao.OrderDao;
import com.yoiit.domain.Order;
import com.yoiit.domain.OrderItem;
import com.yoiit.domain.Product;
import com.yoiit.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void add(Order o) throws Exception {

		QueryRunner qr = new QueryRunner();

		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";

		qr.update(DataSourceUtils.getConnection(), sql, o.getOid(), o.getOrdertime(), o.getTotal(), o.getState(),
				o.getAddress(), o.getName(), o.getTelephone(), o.getUser().getUid());

	}

	@Override
	public void addItem(OrderItem item) throws Exception {

		QueryRunner qr = new QueryRunner();

		String sql = "insert into orderitems values (?,?,?,?,?)";

		qr.update(DataSourceUtils.getConnection(), sql, item.getItemid(), item.getCount(), item.getSubtotal(),
				item.getProduct().getPid(), item.getOrder().getOid());
	}

	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from orders where uid = ? limit ? , ? ";

		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid, (currPage - 1) * pageSize, pageSize);

		// 所有的订单数据都有了，但是，订单中的详细信息还没有
		// 接着，我们要通过查询来获取订单中对应的详细信息
		// 也就是获取订单中的明细信息
		// Order -> OrderItem -> Product
		sql = "select * from orderitems oi, product p " + "where oi.pid = p.pid " + "and oi.oid = ?";

		for (Order order : list) {

			// 当前订单包含的所有内容
			// list 代表的是所有的商品
			// map 代表的是某个商品<商品名字，商品的数量>
			List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());

			// 循环，获取每一个 map，也就是每一个商品的信息
			for (Map<String, Object> map : mapList) {

				// 封装 Product 对象
				Product p = new Product();
				BeanUtils.populate(p, map);

				// 封装 OrderItem 对象
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);

				// 把 product 设置到 orderItem 中
				oi.setProduct(p);

				// 再把 orderItem 设置到 order 中
				// 先获取 order 中的 list 集合，再把 OrderItem 加入到 list 中即可
				order.getList().add(oi);
			}
		}

		return list;
	}

	@Override
	public int getTotalCount(String uid) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select count(*) from orders where uid = ?";

		return ((Long) qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	@Override
	public Order getOrderByOid(String oid) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from orders where oid = ? ";

		Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);

		/////////////////// 展示订单中的详情///////////////////////////
		sql = "select * from orderitems oi, product p " 
				+ "where oi.pid = p.pid " 
				+ "and oi.oid = ?";

		// 当前订单包含的所有内容
		// list 代表的是所有的商品
		// map 代表的是某个商品<商品名字，商品的数量>
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());

		// 循环，获取每一个 map，也就是每一个商品的信息
		for (Map<String, Object> map : mapList) {

			// 封装 Product 对象
			Product p = new Product();
			BeanUtils.populate(p, map);

			// 封装 OrderItem 对象
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);

			// 把 product 设置到 orderItem 中
			oi.setProduct(p);

			// 再把 orderItem 设置到 order 中
			// 先获取 order 中的 list 集合，再把 OrderItem 加入到 list 中即可
			order.getList().add(oi);
		}

		///////////////////////////////////////////////////////////

		return order;
	}

	@Override
	public void update(Order o) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update orders set state = ?, address = ?, name = ?, "
				+ "telephone = ? where oid = ?";

		qr.update(sql, o.getState(), o.getAddress(), o.getName(), o.getTelephone(), o.getOid());
	}

}
