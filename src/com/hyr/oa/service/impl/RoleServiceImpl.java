package com.hyr.oa.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.Role;
import com.hyr.oa.service.RoleService;

/**
 * @author Administrator
 * @category 岗位Service实现
 */
@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService {

}