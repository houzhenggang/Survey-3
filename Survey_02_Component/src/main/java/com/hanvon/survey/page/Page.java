package com.hanvon.survey.page;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private List<T> dataList = new ArrayList<T>(); //数据
	private int pageSize = 5 ; //每页条数
	private int pageNo ; //当前页
	private int totalRecordSize ; //总记录数
	private int totalPageNo ; //总页码
	
	public Page(String pageNoStr,int totalRecordSizeParam){
		//将总记录数赋值
		totalRecordSize = totalRecordSizeParam ;
		
		//计算总页码
		totalPageNo = totalRecordSizeParam/pageSize + ((totalRecordSizeParam%pageSize == 0) ? 0 : 1) ;
		
		//修正页码
		pageNo = 1 ;
		
		try {
			//将类型进行转换
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {/*出现类型转换异常，不做任何处理*/}
		
		//如果页面参数大于总页码，修正为最大页码
		if(pageNo > totalPageNo){
			// (pageNo-1)*pageSize   =>>   (0-1)*5 = -5  开始索引不能为负值
			// select * from guest_survey where user_id=1 and completed=false limit -5,5
			pageNo = totalPageNo ; 
 		}
		
		//修正pageNo的值：1~总页码
		if(pageNo < 1){
			pageNo = 1 ;
		}
				
	}

	public List<T> getDataList() {
		return dataList;
	}

	//get/set方法体现对象的封装特殊，set需要考虑是否提供？
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalRecordSize() {
		return totalRecordSize;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}
	
	//-----------------------------------
	//特殊方法：
	//判断是否有上一页和首页
	public boolean isHasPrev(){
		return pageNo > 1 ;
	}
	
	//判断是否有下一页和末页
	public boolean isHasNext(){
		return pageNo < totalPageNo ;
	}
	
	//获取上一页页码
	public int getPrev(){
		return pageNo - 1 ;
	}
	
	//获取下一页页码
	public int getNext(){
		return pageNo + 1 ;
	}
	
	public int getIndex(){
		return (pageNo-1)*pageSize ;
	}
}
