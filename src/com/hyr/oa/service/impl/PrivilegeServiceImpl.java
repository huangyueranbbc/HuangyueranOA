package com.hyr.oa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.Privilege;
import com.hyr.oa.service.PrivilegeService;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 权限Service实现
 */
@Service("privilegeServiceImpl")
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege>implements PrivilegeService
{

	@Override
	public List<Privilege> findTopList() throws AppException
	{

		return getSession().createQuery("FROM Privilege p WHERE p.parent IS NULL").list();
	}

	public List<String> getAllPrivilegeUrls() throws AppException
	{
		return getSession()
				.createQuery(//
						"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL")//
				.list();
	}

}
