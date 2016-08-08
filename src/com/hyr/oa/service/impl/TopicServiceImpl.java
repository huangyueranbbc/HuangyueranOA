package com.hyr.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.cfg.Configuration;
import com.hyr.oa.model.Forum;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Topic;
import com.hyr.oa.model.User;
import com.hyr.oa.service.TopicService;
import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 主题Service实现
 */
@Transactional
@Service("topicServiceImpl")
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService
{

	/**
	 * 查询指定版块中的主题。排序：最新状态的排到最上面，置顶贴在最上面。
	 * 
	 * @param forum
	 * @return
	 * @throws AppException
	 */
	public List<Topic> findByForum(Forum forum) throws AppException
	{
		return getSession().createQuery("FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END),t.lastUpdateTime DESC")
				.setParameter(0, forum).list();
	}

	@Override
	public void save(Topic topic) throws AppException
	{
		// 1，设置属性并保存
		topic.setType(Topic.TYPE_NORMAL); // 普通帖
		topic.setReplyCount(0);
		topic.setLastReply(null);
		topic.setPostTime(new Date()); // 当前时间
		topic.setLastUpdateTime(topic.getPostTime()); // 默认为主题的发表时间
		getSession().save(topic); // 保存

		// 2，更新相关的信息
		Forum forum = topic.getForum();

		forum.setTopicCount(forum.getTopicCount() + 1); // 主题数量
		forum.setArticleCount(forum.getArticleCount() + 1); // 文章数量（主题+回复）
		forum.setLastTopic(topic); // 最后发表的主题

		getSession().update(forum);
	}

	/**
	 * 查询分页的主题列表数据
	 * 
	 * @param pageNum
	 * @param forum
	 * @return
	 * @throws AppException
	 */
	@Deprecated
	public PageBean getPageBeanByForum(int pageNum, Forum forum) throws AppException
	{
		// 获取每页显示的记录数目
		int pageSize = Configuration.getPageSize();

		List list = getSession().createQuery("FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END),t.lastUpdateTime DESC")
				.setParameter(0, forum).setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();

		Long count = (Long) getSession().createQuery("SELECT COUNT(*) FROM Topic t WHERE t.forum = ?").setParameter(0, forum).uniqueResult();

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	/**
	 * 根据用户删除用户主题
	 * 
	 * @param user
	 */
	public void deleteTopicByUser(User user) throws AppException
	{
		getSession().createQuery("DELETE Topic t WHERE t.author=? ").setParameter(0, user).executeUpdate();
	}

}
