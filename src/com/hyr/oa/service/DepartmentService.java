package com.hyr.oa.service;

import java.util.List;

import javax.transaction.Transactional;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Department;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 部门Service接口
 */
@Transactional
public interface DepartmentService extends DaoSupport<Department> 
{

	/**
	 * 找到所有的顶级部门
	 * 
	 * @return
	 * @throws AppException
	 */
	List<Department> findTopList() throws AppException;

	/**
	 * 找到该部门的子部门
	 * 
	 * @param parentId
	 * @return
	 * @throws AppException
	 */
	List<Department> fintChildrenList(Long parentId) throws AppException;

}
