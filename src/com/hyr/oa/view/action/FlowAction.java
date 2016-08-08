package com.hyr.oa.view.action;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.model.Application;
import com.hyr.oa.model.ApproveInfo;
import com.hyr.oa.model.TaskView;
import com.hyr.oa.model.Template;
import com.hyr.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

/**
 * @category 流转功能
 */
@Controller
@Scope("prototype")
public class FlowAction extends BaseAction
{

	private static final long serialVersionUID = 7484037766949224770L;

	private Long templateId;
	private Long applicationId; //
	private File upload; // 上传用的文件
	private String status;
	private String taskId; // 任务ID

	private boolean approval;
	private String comment;
	private String outcome;

	// ============================== 申请人的功能 ===================================

	/** 起草申请 */
	public String templateList() throws Exception
	{
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);
		return "templateList";
	}

	/** 提交申请页面 */
	public String submitUI() throws Exception
	{
		return "submitUI";
	}

	/** 提交申请 */
	public String submit() throws Exception
	{
		// 封装对象
		Application application = new Application();
		// >> 1, 表单中的参数
		application.setTemplate(templateService.getById(templateId)); // 所属的模版
		application.setPath(saveUploadFile(upload)); // 上传的文件
		// >> 2, 显示层的信息
		application.setApplicant(getCurrentUser()); // 当前登录的用户

		// 调用业务方法（保持申请信息，启动流程开始流转）
		flowService.submit(application);

		return "toMyApplicationList";
	}

	/** 我的申请查询 */
	public String myApplicationList() throws Exception
	{
		new QueryHelper(Application.class, "a")//
				.addWhereCondition("a.applicant=?", getCurrentUser())// 申请人是当前登录用户
				.addWhereCondition((templateId != null), "a.template.id=?", templateId)//
				.addWhereCondition((status != null && status.trim().length() > 0), "a.status=?", status)//
				.addOrderByProperty("a.applyTime", false)//
				.preparePageBean(userService, pageNum); // 注意service要是一个继承了DaoSupportImpl的类

		// 准备模板列表
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);

		return "myApplicationList";
	}

	// ============================== 审批人的功能 ===================================

	/** 待我审批 */
	public String myTaskList() throws Exception
	{
		List<TaskView> taskViewList = flowService.findMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception
	{
		List<Task> outcomes = flowService.getOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception
	{// 封装对象
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setApproval(approval); //
		approveInfo.setComment(comment);
		approveInfo.setApplication(flowService.getApplicationById(applicationId)); // 所属的申请
		approveInfo.setApprover(getCurrentUser()); // 审批人，当前登录用户
		approveInfo.setApproveTime(new Date()); // 审批时间，当前时间

		// 调用业务方法（保存审批信息，完成当前任务，维护申请的状态）
		flowService.approve(approveInfo, taskId, outcome);
		return "toMyTaskList";
	}

	/** 查看流转记录 */
	public String approvedHistory() throws Exception
	{
		List<ApproveInfo> approveInfoList = flowService.getApproveInfosByApplicationId(applicationId);
		ActionContext.getContext().put("approveInfoList", approveInfoList);
		return "approvedHistory";
	}

	/**
	 * @return the templateId
	 */
	public Long getTemplateId()
	{
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	/**
	 * @return the upload
	 */
	public File getUpload()
	{
		return upload;
	}

	/**
	 * @param upload
	 *            the upload to set
	 */
	public void setUpload(File upload)
	{
		this.upload = upload;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId()
	{
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(Long applicationId)
	{
		this.applicationId = applicationId;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public boolean isApproval()
	{
		return approval;
	}

	public void setApproval(boolean approval)
	{
		this.approval = approval;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getOutcome()
	{
		return outcome;
	}

	public void setOutcome(String outcome)
	{
		this.outcome = outcome;
	}

}
