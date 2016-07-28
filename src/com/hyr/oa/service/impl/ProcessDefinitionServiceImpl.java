package com.hyr.oa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hyr.oa.service.ProcessDefinitionService;
import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 流程定义Service实现
 */
@Service("processDefinitionServiceImpl")
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService
{
	@Resource
	private ProcessEngine processEngine;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 查询所有最新版本的流程定义列表
	 * 
	 * @return
	 * @throws AppException
	 */
	public List<ProcessDefinition> findAllLatestVersionList() throws AppException
	{
		// 查询所有流程定义的最新的版本存入List
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().list();
		return processDefinitions;
	}

	/**
	 * 删除指定Key的所有版本的流程定义
	 * 
	 * @param key
	 * @throws AppException
	 */
	public void deleteByKey(String key) throws AppException
	{
		// 1.查询出指定Key的所有版本的流程定义
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery().processDefinitionKey(key).list();

		// 2.循环删除
		for (ProcessDefinition pd : processDefinitions)
		{
			processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
		}
	}

	/**
	 * 部署流程定义（使用ZIP压缩包）
	 * 
	 * @param zipFile
	 * @throws AppException
	 */
	public void deployByZip(File zipFile) throws AppException
	{
		ZipInputStream zipInputStream = null;
		try
		{
			zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			processEngine.getRepositoryService().createDeployment().addZipInputStream(zipInputStream).deploy();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (zipInputStream != null)
			{
				// 关闭流资源
				try
				{
					zipInputStream.close();
					zipInputStream = null;
				} catch (IOException e)
				{
					e.printStackTrace();
					throw new AppException("com.hyr.oa.service.impl.ProcessDefinitionServiceImpl.deployByZip(File)");
				}
			}
		}
	}
	
	/**
	 * 获取指定流程定义的流程图片
	 * 
	 * @param id
	 * @return
	 */
	public InputStream getProcessDiagramResourceAsStream(String processDefinitionId) throws AppException{
		//多此一举
		/*ProcessDefinition pd=processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);*/
		
		//获取图片资源（根据流程定义的部署Id和数据库图片路径）
		processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
		return processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
		 
		//多此一举
		/*	processEngine.getRepositoryService()//
		.getResourceAsStream(pd.getDeploymentId(),pd.getDiagramResourceName());*/
	}

}
