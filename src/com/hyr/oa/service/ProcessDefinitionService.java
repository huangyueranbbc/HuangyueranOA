package com.hyr.oa.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.hyr.oa.util.AppException;

/**
 * 
 * @author Administrator
 * @category 流程定义Service接口
 */
public interface ProcessDefinitionService
{

	/**
	 * 查询所有最新版本的流程定义列表
	 * 
	 * @return
	 * @throws AppException
	 */
	List<ProcessDefinition> findAllLatestVersionList() throws AppException;

	/**
	 * 删除指定Key的所有版本的流程定义
	 * 
	 * @param key
	 * @throws AppException
	 */
	void deleteByKey(String key) throws AppException;

	/**
	 * 部署流程定义（使用ZIP压缩包）
	 * 
	 * @param zipFile
	 * @throws AppException
	 */
	void deployByZip(File zipFile) throws AppException;

	/**
	 * 获取指定流程定义的流程图片
	 * 
	 * @param id
	 * @return
	 */
	InputStream getProcessDiagramResourceAsStream(String processDefinitionId) throws AppException;

}
