package com.hyr.oa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 * @category 申请模板实体
 */
@Entity
@Table(name = "hyr_oa_template")
public class Template implements Serializable
{
	private Long id;
	private String name;
	private String processKey;
	private String path;
	private Set<Application> applications = new HashSet<Application>();

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the processKey
	 */
	public String getProcessKey()
	{
		return processKey;
	}

	/**
	 * @param processKey
	 *            the processKey to set
	 */
	public void setProcessKey(String processKey)
	{
		this.processKey = processKey;
	}

	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	@OneToMany(mappedBy = "template")
	public Set<Application> getApplications()
	{
		return applications;
	}

	/**
	 * @param applications
	 *            the applications to set
	 */
	public void setApplications(Set<Application> applications)
	{
		this.applications = applications;
	}

}
