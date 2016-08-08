package com.hyr.oa.service;

import java.util.List;

import com.hyr.oa.base.DaoSupport;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Reply;
import com.hyr.oa.model.Topic;
import com.hyr.oa.model.User;
import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 * @category 回复Service接口
 */
public interface ReplyService extends DaoSupport<Reply>
{

	/**
	 * 查询指定主题中的回复列表。排序：最新的排到最后
	 * 
	 * @param topic
	 * @return
	 * @throws AppException
	 */
	List<Reply> findByTopic(Topic topic) throws AppException;

	/**
	 * 查询分页的回复列表数据
	 * 
	 * @param pageNum
	 * @param topic
	 * @return
	 * @throws AppException
	 */
	@Deprecated
	PageBean getPageBeanByTopic(int pageNum, Topic topic) throws AppException;

	/**
	 * 根据用户删除用户所有回复
	 * 
	 * @param id
	 * @throws AppException
	 */
	void deleteReplyByUser(User user) throws AppException;

}
