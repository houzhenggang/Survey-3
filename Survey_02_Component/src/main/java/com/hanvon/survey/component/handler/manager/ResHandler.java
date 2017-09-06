package com.hanvon.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.entities.manager.Res;

@Controller
public class ResHandler {
	
	@Autowired
	private ResService resService;

	@ResponseBody
	@RequestMapping("/manager/res/updateStatus")
	public Map<String,String> updateStatus(@RequestParam(value = "resId" ,required = false ) Integer resId){
		Map<String,String> map = new HashMap<String,String>();
		boolean finalStatus ;
		try {		
			finalStatus = resService.updateStatus(resId);	
			map.put("finalStatus", finalStatus+"");			
			map.put("message", "success");
		} catch (Exception e) { 
			e.printStackTrace();	
			map.put("message", "failed");
		}
		
		return map;
	}
	
	
	@RequestMapping("/manager/res/batchDelete")
	public String batchDelete(@RequestParam(value="resIdList",required=false) List<Integer> resIdList){
		
		if(resIdList!=null &&  resIdList.size()>0){
			resService.deleteBatch(resIdList);
		}
		
		return "redirect:/manager/res/showList";
	}
	
	@RequestMapping("/manager/res/showList")
	public String showList(Map<String, Object> map) {
		
		List<Res> resList = resService.getAll();
		map.put("resList", resList);
		
		return "manager/res_list";
	}
}
