package com.hyr.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.ModelDrivenBaseAction;
import com.hyr.oa.model.Template;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TemplateAction extends ModelDrivenBaseAction<Template>
{
	private static final long serialVersionUID = -1378093460869151841L;

	private File upload; // 上传的文件
	private InputStream inputStream; // 下载的流

	/** 列表 */
	public String list() throws AppException
	{
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);
		return "list";
	}

	/** 添加页面 */
	public String addUI() throws AppException
	{
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersionList();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);

		return "addUI";
	}

	/** 添加 */
	public String add() throws AppException
	{
		Template template = new Template();
		template.setName(model.getName());
		template.setProcessKey(model.getProcessKey());

		// >> 处理上传的文件
		// >> 1. 得到保存文件的路径的真实地址
		// >> 2. 移动文件
		String path = saveUploadFile(upload);
		// >> 3. 封装到javabean中
		template.setPath(path);

		// 保持到数据库
		templateService.save(template);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws AppException
	{
		// 准备回显数据
		Template template = templateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(template);

		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersionList();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);

		return "editUI";
	}

	/** 修改 */
	public String edit() throws AppException
	{
		// 1. 从数据库中获取原对象
		Template template = templateService.getById(model.getId());

		// 2.如果上传了文件 才删除原文件
		if (upload != null)
		{ // 如果上传了文件，才表示要修改文件模板内容
			// 删除老文件
			File file = new File(template.getPath());
			if (file.exists())
			{
				file.delete();
			}
			// 3.保存文件
			String path = saveUploadFile(upload);
			template.setPath(path);
		}

		// 4.设置属性
		template.setName(model.getName());
		template.setProcessKey(model.getProcessKey());

		// 5. 更新到数据库中
		templateService.update(template);

		return "toList";
	}

	/** 删除 */
	public String delete() throws AppException
	{
		templateService.delete(model.getId());
		return "toList";
	}

	/** 下載 */
	public String download() throws AppException
	{
		try
		{
			Template template = templateService.getById(model.getId());

			String fileName = template.getName();
			fileName = URLEncoder.encode(fileName, "utf-8"); // 解决文件下载的文件名中文问题
			ActionContext.getContext().put("fileName", fileName);
			inputStream = new FileInputStream(template.getPath());
		} catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new AppException("com.hyr.oa.view.action.TemplateAction.download()");
		}
		return "download";
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
	 * @return the inputStream
	 */
	public InputStream getInputStream()
	{
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

}
