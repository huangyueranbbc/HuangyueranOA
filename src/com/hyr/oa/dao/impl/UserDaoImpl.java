package com.hyr.oa.dao.impl;

import org.springframework.stereotype.Repository;

import com.hyr.oa.base.BaseDaoImpl;
import com.hyr.oa.dao.UserDao;
import com.hyr.oa.model.User;
/**
 * @author Administrator
 * @category 用户Dao实现
 */
@Repository(value = "userdaoimplement")
public class UserDaoImpl extends BaseDaoImpl<User>implements UserDao
{

}
