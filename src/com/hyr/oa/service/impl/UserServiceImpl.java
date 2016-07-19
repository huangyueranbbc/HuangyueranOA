package com.hyr.oa.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.User;
import com.hyr.oa.service.UserService;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.DigestUtils;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User>implements UserService
{

	@Override
	public void save(User user) throws AppException
	{
		// 设置密码为1234(默认) 进行MD5加密
		String md5Password = DigestUtils.md5Hex("1234");
		user.setPassword(md5Password); 

		// 保存到数据库
		getSession().save(user);
	}

}
