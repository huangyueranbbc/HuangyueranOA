package com.hyr.oa.service.impl;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hyr.oa.base.DaoSupportImpl;
import com.hyr.oa.model.Template;
import com.hyr.oa.service.TemplateService;
import com.hyr.oa.util.AppException;

@Service("templateServiceImpl")
@Transactional
public class TemplateServiceImpl extends DaoSupportImpl<Template> implements TemplateService
{

	/**
	 * 除了删除数据库记录 增加删除目录文件功能
	 */
	public void delete(Long id) throws AppException
	{
		Template template = getById(id);
		getSession().delete(template);

		File file = new File(template.getPath());
		if (file.exists())
		{
			file.delete();
		}

	}

}
