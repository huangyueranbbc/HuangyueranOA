package com.hyr.oa.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name = "hyr_oa_user")
public class User
{
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

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
	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 判断当前用户是否有指定名称的权限
	 * 
	 * @param privName
	 *            权限的名称
	 */
	public boolean hasPrivilegeByName(String privName)
	{
		// 如果是超级管理员，就有所有的权限
		if (hasAdminPrivilege())
		{
			return true;
		}
		// 如果是普通用户，就需要判断权限了
		for (Role role : roles)
		{
			for (Privilege p : role.getPrivileges())
			{
				if (p.getName().equals(privName))
				{
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 判断当前用户是否有指定URL的权限
	 * 
	 * @param privUrl
	 *            权限的URL
	 * @return
	 */
	public boolean hasPrivilegeByUrl(String privUrl)
	{
		// 如果是超级管理员，就有所有的权限
		if (hasAdminPrivilege())
		{
			return true;
		}

		// a, 去掉后面的参数字符串（如果有）
		int pos = privUrl.indexOf("?");
		if (pos > -1)
		{
			privUrl = privUrl.substring(0, pos);
		}
		// b, 去掉后面的UI后缀（如果有）
		if (privUrl.endsWith("UI"))
		{
			privUrl = privUrl.substring(0, privUrl.length() - 2);
		}

		// 如果是普通用户，就需要判断权限了
		// a, 如果这个URL是不需要控制的功能（登录后就能直接使用的），这是应直接返回true
		Collection<String> allPrivilegeUrls = (Collection<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls");
		if (!allPrivilegeUrls.contains(privUrl))
		{
			return true;
		}
		// b, 如果这个URL是需要控制的功能（登录后还得有对应的权限才能使用的），这是应判断权限
		else
		{
			for (Role role : roles)
			{
				for (Privilege p : role.getPrivileges())
				{
					if (privUrl.equals(p.getUrl()))
					{
						return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * 判断当前用户是否是超级管理员
	 */
	public boolean hasAdminPrivilege()
	{
		return "admin".equals(loginName);
	}

}
