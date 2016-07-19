package com.hyr.oa.util;

/**
 * @author Administrator
 * @category MD5加密工具类
 */
public class DigestUtils
{
	/**
	 * MD5加密
	 * 
	 * @param password
	 * @return
	 */
	public static String md5Hex(String password)
	{
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(password);
	}
}
