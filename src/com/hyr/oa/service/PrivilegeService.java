package com.hyr.oa.service;

import java.util.List;

import javax.transaction.Transactional;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Privilege;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 权限Service接口
 */
public interface PrivilegeService extends DaoSupport<Privilege>
{

	/**
	 * 查询所有顶级的权限列表
	 * 
	 * @return
	 */
	List<Privilege> findTopList() throws AppException;

	/**
	 * 获取所有权限的URL
	 * 
	 * @return
	 * @throws AppException
	 */
	List<String> getAllPrivilegeUrls() throws AppException;

}
