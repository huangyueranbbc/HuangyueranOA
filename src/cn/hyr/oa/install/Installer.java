package cn.hyr.oa.install;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.model.Privilege;
import com.hyr.oa.model.User;

/**
 * 安装程序（初始化数据）
 * 
 * @author tyg
 * 
 */
@Component 
public class Installer
{

	@Resource
	private SessionFactory sessionFactory;

	@Transactional
	public void install()
	{
		Session session = sessionFactory.getCurrentSession();

		// =======================================================================
		// 1，超级管理员
		User user = new User();
		user.setLoginName("admin");
		user.setName("超级管理员");
		user.setPassword(DigestUtils.md5Hex("admin")); // 密码要使用MD5摘要
		session.save(user); // 保存

		// =======================================================================
		// 2，权限数据
		Privilege menu, menu1, menu2, menu3, menu4, menu5;

		menu = new Privilege("系统管理", null, null);
		menu1 = new Privilege("岗位管理", "/roleAction_list", menu);
		menu2 = new Privilege("部门管理", "/departmentAction_list", menu);
		menu3 = new Privilege("用户管理", "/userAction_list", menu);

		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);

		session.save(new Privilege("岗位列表", "/roleAction_list", menu1));
		session.save(new Privilege("岗位删除", "/roleAction_delete", menu1));
		session.save(new Privilege("岗位添加", "/roleAction_add", menu1));
		session.save(new Privilege("岗位修改", "/roleAction_edit", menu1));

		session.save(new Privilege("部门列表", "/departmentAction_list", menu2));
		session.save(new Privilege("部门删除", "/departmentAction_delete", menu2));
		session.save(new Privilege("部门添加", "/departmentAction_add", menu2));
		session.save(new Privilege("部门修改", "/departmentAction_edit", menu2));

		session.save(new Privilege("用户列表", "/userAction_list", menu3));
		session.save(new Privilege("用户删除", "/userAction_delete", menu3));
		session.save(new Privilege("用户添加", "/userAction_add", menu3));
		session.save(new Privilege("用户修改", "/userAction_edit", menu3));
		session.save(new Privilege("用户初始化密码", "/userAction_initPassword", menu3));

		// ------

		menu = new Privilege("网上交流", null, null);
		menu1 = new Privilege("论坛管理", "/forumManageAction_list", menu);
		menu2 = new Privilege("论坛", "/forumAction_list", menu);

		session.save(menu);
		session.save(menu1);
		session.save(menu2);

		// ------

		menu = new Privilege("审批流转", null, null);
		menu1 = new Privilege("审批流程管理", "/processDefinitionAction_List", menu);
		menu2 = new Privilege("申请模板管理", "/templateAction_List", menu);
		menu3 = new Privilege("起草申请", "/flowAction_templateList", menu);
		menu4 = new Privilege("待我审批", "/flowAction_myTaskList", menu);
		menu5 = new Privilege("我的申请查询", "/flowAction_myApplicationList", menu); 

		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);

	}

	public static void main(String[] args)
	{
		System.out.println("正在初始化数据...");

		// 一定要从Spring容器中取出对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		Installer installer = (Installer) ac.getBean("installer");
		// 执行安装
		installer.install();

		System.out.println("初始化数据完毕！");
	}

}
