package com.hyr.oa.service;

import java.util.List;

import com.hyr.oa.model.Department;
import com.hyr.oa.util.AppException;

public interface DepartmentService
{

	List<Department> findAll() throws AppException;

	void save(Department departmentModel) throws AppException;

	void delete(Long id) throws AppException;

	Department getById(Long id) throws AppException;

	void update(Department department) throws AppException;

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
