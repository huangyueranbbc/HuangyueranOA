package com.hyr.oa.service;

import java.util.List;

import com.hyr.oa.model.Application;
import com.hyr.oa.model.TaskView;
import com.hyr.oa.model.User;
import com.hyr.oa.util.AppException;

public interface FlowService
{

	/**
	 * 提交申请
	 * 
	 * @param application
	 * @throws AppException
	 */
	void submit(Application application) throws AppException;

	/**
	 * 查询我的任务列表
	 * 
	 * @param currentUser
	 * @return
	 * @throws AppException
	 */
	List<TaskView> findMyTaskViewList(User currentUser) throws AppException;

}
