package com.hyr.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.cfg.Configuration;
import com.hyr.oa.model.Forum;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Reply;
import com.hyr.oa.model.Topic;
import com.hyr.oa.model.User;
import com.hyr.oa.service.ReplyService;
import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 回复Service实现
 */
@Transactional
@Service("replyServiceImpl")
@SuppressWarnings("unchecked")
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService
{

	/**
	 * 查询指定主题中的回复列表。排序：最新的排到最后
	 * 
	 * @param topic
	 * @return
	 * @throws AppException
	 */
	public List<Reply> findByTopic(Topic topic) throws AppException
	{
		return getSession().createQuery("FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC").setParameter(0, topic).list();
	}

	@Override
	public void save(Reply reply) throws AppException
	{
		// 1，设置属性并保存
		reply.setDeleted(false); // 默认为未删除
		reply.setPostTime(new Date()); // 当前时间
		getSession().save(reply);

		// 2，更新相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();

		forum.setArticleCount(forum.getArticleCount() + 1); // 版块的文章数量（主题+回复）
		topic.setReplyCount(topic.getReplyCount() + 1); // 主题的回复数量
		topic.setLastUpdateTime(reply.getPostTime()); // 主题的最后更新时间（主题发表的时间或最后回复的时间）
		topic.setLastReply(reply); // 主题的最后发表的回复

		getSession().update(topic);
		getSession().update(forum);
	}

	/**
	 * 查询分页的回复列表数据
	 * 
	 * @param pageNum
	 * @param topic
	 * @return
	 * @throws AppException
	 */
	public PageBean getPageBeanByTopic(int pageNum, Topic topic) throws AppException
	{
		// 获取每页显示的记录数目
		int pageSize = Configuration.getPageSize();

		List list = getSession().createQuery("FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC").setParameter(0, topic)
				.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();

		Long count = (Long) getSession().createQuery("SELECT COUNT(*) FROM Reply r WHERE r.topic=? ").setParameter(0, topic).uniqueResult();

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	/**
	 * 根据用户ID删除用户所有回复
	 * 
	 * @param id
	 * @throws AppException
	 */
	public void deleteReplyByUser(User user) throws AppException
	{
		getSession().createQuery("DELETE Reply r WHERE r.author=? ").setParameter(0, user).executeUpdate();

	}

}
