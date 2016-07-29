package com.hyr.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hyr.oa.base.BaseAction;
import com.hyr.oa.util.AppException;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction
{

	private String key;
	private File upload; // 上传的文件
	private String id; //
	private InputStream inputStream; // 下载用的流

	/** 列表 显示所有最新版本的流程定义 */
	public String list() throws AppException
	{
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersionList();
		System.out.println("长度为：" + processDefinitionList.size());
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "list";
	}

	/**
	 * 删除 删除指定Key的所有版本的流程定义
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public String delete() throws AppException, UnsupportedEncodingException
	{
		processDefinitionService.deleteByKey(key);
 
		return "toList";
	}

	/** 添加页面(部署页面) */
	public String addUI() throws AppException
	{
		return "addUI";
	}

	/** 添加(部署) */
	public String add() throws AppException
	{
		processDefinitionService.deployByZip(upload);
		return "toList";
	}

	/** 查看流程图(xxx.png) */
	public String showProcessImage() throws AppException
	{

		try
		{
			// 自己将客户端传递的URL编码的数据进行解码

			inputStream = processDefinitionService.getProcessDiagramResourceAsStream(id);
			System.out.println(inputStream);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new AppException("com.hyr.oa.view.action.ProcessDefinitionAction.showProcessImage()");
		}
		return "downloadProcessImage";
	}

	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key)
	{
		this.key = key;
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
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
