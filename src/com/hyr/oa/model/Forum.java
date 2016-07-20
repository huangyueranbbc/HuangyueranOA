package com.hyr.oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author tyg
 * @category 实体：版块
 */
@Entity
@Table(name = "hyr_oa_forum")
public class Forum
{
	private Long id;
	private String name;
	private String description;
	private int position; // 排序用的位置号

	private int topicCount; // 主题数量
	private int articleCount; // 文章数量（主帖数 + 回帖数）
	private Topic lastTopic; // 最新主题
	private Set<Topic> topics = new HashSet<Topic>();

	@Id
	@GeneratedValue
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * @return the topicCount
	 */
	public int getTopicCount()
	{
		return topicCount;
	}

	/**
	 * @param topicCount
	 *            the topicCount to set
	 */
	public void setTopicCount(int topicCount)
	{
		this.topicCount = topicCount;
	}

	/**
	 * @return the articleCount
	 */
	public int getArticleCount()
	{
		return articleCount;
	}

	/**
	 * @param articleCount
	 *            the articleCount to set
	 */
	public void setArticleCount(int articleCount)
	{
		this.articleCount = articleCount;
	}

	/**
	 * @return the lastTopic
	 */
	@OneToOne(targetEntity = Topic.class)
	public Topic getLastTopic()
	{
		return lastTopic;
	}

	/**
	 * @param lastTopic
	 *            the lastTopic to set
	 */
	public void setLastTopic(Topic lastTopic)
	{
		this.lastTopic = lastTopic;
	}

	/**
	 * @return the topics
	 */
	@OneToMany(mappedBy = "forum")
	public Set<Topic> getTopics()
	{
		return topics;
	}

	/**
	 * @param topics
	 *            the topics to set
	 */
	public void setTopics(Set<Topic> topics)
	{
		this.topics = topics;
	}

}
