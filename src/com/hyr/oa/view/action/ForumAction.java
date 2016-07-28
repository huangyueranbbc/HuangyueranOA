package com.hyr.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.ModelDrivenBaseAction;
import com.hyr.oa.model.Forum;
import com.hyr.oa.model.PageBean;
import com.hyr.oa.model.Topic;
import com.hyr.oa.util.AppException;
import com.hyr.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends ModelDrivenBaseAction<Forum>
{

	/**
	 * 0 表示全部主题<br>
	 * 1 表示全部精华贴
	 */
	private int viewType;

	/**
	 * 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 表示只按最后更新时间排序<br>
	 * 2 表示只按主题发表时间排序<br>
	 * 3 表示只按回复数量排序
	 */
	private int orderBy;

	/**
	 * true表示升序<br>
	 * false表示降序
	 */
	private boolean asc;

	/** 版块列表 */
	public String list() throws AppException
	{
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/** 显示单个版块 */
	public String show() throws AppException
	{
		// 准备Forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);

		new QueryHelper(Topic.class, "t")//
		.addWhereCondition("t.forum=?", forum)//
		.addWhereCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1 表示只看精华帖
		.addOrderByProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1 表示只按最后更新时间排序
		.addOrderByProperty((orderBy == 2), "t.postTime", asc) // 表示只按主题发表时间排序
		.addOrderByProperty((orderBy == 3), "t.replyCount", asc) // 表示只按回复数量排序
		.addOrderByProperty((orderBy == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
		.addOrderByProperty((orderBy == 0), "t.lastUpdateTime", false) // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
		.preparePageBean(topicService, pageNum);


		return "show";
	}

	/**
	 * @return the viewType
	 */
	public int getViewType()
	{
		return viewType;
	}

	/**
	 * @param viewType
	 *            the viewType to set
	 */
	public void setViewType(int viewType)
	{
		this.viewType = viewType;
	}

	/**
	 * @return the orderBy
	 */
	public int getOrderBy()
	{
		return orderBy;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(int orderBy)
	{
		this.orderBy = orderBy;
	}

	/**
	 * @return the asc
	 */
	public boolean isAsc()
	{
		return asc;
	}

	/**
	 * @param asc
	 *            the asc to set
	 */
	public void setAsc(boolean asc)
	{
		this.asc = asc;
	}

}
