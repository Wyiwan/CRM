package com.yoiit.test;

public class AA extends Fu {

	@Override
	void add() {
		// 子类中，必须是存着父类的实例，才能调用父类的方法
		// 子类中，是不会保存父类的方法的，只是通过 super（指针）指向父类
		// 然后在执行的时候，才会去调用父类的方法
		super.add();
		System.out.println("AA 中的方法");
	}
}
