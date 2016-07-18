package com.hyr.oa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.Department;
import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.util.AppException;

@Service("departmentServiceImpl")
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends DaoSupportImpl<Department>implements DepartmentService
{

	/**
	 * 找到所有的顶级部门
	 * 
	 * @return
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public List<Department> findTopList()
	{
		return getSession()
				.createQuery(//
						"FROM Department d WHERE d.parent IS NULL")//
				.list();
	}

	/**
	 * 找到该部门的子部门
	 * 
	 * @param parentId
	 * @return
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public List<Department> fintChildrenList(Long parentId)
	{
		return getSession()
				.createQuery(//
						"FROM Department d WHERE d.parent.id=?")//
				.setParameter(0, parentId)//
				.list();
	}

}
