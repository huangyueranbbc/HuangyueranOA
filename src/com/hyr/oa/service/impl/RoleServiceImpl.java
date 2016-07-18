package com.hyr.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.dao.RoleDao;
import com.hyr.oa.model.Role;
import com.hyr.oa.service.RoleService;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 岗位Service实现
 */
@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl implements RoleService
{
	private RoleDao roleDao;

	public RoleDao getRoleDao()
	{
		return roleDao;
	}

	@Resource(name = "roledaoimplement")
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}

	/**
	 * 查询所有岗位
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public List<Role> findAll() throws AppException
	{

		return roleDao.findAll();
	}

	/**
	 * 删除一个岗位信息
	 * 
	 * @param id
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public void delete(Long id) throws AppException
	{
		roleDao.delete(id);
	}

	/**
	 * 添加一个岗位
	 * 
	 * @param role
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public void save(Role role) throws AppException
	{
		roleDao.save(role);
	}

	/**
	 * 获取一个岗位信息
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public Role getById(Long id) throws AppException
	{
		return roleDao.getById(id);
	}

	/**
	 * 更新一个岗位信息
	 * 
	 * @param role
	 * @throws AppException
	 * @throws DatabaseException
	 */
	public void update(Role role) throws AppException
	{
		roleDao.update(role);
	}

}
