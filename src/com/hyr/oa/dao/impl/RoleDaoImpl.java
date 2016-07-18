package com.hyr.oa.dao.impl;

import org.springframework.stereotype.Repository;

import com.hyr.oa.base.BaseDaoImpl;
import com.hyr.oa.dao.RoleDao;
import com.hyr.oa.model.Role;
/**
 * @author Administrator
 * @category 岗位Dao实现
 */
@Repository(value = "roledaoimplement")
public class RoleDaoImpl extends BaseDaoImpl<Role>implements RoleDao
{

}
