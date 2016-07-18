package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.service.RoleService;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>
{
	private static final long serialVersionUID = 1830067021806381887L;

	// ===================== 注入Service对象 ===================== //
	protected DepartmentService departmentService; // 部门Service注入
	protected RoleService roleService; // 岗位Service注入

	// ===================== 对ModelDriven的支持 ===================== //
	protected T model; // 接收数据的Model对象

	public T getModel()
	{
		// TODO Auto-generated method stub
		return model;
	} 

	public BaseAction() throws AppException
	{
		// 通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];

		try
		{
			model = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	@Resource(name = "roleServiceImpl")
	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

	public DepartmentService getDepartmentService()
	{
		return departmentService;
	}

	@Resource(name = "departmentServiceImpl")
	public void setDepartmentService(DepartmentService departmentService)
	{
		this.departmentService = departmentService;
	}

}
