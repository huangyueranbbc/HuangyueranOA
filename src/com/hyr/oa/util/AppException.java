package com.hyr.oa.util;

/**
 * @author Administrator
 * @category 自定义的Action异常处理类
 */
public class AppException extends Exception
{
	private static final long serialVersionUID = -1051457807753607621L;

	private int exceptionCode; // 异常编号
	private String message; // 异常信息

	/*
	 * 构造方法 初始化返回异常的信息
	 */
	public AppException(String message)
	{
		super(message);
		this.message = message;
	}

	/*
	 * 构造方法 初始化返回异常的信息和异常编号
	 */
	public AppException(int exceptionCode, String message)
	{
		super(message);
		this.exceptionCode = exceptionCode;
		this.message = message;
	}

	/*
	 * 返回详细的异常信息
	 */
	public String getDetailMessage()
	{
		String detailMessage = "Detail Message:" + exceptionCode + ":" + message;// 详细的异常信息
		return detailMessage;
	}

	public int getExceptionCode()
	{
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode)
	{
		this.exceptionCode = exceptionCode;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
