package com.hyr.oa.service;

import javax.transaction.Transactional;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Role;

/**
 * @author Administrator
 * @category 岗位Service接口
 */
@Transactional
public interface RoleService extends DaoSupport<Role>
{

}
