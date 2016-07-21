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
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>
{

	private static final long serialVersionUID = -5197412010588767285L;

	protected int pageNum = 1; // 页面获取的当前页 默认为第一页。

	// ===================== 声明Service ====================
	@Resource(name = "roleServiceImpl")
	protected RoleService roleService;
	@Resource(name = "departmentServiceImpl")
	protected DepartmentService departmentService;
	@Resource(name = "userServiceImpl")
	protected UserService userService;
	@Resource(name = "privilegeServiceImpl")
	protected PrivilegeService privilegeService;
	@Resource(name = "forumServiceImpl")
	protected ForumService forumService;
	@Resource(name = "topicServiceImpl")
	protected TopicService topicService;
	@Resource(name = "replyServiceImpl")
	protected ReplyService replyService;

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

	// ========================== 工具方法 ==========================

	/**
	 * 获取当前登录的用户
	 */
	public User getCurrentUser() throws AppException
	{
		return (User) ActionContext.getContext().getSession().get("user");
	}

	/**
	 * 获取客户户的IP地址
	 * 
	 * @return
	 */
	public String getRequestIP() throws AppException
	{
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum()
	{
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

}
