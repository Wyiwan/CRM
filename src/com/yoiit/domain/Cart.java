package com.yoiit.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<String, CartItem> map = new HashMap<>();
	
	private double total=0.0;

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public Collection<CartItem> getItem(){
		return map.values();
	}
	
	//添加商品到购物车
	public void add2Cart(CartItem item){
		
		String pid = item.getProduct().getPid();
		
		if(map.containsKey(pid)){
			//如果有，这更新数量
			CartItem oldItem = map.get(pid);
			
			int count = oldItem.getCount()+item.getCount();
			
			oldItem.setCount(count);
		}else {
			
			map.put(pid, item);
		}
		
		total +=item.getSubtotal();
	}
	
	//删除商品
	public void remove(String pid){
		CartItem item = map.remove(pid);
		total -= item.getSubtotal();
	}
	
	//清除购物车
	public void clear(){
		map.clear();
		
		total = 0.0;
	}
}
