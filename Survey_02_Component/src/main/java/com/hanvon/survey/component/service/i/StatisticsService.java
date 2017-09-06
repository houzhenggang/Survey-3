package com.hanvon.survey.component.service.i;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

public interface StatisticsService {

	List<String> queryTextStatistics(Integer questionId);

	JFreeChart getChart(Integer questionId);

	HSSFWorkbook getWorkBook(Integer surveyId);

	//Page<Survey> queryAllAvailableSurvey(String pageNoStr);

}
