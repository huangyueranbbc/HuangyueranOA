package com.hyr.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.model.Department;
import com.hyr.oa.model.Role;
import com.hyr.oa.model.User;
import com.hyr.oa.service.UserService;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.DepartmentUtils;
import com.hyr.oa.util.DigestUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>
{

	private static final long serialVersionUID = 8096925977637826950L;

	private Long departmentId; // 接收参数的部门ID
	private Long[] roleIds; // 接收岗位的数组

	public UserAction() throws AppException
	{
		super();
	}

	/**
	 * 列表
	 * 
	 * @return
	 * @throws AppException
	 */
	public String list() throws AppException
	{
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws AppException
	 */
	public String delete() throws AppException
	{
		userService.delete(model.getId());
		return "toList";
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String addUI() throws AppException
	{
		// 准备部门列表 岗位列表数据
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartmentList(topList, null);
		ActionContext.getContext().put("departmentList", departmentList);

		return "addUI";
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws AppException
	 */
	public String add() throws AppException
	{
		// 封装对象
		// >> 处理关联的部门
		model.setDepartment(departmentService.getById(departmentId));
		// >> 处理关联的岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<>(roleList));

		// 保存到数据库
		userService.save(model);

		return "toList";
	}

	/**
	 * 修改页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String editUI() throws AppException
	{
		// 准备回显的数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);

		// >> 处理部门回显
		if (user.getDepartment() != null)
		{
			departmentId = user.getDepartment().getId();
		}

		// >> 处理岗位回显
		if (user.getRoles().size() > 0 || user.getRoles() != null)
		{
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles())
			{
				roleIds[index++] = role.getId();
			}
		}

		// 准备部门列表 岗位列表数据
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartmentList(topList, null);
		ActionContext.getContext().put("departmentList", departmentList);

		return "editUI";
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws AppException
	 */
	public String edit() throws AppException
	{
		// 1.从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2.设置要修改的属性
		user.setLoginName(model.getLoginName());
		user.setDescription(model.getDescription());
		user.setEmail(model.getEmail());
		user.setGender(model.getGender());
		user.setName(model.getName());
		user.setPhoneNumber(model.getPhoneNumber());

		// >> 处理关联的部门
		user.setDepartment(departmentService.getById(departmentId));
		// >> 处理关联的岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<>(roleList));

		// 3.更新到数据库
		userService.update(user);
		return "toList";
	}

	/**
	 * 初始化密码
	 * 
	 * @return
	 * @throws AppException
	 */
	public String initPassword() throws AppException
	{
		// 1.从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2.设置要修改的属性 进行密码MD5加密
		String md5Password = DigestUtils.md5Hex("1234");
		user.setPassword(md5Password);

		// 3.更新到数据库
		userService.update(user);
		return "toList";
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds()
	{
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds)
	{
		this.roleIds = roleIds;
	}

}
