package com.hyr.oa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Administrator
 * @category 文章
 */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Article implements Serializable
{
	private static final long serialVersionUID = 3465231109838821461L;

	private Long id;
	private String content;// 内容
	private String faceIcon; // 表情符号

	private User author;// 作者
	private Date postTime;// 发表时间
	private String ipAddr;// 发表文章时所用的IP地址

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

	@Lob
	@Column(length = 5000)
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFaceIcon()
	{
		return faceIcon;
	}

	public void setFaceIcon(String faceIcon)
	{
		this.faceIcon = faceIcon;
	}

	@ManyToOne(targetEntity = User.class)
	public User getAuthor()
	{
		return author;
	}

	public void setAuthor(User author)
	{
		this.author = author;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostTime()
	{
		return postTime;
	}

	public void setPostTime(Date postTime)
	{
		this.postTime = postTime;
	}

	public String getIpAddr()
	{
		return ipAddr;
	}

	public void setIpAddr(String ipAddr)
	{
		this.ipAddr = ipAddr;
	}

}
