package com.hyr.oa.service;

import java.util.Collection;
import java.util.List;

import org.activiti.engine.task.Task;

import com.hyr.oa.model.Application;
import com.hyr.oa.model.ApproveInfo;
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

	/**
	 * 获取指定任务活动中所有指向后面环节的Transition的名称
	 * 
	 * @param taskId
	 * @return
	 * @throws AppException
	 */
	List<Task> getOutcomesByTaskId(String taskId) throws AppException;

	/**
	 * 审批处理：
	 * 
	 * 1, 保存审批信息.
	 * 
	 * 2, 完成当前任务.
	 * 
	 * 3, 维护申请的状态.
	 * 
	 * @param approveInfo
	 * @param taskId
	 * @param outcome
	 *            指定离开本活动要使用的Transition的名称，如果为null，就使用默认的Transition离开本活动
	 */
	void approve(ApproveInfo approveInfo, String taskId, String outcome) throws AppException;

	/**
	 * 获取申请信息
	 * 
	 * @param applicationId
	 * @return
	 */
	Application getApplicationById(Long applicationId) throws AppException;

	/**
	 * 获取指定的申请中所有的流转记录（审批信息）
	 * 
	 * @param applicationId
	 * @return
	 */
	List<ApproveInfo> getApproveInfosByApplicationId(Long applicationId) throws AppException;

}
