package com.hyr.oa.model;

import java.util.List;

public class PageBean
{
	// 传递的参数或是配置的参数
	private int currentPage; // 当前页
	private int pageSize; // 每页显示多少条记录

	// 查询数据库
	private List recordList; // 本页的数据列表
	private int recordCount; // 总记录数

	// 计算
	private int pageCount; // 总页数
	private int beginPageIndex; // 页码列表的开始索引（包含）
	private int endPageIndex; // 页码列表的结束索引（包含）

	/**
	 * 只接受4个必要的属性，会自动的计算出其他3个属性的值
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param recordList
	 * @param recordCount
	 */
	public PageBean(int currentPage, int pageSize, List recordList, int recordCount)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordList = recordList;
		this.recordCount = recordCount;

		// 计算 pageCount
		pageCount = (recordCount + pageSize - 1) / pageSize;

		// 计算 beginPageIndex 与 endPageIndex
		// >> 总页码小于等于10页时，全部显示
		if (pageCount <= 10)
		{
			if (pageCount == 0)
			{
				pageCount = 1;
				beginPageIndex = 1;
				endPageIndex = pageCount;
				;
			} else
			{
				beginPageIndex = 1;
				endPageIndex = pageCount;
			}

		}
		// >> 总页码大于10页时，就只显示当前页附近的共10个页码
		else
		{
			// 默认显示 前4页 + 当前页 + 后5页
			beginPageIndex = currentPage - 4; // 7 - 4 = 3;
			endPageIndex = currentPage + 5; // 7 + 5 = 12; --> 3 ~ 12

			// 如果前面不足4个页码时，则显示前10页
			if (beginPageIndex < 1)
			{
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 如果后面不足5个页码时，则显示后10页
			else if (endPageIndex > pageCount)
			{
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 9;
			}
		}
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * @return the recordList
	 */
	public List getRecordList()
	{
		return recordList;
	}

	/**
	 * @param recordList
	 *            the recordList to set
	 */
	public void setRecordList(List recordList)
	{
		this.recordList = recordList;
	}

	/**
	 * @return the recordCount
	 */
	public int getRecordCount()
	{
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            the recordCount to set
	 */
	public void setRecordCount(int recordCount)
	{
		this.recordCount = recordCount;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount()
	{
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	/**
	 * @return the beginPageIndex
	 */
	public int getBeginPageIndex()
	{
		return beginPageIndex;
	}

	/**
	 * @param beginPageIndex
	 *            the beginPageIndex to set
	 */
	public void setBeginPageIndex(int beginPageIndex)
	{
		this.beginPageIndex = beginPageIndex;
	}

	/**
	 * @return the endPageIndex
	 */
	public int getEndPageIndex()
	{
		return endPageIndex;
	}

	/**
	 * @param endPageIndex
	 *            the endPageIndex to set
	 */
	public void setEndPageIndex(int endPageIndex)
	{
		this.endPageIndex = endPageIndex;
	}

}
