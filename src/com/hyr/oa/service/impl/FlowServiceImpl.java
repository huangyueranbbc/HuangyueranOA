package com.hyr.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hyr.oa.model.Application;
import com.hyr.oa.model.ApproveInfo;
import com.hyr.oa.model.TaskView;
import com.hyr.oa.model.User;
import com.hyr.oa.service.FlowService;
import com.hyr.oa.util.AppException;
import com.sun.org.apache.bcel.internal.generic.LREM;

@Service("flowServiceImpl")
@Transactional
public class FlowServiceImpl implements FlowService
{

	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ProcessEngine processEngine;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 提交申请
	 * 
	 * @param application
	 * @throws AppException
	 */
	public void submit(Application application) throws AppException
	{
		// 1，设置属性并保存申请信息
		application.setApplyTime(new Date()); // 申请时间
		application.setStatus(Application.STATUS_RUNNING); // 状态，默认为“正在审批中”
		application.setTitle(application.getTemplate().getName()// 标题格式：{模版名称}_{申请人姓名}_{申请日期}
				+ "_" + application.getApplicant().getName()//
				+ "_" + sdf.format(application.getApplyTime()));
		sessionFactory.getCurrentSession().save(application); // 保存

		// 2，启动流程开始流转
		// >> 启动流程实例，设置流程变量application
		String processKey = application.getTemplate().getProcessKey(); // 流程定义的key
		Map<String, Object> variables = new HashMap<String, Object>(); // 流程变量
		variables.put("application", application); // 携带数据
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processKey, variables);

		// >> 办理完第一个任务（提交申请）
		Task task = processEngine.getTaskService().createTaskQuery()// 查询出本流程实例中当前情况下仅有的一个任务
				.processInstanceId(pi.getId()).singleResult();
		processEngine.getTaskService().complete(task.getId());
	}

	/**
	 * 查询我的任务列表
	 * 
	 * @param currentUser
	 * @return
	 * @throws AppException
	 */
	public List<TaskView> findMyTaskViewList(User currentUser) throws AppException
	{
		String userId = currentUser.getLoginName(); // loginName作为标示符
		List<Task> taskLits = processEngine.getTaskService().createTaskQuery().taskAssignee(userId).list();
		List<TaskView> returnList = new ArrayList<TaskView>();

		for (Task task : taskLits)
		{
			if (!task.isSuspended())
			{
				Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
				TaskView tv = new TaskView(task, application);
				// new TaskView(task, application);
				returnList.add(tv);
			}

		}
		return returnList;
	}

	/**
	 * 获取指定任务活动中所有指向后面环节的Transition的名称
	 * 
	 * @param taskId
	 * @return
	 * @throws AppException
	 */
	public List<Task> getOutcomesByTaskId(String taskId) throws AppException
	{
		Map<String, Object> taskComments1 = processEngine.getTaskService().getVariablesLocal(taskId);

		return new ArrayList<>();
	}

	public Application getApplicationById(Long applicationId)
	{
		return (Application) sessionFactory//
				.getCurrentSession()//
				.get(Application.class, applicationId);
	}

	public void approve(ApproveInfo approveInfo, String taskId, String outcome)
	{
		// 1, 保存审批信息
		sessionFactory.getCurrentSession().save(approveInfo);

		// 2, 完成当前任务
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();// 获取任务的代码一定要是写到完成任务前，因为任务办理完后就变成了历史任务信息。
		processEngine.getTaskService().complete(taskId); // 使用默认的Transition

		// >> 获取任务所属的流程实例（正在执行的表中的信息），如果流程已经结束了，则返回null.
		ProcessInstance pi = processEngine.getRuntimeService()// 表示正在执行的流程实例和执行对象
				.createProcessInstanceQuery()// 创建流程实例查询
				.processInstanceId(task.getExecutionId())// 使用流程实例ID查询
				.singleResult();

		// 3, 维护申请的状态
		Application application = approveInfo.getApplication();
		if (!approveInfo.isApproval())
		{
			// 如果本环节不同意，则流程直接结束，申请的状态为“未通过”
			// 如果本环节不是最后一个，我们就要手工结束这个流程实例
			if (pi != null)
			{
				processEngine.getRuntimeService().suspendProcessInstanceById(pi.getId());
			}
			application.setStatus(Application.STATUS_REJECTED);
		} else
		{
			if (pi == null)
			{
				// 如果本环节同意了，并且本环节是最后一个审批，则流程正常结束，申请的状态为“已通过”
				application.setStatus(Application.STATUS_APPROVED);
			}
		}
		sessionFactory.getCurrentSession().update(application);
	}

	/**
	 * 获取指定的申请中所有的流转记录（审批信息）
	 * 
	 * @param applicationId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ApproveInfo> getApproveInfosByApplicationId(Long applicationId)
	{
		return sessionFactory.getCurrentSession()//
				.createQuery("FROM ApproveInfo a WHERE a.application.id=? ORDER BY a.approveTime ASC")//
				.setParameter(0, applicationId)//
				.list();
	}
}
