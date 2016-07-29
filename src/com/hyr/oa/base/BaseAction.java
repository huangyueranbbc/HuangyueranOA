package com.hyr.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.hyr.oa.model.User;
import com.hyr.oa.service.DepartmentService;
import com.hyr.oa.service.ForumService;
import com.hyr.oa.service.PrivilegeService;
import com.hyr.oa.service.ProcessDefinitionService;
import com.hyr.oa.service.ReplyService;
import com.hyr.oa.service.RoleService;
import com.hyr.oa.service.TemplateService;
import com.hyr.oa.service.TopicService;
import com.hyr.oa.service.UserService;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport
{
	protected int pageNum = 1; // 页面获取的当前页 默认为第一页。

	// ===================== 声明Service ====================
	@Resource(name = "roleServiceImpl")
	protected RoleService roleService;
	@Resource(name = "departmentServiceImpl")
	protected DepartmentService departmentService;
	@Resource(name = "userServiceImpl")
	protected UserService userService;
	@Resource(name = "privilegeServiceImpl")
	protected PrivilegeService privilegeService;
	@Resource(name = "forumServiceImpl")
	protected ForumService forumService;
	@Resource(name = "topicServiceImpl")
	protected TopicService topicService;
	@Resource(name = "replyServiceImpl")
	protected ReplyService replyService;
	@Resource(name = "processDefinitionServiceImpl")
	protected ProcessDefinitionService processDefinitionService;
	@Resource(name = "templateServiceImpl")
	protected TemplateService templateService;

	// ========================== 工具方法 ==========================

	/**
	 * 获取当前登录的用户
	 */
	public User getCurrentUser() throws AppException
	{
		return (User) ActionContext.getContext().getSession().get("user");
	}

	/**
	 * 获取客户户的IP地址
	 * 
	 * @return
	 */
	public String getRequestIP() throws AppException
	{
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	/**
	 * 保存上传的文件，并返回在服务器端真实的存储路径
	 * 
	 * @param upload
	 * @return
	 */
	protected String saveUploadFile(File upload)
	{
		// >> 1, 得到在保存的文件路径的真实地址
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_files");
		String datePath = sdf.format(new Date());

		// >> 2, 如果文件夹不存在，就创建
		File dir = new File(basePath + datePath);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		String path = basePath + datePath + UUID.randomUUID().toString()+System.currentTimeMillis(); // 注意同名的问题，可以使用uuid做为文件名 
		File destFile = new File(path);

		// >> 3, 移动文件
		upload.renameTo(destFile);
		return path;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum()
	{
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}
}
