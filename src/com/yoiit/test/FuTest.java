package com.yoiit.test;

public class FuTest {

	public static void main(String[] args) {
		
		Fu f = new AA();
		f.add();
		
		/*
		 主题：子类的实例，赋值给父类的引用
		 
		 问题：为什么可以执行子类的方法
		 
		 解释：
		 	1）子类中有一个区域存放了父类的实例，子类内存区域里应该有一根 this 指针。
		 	指向了这个内存区域中的父类实例，当把引用赋给父类的时候，其实就是把子类内存
		 	中的父类实例区域的引用给回了父类的实例。
		 	
		 	2）每一个子类中，都存在一个 super()，相当于指向父类的那根指针，子类只保存
		 	子类的信息和 super 指针。当 JVM 中加载一个子类的时候，也会把它的父类一起加载，
		 	子类的内部通过 super 指针保存了父类的引用。
		 	
		 	也就是说，子类的内存中，有一个地方存在父类的实例，主要是叫 super。
		 	super 主要是用来指向父类的，然后在子类中我们就可以通过 super() 引用父类。
		 */
	}
}