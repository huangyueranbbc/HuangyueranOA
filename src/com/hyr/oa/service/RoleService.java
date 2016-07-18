package com.hyr.oa.service;

import java.util.List;

import javax.transaction.Transactional;

import com.hyr.oa.model.Role;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 岗位Service接口
 */
@Transactional
public interface RoleService
{

	/**
	 * 查询所有岗位
	 * 
	 * @return
	 */
	List<Role> findAll() throws AppException;

	/**
	 * 删除一个岗位信息
	 * 
	 * @param id
	 * @throws AppException
	 */
	void delete(Long id) throws AppException;

	/**
	 * 添加一个岗位
	 * 
	 * @param role
	 * @throws AppException
	 */
	void save(Role role) throws AppException;

	/**
	 * 获取一个岗位信息
	 * 
	 * @param id
	 * @return
	 */
	Role getById(Long id) throws AppException;

	/**
	 * 更新一个岗位信息
	 * 
	 * @param role
	 * @throws AppException
	 */
	void update(Role role) throws AppException;

}
