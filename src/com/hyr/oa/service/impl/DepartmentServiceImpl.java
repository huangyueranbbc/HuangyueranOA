package com.hyr.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.dao.DepartmentDao;
import com.hyr.oa.model.Department;
import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.util.AppException;

@Service("departmentServiceImpl")
@Transactional
public class DepartmentServiceImpl implements DepartmentService
{
	private DepartmentDao departmentDao;

	public DepartmentDao getDepartmentDao()
	{
		return departmentDao;
	}

	@Resource(name = "departmentdaoimplement")
	public void setDepartmentDao(DepartmentDao departmentDao)
	{
		this.departmentDao = departmentDao;
	}

	public List<Department> findAll() throws AppException
	{
		return departmentDao.findAll();
	}

	public void save(Department department) throws AppException
	{
		departmentDao.save(department);
	}

	@Override
	public void delete(Long id) throws AppException
	{
		departmentDao.delete(id);
	}

	@Override
	public Department getById(Long id) throws AppException
	{
		return departmentDao.getById(id);
	}

	@Override
	public void update(Department department) throws AppException
	{
		departmentDao.update(department);
	}

	/**
	 * 找到所有的顶级部门
	 * 
	 * @return
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public List<Department> findTopList() throws AppException
	{
		return departmentDao.findTopList();
	}

	/**
	 * 找到该部门的子部门
	 * 
	 * @param parentId
	 * @return
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public List<Department> fintChildrenList(Long parentId) throws AppException
	{
		return departmentDao.fintChildrenList(parentId);
	}

}
