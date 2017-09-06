package com.hanvon.survey.log.thread.local;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebBinder {

	private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();
	
	public static void setBinder(HttpServletRequest request,HttpServletResponse response){
		localRequest.set(request);
		localResponse.set(response);
	}
	
	public static HttpServletRequest getBinderRequest(){
		return localRequest.get();
	}
	
	public static HttpServletResponse getBinderResponse(){
		return localResponse.get();
	}
	
	public static void removeBinder(){
		//local.set(null);
		localRequest.remove();
		localResponse.remove();
	}
}
