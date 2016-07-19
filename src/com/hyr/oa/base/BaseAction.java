package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.service.PrivilegeService;
import com.hyr.oa.service.RoleService;
import com.hyr.oa.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>
{

	private static final long serialVersionUID = -5197412010588767285L;

	// ===================== 声明Service ====================
	@Resource(name = "roleServiceImpl")
	protected RoleService roleService;
	@Resource(name = "departmentServiceImpl")
	protected DepartmentService departmentService;
	@Resource(name = "userServiceImpl")
	protected UserService userService;
	@Resource(name = "privilegeServiceImpl")
	protected PrivilegeService privilegeService;

	// ===================== 对ModelDriven的支持 ====================

	protected T model;

	public BaseAction()
	{
		System.out.println("----------> BaseAction.BaseAction()");
		try
		{
			// 通过反射获取T的真是类型
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public T getModel()
	{
		return model;
	}

}
