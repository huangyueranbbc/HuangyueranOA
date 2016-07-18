package com.hyr.oa.service;

import java.util.List;

import javax.transaction.Transactional;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Role;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 岗位Service接口
 */
@Transactional
public interface RoleService extends DaoSupport<Role>
{

}
