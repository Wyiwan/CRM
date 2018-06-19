package com.yoiit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {

	// 想要在封装数据之前，就注册好转换器
	@Override
	public Object convert(Class clazz, Object obj) {
		
		// clazz: 代表是你想要转成的类型。
		// obj: 传入进来的想要被转化的对象。
		
		//将 obj 转成 data 类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date date = sdf.parse((String)obj);
			
			return date;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
