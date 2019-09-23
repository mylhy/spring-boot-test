package com.pro.util;
/**
 * 基础工具糊糊
 * @author Administrator
 *
 */

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BaseUtils {

	/**
	 * 获取请求中指定前缀的数据
	 */
	public static Map<String, String> getRequestParam(HttpServletRequest request,String prefix){
		Map<String, String> result=new HashMap<String,String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements())    
        {
  		  String t=paramNames.nextElement();
  		  if(t.indexOf(prefix) != -1) {
  			result.put(t, request.getParameter(t));
  		  }
        }
		return result;
	}
}
