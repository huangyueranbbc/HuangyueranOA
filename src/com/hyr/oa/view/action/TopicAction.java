package com.hyr.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.ModelDrivenBaseAction;
import com.hyr.oa.model.Forum;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Reply;
import com.hyr.oa.model.Topic;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends ModelDrivenBaseAction<Topic>
{
	private static final long serialVersionUID = 344773284573816989L;

	private Long forumId;

	/** 显示单个主题 */
	public String show() throws AppException
	{
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);

		// 准备分页的数据 （最终版）
		new QueryHelper(Reply.class, "r")//
				.addWhereCondition("r.topic=?", topic)//
				.addOrderByProperty("r.postTime", true)//
				.preparePageBean(replyService, pageNum);
 
		return "show";
	}

	/** 发新帖页面 */
	public String addUI() throws AppException
	{
		// 准备数据
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);

		return "addUI";
	}

	/** 发新帖 */
	public String add() throws AppException
	{
		// 封装对象
		Topic topic = new Topic();
		// >> a, 表单中的参数
		topic.setTitle(model.getTitle());
		topic.setContent(model.getContent());
		topic.setForum(forumService.getById(forumId));
		// >> b, 在显示层才能获得的数据
		topic.setAuthor(getCurrentUser()); // 当前登录的用户
		topic.setIpAddr(getRequestIP()); // 客户端的IP地址

		// 调用业务方法
		topicService.save(topic);

		// 用于回到刚刚发表的主题展示页面
		ActionContext.getContext().put("topicId", topic.getId());

		// 调用业务方法

		return "toShow";
	}

	/**
	 * @return the forumId
	 */
	public Long getForumId()
	{
		return forumId;
	}

	/**
	 * @param forumId
	 *            the forumId to set
	 */
	public void setForumId(Long forumId)
	{
		this.forumId = forumId;
	}

}
