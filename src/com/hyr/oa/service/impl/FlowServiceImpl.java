package com.hyr.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hyr.oa.model.Application;
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
		List<Task> taskLits = processEngine.getTaskService().createTaskQuery().taskAssignee("张三").list();

		List<TaskView> returnList = new ArrayList<TaskView>();

		for (Task task : taskLits)
		{
			Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
			TaskView tv = new TaskView(task, application);
			returnList.add(tv);
		}
		return returnList;
	}

}
