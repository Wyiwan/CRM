package com.yoiit.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
		// 订单 id
		private String oid;
		// 订单时间
		private Date ordertime;
		// 订单总额
		private Double total;
		// 订单状态
		private Integer state=0;
		// 订单地址
		private String address;
		// 订单名字
		private String name;
		// 订单电话
		private String telephone;
		// 属于哪个用户的
		private User user;
		
		private List<OrderItem> list = new LinkedList<>();

		public String getOid() {
			return oid;
		}

		public void setOid(String oid) {
			this.oid = oid;
		}

		public Date getOrdertime() {
			return ordertime;
		}

		public void setOrdertime(Date ordertime) {
			this.ordertime = ordertime;
		}

		public Double getTotal() {
			return total;
		}

		public void setTotal(Double total) {
			this.total = total;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<OrderItem> getList() {
			return list;
		}

		public void setList(List<OrderItem> list) {
			this.list = list;
		}
		
}
