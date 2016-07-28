package com.hyr.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.ModelDrivenBaseAction;
import com.hyr.oa.model.Privilege;
import com.hyr.oa.model.Role;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Administrator
 * @category 岗位Action
 */
@Controller
@Scope("prototype")
public class RoleAction extends ModelDrivenBaseAction<Role>
{

	private Long[] privilegeIds;

	public RoleAction() throws AppException
	{
		super();
	}

	private static final long serialVersionUID = 7649586411389769049L;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws AppException
	 */
	public String list() throws AppException
	{
		// 获取数据
		List<Role> roleList = roleService.findAll();

		// 放入WEB
		ActionContext.getContext().put("roleList", roleList);
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
		roleService.delete(model.getId());
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
		// 得到参数 封装为对象
		Role role = new Role();
		role.setName(model.getName());
		role.setDescription(model.getDescription());

		// 保持到数据库中
		roleService.save(role);

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
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
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
		// 从数据库取出原对象
		Role role = roleService.getById(model.getId());

		// 设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());

		// 更新到数据库
		roleService.update(role);
		return "toList";
	}

	/**
	 * 设置权限页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String setPrivilegeUI() throws AppException
	{
		// 准备数据
		List<Privilege> privilegeTopList=privilegeService.findTopList();
		ActionContext.getContext().put("privilegeTopList", privilegeTopList);
		
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); 

		// >> 准备回显的权限数据
		privilegeIds = new Long[role.getPrivileges().size()];
		int index = 0;
		for (Privilege privilege : role.getPrivileges())
		{
			privilegeIds[index++] = privilege.getId();
		}

		return "setPrivilegeUI";
	}

	/**
	 * 设置权限
	 * 
	 * @return
	 * @throws AppException
	 */
	public String setPrivilege() throws AppException
	{
		// 从数据库取出原对象
		Role role = roleService.getById(model.getId());

		// 设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<>(privilegeList));

		// 更新到数据库
		roleService.update(role);
		return "toList";
	}

	/**
	 * @return the privilegeIds
	 */
	public Long[] getPrivilegeIds()
	{
		return privilegeIds;
	}

	/**
	 * @param privilegeIds
	 *            the privilegeIds to set
	 */
	public void setPrivilegeIds(Long[] privilegeIds)
	{
		this.privilegeIds = privilegeIds;
	}

}
