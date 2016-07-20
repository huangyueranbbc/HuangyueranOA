package com.hyr.oa.service;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Forum;
import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 版块Service接口
 */
public interface ForumService extends DaoSupport<Forum>
{

	/**
	 * 上移 最上面不能上移
	 * 
	 * @param id
	 * @throws AppException
	 */
	void moveUp(Long id) throws AppException;

	/**
	 * 下移 最下面不能下移
	 * 
	 * @param id
	 * @throws AppException
	 */
	void moveDown(Long id) throws AppException;

}
