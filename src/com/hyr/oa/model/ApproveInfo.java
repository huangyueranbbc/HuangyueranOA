package com.hyr.oa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 审批信息
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_approveInfo")
public class ApproveInfo implements Serializable
{
	private Long id;
	private Application application; //
	private User approver;// 审批人

	private Date approveTime;// 审批时间
	private boolean approval; // 是否通过
	private String comment; // 审批意见

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

	@ManyToOne
	public User getApprover()
	{
		return approver;
	}

	public void setApprover(User approver)
	{
		this.approver = approver;
	}

	public Date getApproveTime()
	{
		return approveTime;
	}

	public void setApproveTime(Date approveTime)
	{
		this.approveTime = approveTime;
	}

	@ManyToOne
	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public boolean isApproval()
	{
		return approval;
	}

	public void setApproval(boolean approval)
	{
		this.approval = approval;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

}
