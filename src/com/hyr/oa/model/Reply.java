package com.hyr.oa.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 实体：回帖
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_reply")
public class Reply extends Article
{
	private static final long serialVersionUID = 9148769173945531613L;

	private Topic topic; // 所属的主题
	private boolean deleted; // 是否已删除

	@ManyToOne
	public Topic getTopic()
	{
		return topic;
	}

	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

}
