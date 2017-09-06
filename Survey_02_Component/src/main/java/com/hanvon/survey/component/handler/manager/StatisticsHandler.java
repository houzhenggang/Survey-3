package com.hanvon.survey.component.handler.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanvon.survey.component.service.i.EngageService;
import com.hanvon.survey.component.service.i.StatisticsService;
import com.hanvon.survey.component.service.i.SurveyService;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.page.Page;
import com.hanvon.survey.utils.GlobalSettings;

@Controller
public class StatisticsHandler {

	@Autowired
	private StatisticsService statisticsService ;
	
	@Autowired
	private EngageService engageService ;
	
	@Autowired
	private SurveyService surveyService ;
	
	
	@RequestMapping("/manager/statistics/exportExcel/{surveyId}")
	public void exportExcel(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletResponse response
			) throws IOException{
		//需要引入poi相关jar包
		HSSFWorkbook workbook =  statisticsService.getWorkBook(surveyId);
		
		//设置响应头
		//生成文件名称
		String filename = System.nanoTime() + ".xls";
		
		//设置响应内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//设置响应头
		//表示客户端浏览器以另存为窗口打开附件
		response.setHeader("Content-Disposition", "attachment;filename="+filename);		
		
		//将HSSFWorkbook写入到响应流中，将数据再响应给客户端浏览器(文件下载)
		ServletOutputStream sos = response.getOutputStream(); //字节流
		//PrintWriter writer = response.getWriter(); //字符流 		
		workbook.write(sos);		
		
	}
	
	@RequestMapping("/manager/statistics/showChartStatistics/{questionId}")
	public void showChartStatistics(
				@PathVariable("questionId") Integer questionId,
				HttpServletResponse response) throws IOException{
		
		//获取当前问题被参数统计图表
		JFreeChart jfreeChart = statisticsService.getChart(questionId);
		
		//通过response来获取响应输出流
		ServletOutputStream outputStream = response.getOutputStream();
		
		//通过输出流将JFreeChart图表数据返回给客户端浏览器
		ChartUtilities.writeChartAsJPEG(outputStream, jfreeChart, 1200, 800);
	}
	
	
	@RequestMapping("/manager/statistics/showTextStatistics/{questionId}")
	public String textStatistics(
			@PathVariable("questionId") Integer questionId,
			Map map){
		
		List<String> answerList =  statisticsService.queryTextStatistics(questionId);
		map.put("answerList", answerList);
		
		return "manager/statistics_textResult";
	}
	
	@RequestMapping("/manager/statistics/statisticsSummary/{surveyId}")
	public String statisticsSummary(
			@PathVariable("surveyId") Integer surveyId,
			Map map){
		
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		map.put("survey", survey);
		
		return "manager/statistics_summary";
	}

	@RequestMapping("/manager/statistics/showStatisticsList")
	public String showStatisticsList(@RequestParam(value="pageNoStr",required=false) String pageNoStr,Map map ){
		
		Page<Survey> page = engageService.queryAllAvailableSurvey(pageNoStr);
		map.put(GlobalSettings.PAGE, page);
		
		return "manager/statistics_list";
	}
	
}
