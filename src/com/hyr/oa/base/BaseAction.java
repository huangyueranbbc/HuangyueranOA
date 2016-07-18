package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.service.RoleService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>
{

	// ===================== 声明Service ====================
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;

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
