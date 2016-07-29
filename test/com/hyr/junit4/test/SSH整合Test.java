package com.hyr.junit4.test;

import org.activiti.engine.ProcessEngine;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyr.oa.model.Template;

public class SSH整合Test
{
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

	// 测试sessionfactory
	@Test
	public void testSessionfactory()
	{
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		System.out.println(sessionFactory.getClass());
//		Template template=new Template();
//		sessionFactory.openSession().save(template);
	}

	// 测试transaction
	@Test
	public void testTransaction()
	{
		TestService testService = (TestService) applicationContext.getBean("userService");
		for (int i = 0; i < 10; i++)
		{
			testService.saveTwoUsers();
		}
	}

	// 测试JPBM的ProcessEngine
	@Test
	public void testProcessEngine()
	{

		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		System.out.println(processEngine);
	}
}
