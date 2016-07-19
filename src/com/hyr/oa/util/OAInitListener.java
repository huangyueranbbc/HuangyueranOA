package com.hyr.oa.util;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hyr.oa.model.Privilege;
import com.hyr.oa.service.PrivilegeService;

public class OAInitListener implements ServletContextListener
{

	private Log log = LogFactory.getLog(OAInitListener.class);

	// 初始化
	public void contextInitialized(ServletContextEvent sce)
	{
		ServletContext application = sce.getServletContext();

		// 从Spring的容器中取出PrivilegeService的对象实例.
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application); // 获取Spring的监听器中创建的Spring容器对象
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");

		// 1，查询所有顶级的权限列表并放到application作用域中
		List<Privilege> topPrivilegeList;
		try
		{
			topPrivilegeList = privilegeService.findTopList();
			application.setAttribute("topPrivilegeList", topPrivilegeList);
			System.out.println("====== topPrivilegeList已经放到application作用域中了！ ======");  
			log.info("====== topPrivilegeList已经放到application作用域中了！ ======");
		} catch (AppException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 2，查询出所有的权限的URL集合并放到application作用域中
		List<String> allPrivilegeUrls;
		try
		{
			allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
			application.setAttribute("allPrivilegeUrls", allPrivilegeUrls);
			System.out.println("====== allPrivilegeUrls已经放到application作用域中了！ ======"); 
			log.info("====== allPrivilegeUrls已经放到application作用域中了！ ======");
		} catch (AppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 销毁
	public void contextDestroyed(ServletContextEvent sce)
	{

	}

}
