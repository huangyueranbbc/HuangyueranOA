package com.hyr.oa.base;

import java.util.List;

import com.hyr.oa.model.PageBean;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.QueryHelper;

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

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param hql
	 *            查询数据列表的HQL语句，在方法内部会自动生产查询总数量的HQL语句
	 * @param objects
	 * @return
	 * @throws AppException
	 */
	PageBean getPageBean(int pageNum, String hql, Object[] objects) throws AppException;

	/**
	 * 公共的查询分页信息的方法（最终版）
	 * 
	 * @param pageNum
	 * @param queryHelper
	 *            查询语句 + 参数列表
	 * @return
	 */
	PageBean getPageBean(int pageNum, QueryHelper queryHelper)throws AppException;
}
