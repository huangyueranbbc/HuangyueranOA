package com.hyr.oa.base;

import java.util.List;

import com.hyr.oa.util.AppException;

public interface DaoSupport<T>
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
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id) throws AppException;

	/**
	 * 根据id数组查询多个
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
}
