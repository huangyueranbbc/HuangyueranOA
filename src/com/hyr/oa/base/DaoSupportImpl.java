package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.cfg.Configuration;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.QueryHelper;

// 这个@Transactional注解对子类中的方法也有效！
@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl<T> implements DaoSupport<T>
{

	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz = null; // 这是一个问题！

	// public BaseDaoImpl(Class<T> clazz) {
	// this.clazz = clazz;
	// }

	public DaoSupportImpl()
	{
		// 通过反射获取T的真是类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];

		System.out.println("---> clazz = " + clazz);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() throws AppException
	{
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) throws AppException
	{
		getSession().save(entity);
	}

	public void update(T entity) throws AppException
	{
		getSession().update(entity);
	}

	public void delete(Long id) throws AppException
	{
		if (id == null)
		{
			return;
		}

		Object entity = getById(id);
		if (entity != null)
		{
			getSession().delete(entity);
		}
	}

	public T getById(Long id) throws AppException
	{
		if (id == null)
		{
			return null;
		} else
		{
			return (T) getSession().get(clazz, id);
		}
	}

	public List<T> getByIds(Long[] ids) throws AppException
	{
		if (ids == null || ids.length == 0)
		{
			return Collections.EMPTY_LIST;
		}

		return getSession()
				.createQuery(//
						// 注意空格！
						"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
				.setParameterList("ids", ids)// 注意一定要使用setParameterList()方法！
				.list();
	}

	public List<T> findAll() throws AppException
	{
		// 注意空格！
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param hql
	 *            查询数据列表的HQL语句，在方法内部会自动生产查询总数量的HQL语句
	 * @param objects
	 * @return
	 * @throws AppException
	 */
	public PageBean getPageBean(int pageNum, String hql, Object[] objects) throws AppException
	{
		// 获取每页显示的记录数目
		int pageSize = Configuration.getPageSize();
		Query query = getSession().createQuery(hql);

		if (objects != null && objects.length > 0)
		{
			for (int i = 0; i < objects.length; i++)
			{
				query.setParameter(i, objects[i]);
			}
		}

		List list = query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();

		// 查询总记录数
		query = getSession().createQuery("SELECT COUNT(*) " + hql);
		if (objects != null && objects.length > 0)
		{
			for (int i = 0; i < objects.length; i++)
			{
				query.setParameter(i, objects[i]);
			}
		}

		Long count = (Long) query.uniqueResult();

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	/**
	 * 公共的查询分页信息的方法（最终版）
	 * 
	 * @param pageNum
	 * @param queryHelper
	 *            查询语句 + 参数列表
	 * @return
	 */
	public PageBean getPageBean(int pageNum, QueryHelper queryHelper) throws AppException
	{
		System.out.println("------------> DaoSupportImpl.getPageBean( int pageNum, QueryHelper queryHelper )");

		// 获取pageSize等信息
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = queryHelper.getParameters();

		// 查询一页的数据列表
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0)
		{ // 设置参数
			for (int i = 0; i < parameters.size(); i++)
			{
				query.setParameter(i, parameters.get(i));
			}
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List list = query.list(); // 查询

		// 查询总记录数
		query = getSession().createQuery(queryHelper.getQueryCountHql()); // 注意空格！
		if (parameters != null && parameters.size() > 0)
		{ // 设置参数
			for (int i = 0; i < parameters.size(); i++)
			{
				query.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) query.uniqueResult(); // 查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

}
