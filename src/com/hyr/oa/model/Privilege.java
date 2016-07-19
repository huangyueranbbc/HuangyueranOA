package com.hyr.oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * 实体：权限
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_privilege")
public class Privilege
{
	private Long id;
	private String url;
	private String name;
	private Set<Role> roles = new HashSet<Role>();

	private Privilege parent;
	private Set<Privilege> children = new HashSet<Privilege>();

	public Privilege()
	{
	}

	public Privilege(String name, String url, Privilege parent)
	{
		this.name = name;
		this.url = url;
		this.parent = parent;
	}

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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	@ManyToOne
	public Privilege getParent()
	{
		return parent;
	}

	public void setParent(Privilege parent)
	{
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	@OrderBy(value = "id")
	public Set<Privilege> getChildren()
	{
		return children;
	}

	public void setChildren(Set<Privilege> children)
	{
		this.children = children;
	}

}
