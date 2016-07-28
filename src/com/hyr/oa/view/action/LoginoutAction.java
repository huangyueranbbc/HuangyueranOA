package com.hyr.oa.view.action;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.ModelDrivenBaseAction;
import com.hyr.oa.model.User;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author Administrator
 * @category 用户登录登出
 */
@Controller
@Scope("prototype")
public class LoginoutAction extends ModelDrivenBaseAction<User>
{
	private static final long serialVersionUID = -7711077086531863671L;

	/**
	 * 登录页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String loginUI() throws AppException
	{
		return "loginUI";
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws AppException
	 */
	public String login() throws AppException
	{
		User user = userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());

		if (user == null)
		{

			// 如果验证失败，就转回登录页面并提示错误消息
			addFieldError("loginerror", "登录名或密码不正确!");
			return "loginUI";
 
		} else
		{

			// 如果验证通过 则登录
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("user", user);
			return "toHome";
		}
	}

	/**
	 * 注销
	 * 
	 * @return
	 * @throws AppException
	 */
	public String logout() throws AppException
	{
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}

}
