package com.hyr.oa.view.action;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.model.Application;
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
		List<TaskView> list = flowService.findMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("list", list);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception
	{
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception
	{
		return "toMyTaskList";
	}

	/** 查看流转记录 */
	public String approvedHistory() throws Exception
	{
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

}
