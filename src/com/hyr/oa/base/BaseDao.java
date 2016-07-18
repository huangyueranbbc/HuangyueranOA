package com.hyr.oa.base;

import java.util.List;

import org.hibernate.Session;

import com.hyr.oa.util.AppException;

/**
 * @author Administrator
 *
 * @param <T>
 * @category 公用Dao接口
 */
public interface BaseDao<T>
{

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity) throws AppException;

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Long id) throws AppException;

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity) throws AppException;

	/**
	 * 查询实体
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id) throws AppException;

	/**
	 * 查询实体
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids) throws AppException;

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll() throws AppException;

	/**
	 * 获取当前可用Session
	 * 
	 * @return
	 */
	Session getSession() throws AppException;
}
