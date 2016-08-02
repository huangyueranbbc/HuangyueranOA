package com.hyr.oa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 部门
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_department")
public class Department implements Serializable
{
	private Long id;
	private Set<User> users = new HashSet<User>();
	private Department parent;
	private Set<Department> children = new HashSet<Department>();

	private String name;// 名称
	private String description;// 描述

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

	@OneToMany(mappedBy = "department")
	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	@ManyToOne
	public Department getParent()
	{
		return parent;
	}

	public void setParent(Department parent)
	{
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Department> getChildren()
	{
		return children;
	}

	public void setChildren(Set<Department> children)
	{
		this.children = children;
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

}
