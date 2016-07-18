package com.hyr.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyr.oa.base.BaseDaoImpl;
import com.hyr.oa.dao.DepartmentDao;
import com.hyr.oa.model.Department;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 部门Dao实现
 */
@SuppressWarnings("unchecked")
@Repository("departmentdaoimplement")
public class DepartmentDaoImpl extends BaseDaoImpl<Department>implements DepartmentDao
{
	/**
	 * 找到所有的顶级部门
	 * 
	 * @return
	 * @throws AppException
	 */
	public List<Department> findTopList() throws AppException
	{
		return getSession().createQuery("FROM Department d WHERE d.parent IS NULL").list();
	}

	/**
	 * 找到该部门的子部门
	 * 
	 * @param parentId
	 * @return
	 * @throws AppException
	 */
	public List<Department> fintChildrenList(Long parentId) throws AppException
	{
		return getSession().createQuery("FROM Department d WHERE d.parent.id=?").setParameter(0, parentId).list();
	}

}
