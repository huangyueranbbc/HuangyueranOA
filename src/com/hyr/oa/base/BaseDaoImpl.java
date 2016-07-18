package com.hyr.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 *
 * @param <T>
 * @category 公用Dao实现
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T>
{

	private SessionFactory sessionFactory;
	protected Class<T> clazz; // 这是一个问题！

	public BaseDaoImpl()
	{
		// 通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];

		System.out.println("clazz = " + clazz.getName());
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
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
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
						"FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")//
				.setParameterList("ids", ids)//
				.list();
	}

	public List<T> findAll() throws AppException
	{
		return getSession()
				.createQuery(//
						"FROM " + clazz.getSimpleName())//
				.list();
	}

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取当前可用Session
	 * 
	 * @return
	 */
	public Session getSession() throws AppException
	{
		return sessionFactory.getCurrentSession();

	};

}
