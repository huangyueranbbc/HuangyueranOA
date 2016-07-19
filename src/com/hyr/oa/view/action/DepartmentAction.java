package com.hyr.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.model.Department;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>
{

	public DepartmentAction() throws AppException
	{
		super();
	}

	private static final long serialVersionUID = 7726958497821009642L;

	private Long parentId;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws AppException
	 */
	public String list() throws AppException
	{

		List<Department> departmentList;

		if (parentId == null)
		{
			departmentList = departmentService.findTopList();
		} else
		{
			Department parent = departmentService.getById(parentId);
			departmentList = departmentService.fintChildrenList(parentId);
			ActionContext.getContext().put("parent", parent);
		}

		ActionContext.getContext().put("departmentList", departmentList);
		// System.out.println("长度:"+departmentList.size());
		return "list";
	};

	/**
	 * 删除
	 * 
	 * @return
	 * @throws AppException
	 */
	public String delete() throws AppException
	{
		departmentService.delete(model.getId());
		return "toList";
	};

	/**
	 * 添加页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String addUI() throws AppException
	{
		// 准备数据
		List<Department> departmentList = departmentService.findTopList();
		List<Department> departmentListResult = DepartmentUtils.getAllDepartmentList(departmentList, null);
		ActionContext.getContext().put("departmentList", departmentListResult);
		// 显示数据
		return "addUI";
	};

	/**
	 * 添加
	 * 
	 * @return
	 * @throws AppException
	 */
	public String add() throws AppException
	{
		// 新建对象并封装属性到对象中
		// >> 处理上级部门
		Department parent = departmentService.getById(parentId);
		model.setParent(parent);
		// 保持到数据库中
		departmentService.save(model);
		return "toList";
	};

	/**
	 * 修改页面
	 * 
	 * @return
	 * @throws AppException
	 */
	public String editUI() throws AppException
	{
		// 准备回显的信息
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		List<Department> departmentList = departmentService.findTopList();
		if (department.getParent() != null)
		{
			this.parentId = department.getParent().getId();
		}
		List<Department> departmentListResult = DepartmentUtils.getAllDepartmentList(departmentList, department);
		ActionContext.getContext().put("departmentList", departmentListResult);

		return "editUI";
	};

	/**
	 * 修改
	 * 
	 * @return
	 * @throws AppException
	 */
	public String edit() throws AppException
	{
		// 从数据库中取出原数据
		Department department = departmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		// >> 处理上级部门
		Department parent = departmentService.getById(parentId);
		department.setParent(parent);
		departmentService.update(department);
		return "toList";
	};

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

}
