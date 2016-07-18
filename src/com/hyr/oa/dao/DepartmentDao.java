package com.hyr.oa.dao;

import java.util.List;

import com.hyr.oa.base.BaseDao;
import com.hyr.oa.model.Department;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 部门Dao接口
 */
public interface DepartmentDao extends BaseDao<Department>
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
