package com.hyr.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.Forum;
import com.hyr.oa.service.ForumService;
import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 版块Service实现
 */
@Transactional
@Service("forumServiceImpl")
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum>implements ForumService
{

	/**
	 * 在查询列表时要按position的值排序
	 * 
	 * @throws AppException
	 */
	@Override
	public List<Forum> findAll() throws AppException
	{
		return getSession()
				.createQuery(//
						"FROM Forum f ORDER BY f.position")//
				.list();
	}

	/**
	 * 重写save()方法，在里面要设置position的值
	 * 
	 * @throws AppException
	 */
	@Override
	public void save(Forum forum) throws AppException
	{
		// 保存到数据库 要先save 因为只有save后 fourm对象才会有ID
		getSession().save(forum);

		// 设置position的值（可以使用id的值）
		forum.setPosition(forum.getId().intValue());
	}

	/**
	 * 上移 最上面不能上移
	 * 
	 * @param id
	 * @throws AppException
	 */
	public void moveUp(Long id) throws AppException
	{
		// 1. 找出要交换位置号的Forum对象
		Forum forum = getById(id);
		Forum upForum = (Forum) getSession()
				.createQuery(//
						"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)// 从结果列表中的哪个索引开始取数据
				.setMaxResults(1)// 最多取几条数据
				.uniqueResult();

		// 最上面不能上移
		if (upForum == null)
		{
			return;
		}

		// 2. 交换postion的值
		int temp = forum.getPosition();
		forum.setPosition(upForum.getPosition());
		upForum.setPosition(temp);

		// 3. 更新到数据库
		getSession().update(forum);
		getSession().update(upForum);

	}

	/**
	 * 下移 最下面不能下移
	 * 
	 * @param id
	 * @throws AppException
	 */
	public void moveDown(Long id) throws AppException
	{
		// 1，找出要交位置号的Forum对象
		Forum forum = getById(id);
		Forum dowmForum = (Forum) getSession()
				.createQuery(//
						"FROM Forum f WHERE f.position>? ORDER BY f.position ASC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)// 从结果列表中的哪个索引开始取数据
				.setMaxResults(1)// 最多取几条数据
				.uniqueResult();

		// 最下面不能下移
		if (dowmForum == null)
		{
			return;
		}

		// 2，交换position的值
		int temp = forum.getPosition();
		forum.setPosition(dowmForum.getPosition());
		dowmForum.setPosition(temp);

		// 3，保存到数据库中
		getSession().update(forum);
		getSession().update(dowmForum);
	}

}
