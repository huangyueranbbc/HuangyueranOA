package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.hyr.oa.model.User;
import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.service.ForumService;
import com.hyr.oa.service.PrivilegeService;
import com.hyr.oa.service.ReplyService;
import com.hyr.oa.service.RoleService;
import com.hyr.oa.service.TopicService;
import com.hyr.oa.service.UserService;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public class ModelDrivenBaseAction<T> extends BaseAction implements ModelDriven<T>
{

	private static final long serialVersionUID = -5197412010588767285L;

	// ===================== 对ModelDriven的支持 ====================

	protected T model;

	public ModelDrivenBaseAction()
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
