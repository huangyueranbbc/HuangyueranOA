package com.hyr.junit4.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

public class SSH整合Test{
	private ApplicationContext applicationContext=new ClassPathXmlApplicationContext("beans.xml");
	
	//测试sessionfactory
	@Test
	public void testSessionfactory(){
		SessionFactory sessionFactory=(SessionFactory) applicationContext.getBean("sessionFactory");
		System.out.println(sessionFactory.getClass());
	}
	
	//测试transaction
	@Test
	public void testTransaction(){
		TestService testService=(TestService) applicationContext.getBean("userService");
		for(int i=0;i<10;i++){
			testService.saveTwoUsers();
		}
	}
}
