package com.hyr.oa.view.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.model.Reply;
import com.hyr.oa.model.Topic;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply>
{
	private Long topicId;

	/** 发表回复页面 */
	public String addUI() throws AppException
	{
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}

	/** 发表回复 */
	public String add() throws AppException
	{
		// 封装对象
		Reply reply = new Reply();
		// a, 表单中的参数
		reply.setContent(model.getContent());
		reply.setTopic(topicService.getById(topicId));
		// b, 在显示层才能获得的数据
		reply.setAuthor(getCurrentUser()); // 当前登录的用户
		reply.setIpAddr(getRequestIP()); // 客户端的IP地址
		// 调用业务方法 
		replyService.save(reply);

		return "toTopicShow";
	}

	/**
	 * @return the topicId
	 */
	public Long getTopicId()
	{
		return topicId;
	}

	/**
	 * @param topicId
	 *            the topicId to set
	 */
	public void setTopicId(Long topicId)
	{
		this.topicId = topicId;
	}

}
