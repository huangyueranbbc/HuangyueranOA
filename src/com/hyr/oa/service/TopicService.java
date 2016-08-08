package com.hyr.oa.service;

import java.util.List;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.Forum;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Topic;
import com.hyr.oa.model.User;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 主题Service接口
 */
public interface TopicService extends DaoSupport<Topic>
{

	/**
	 * 查询指定版块中的主题
	 * 
	 * @param forum
	 * @return
	 * @throws AppException
	 */
	List<Topic> findByForum(Forum forum) throws AppException;

	/**
	 * 查询分页的主题列表数据
	 * 
	 * @param pageNum
	 * @param forum
	 * @return
	 * @throws AppException
	 */
	@Deprecated
	PageBean getPageBeanByForum(int pageNum, Forum forum) throws AppException;

	/**
	 * 根据用户删除用户主题
	 * 
	 * @param user
	 */
	void deleteTopicByUser(User user) throws AppException;

}
