package com.hyr.oa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 实体：主帖
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_topic")
public class Topic extends Article
{

	private static final long serialVersionUID = 7866218157961337022L;

	/** 普通帖 */
	public static final int TYPE_NORMAL = 0;

	/** 精华帖 */
	public static final int TYPE_BEST = 1;

	/** 置顶帖 */
	public static final int TYPE_TOP = 2;

	private String title; // 标题
	private int type; // 类型

	private int replyCount; // 回复数量
	private Date lastUpdateTime; // 最后文章的发表时间
	private Reply lastReply; // 最后回复

	private Forum forum;
	private Set<Reply> replies = new HashSet<Reply>();

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getReplyCount()
	{
		return replyCount;
	}

	public void setReplyCount(int replyCount)
	{
		this.replyCount = replyCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	@OneToOne
	public Reply getLastReply()
	{
		return lastReply;
	}

	public void setLastReply(Reply lastReply)
	{
		this.lastReply = lastReply;
	}

	@ManyToOne
	public Forum getForum()
	{
		return forum;
	}

	public void setForum(Forum forum)
	{
		this.forum = forum;
	}

	@OneToMany(mappedBy = "topic")
	public Set<Reply> getReplies()
	{
		return replies;
	}

	public void setReplies(Set<Reply> replies)
	{
		this.replies = replies;
	}

}
