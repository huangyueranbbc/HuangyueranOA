package com.hyr.oa.service;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.User;
import com.hyr.oa.util.AppException;

public interface UserService extends DaoSupport<User>
{

	/**
	 * 验证用户名与密码，如果正确就返回这个用户，否则返回空
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 * @throws AppException
	 */
	User findByLoginNameAndPassword(String loginName, String password) throws AppException;

}
