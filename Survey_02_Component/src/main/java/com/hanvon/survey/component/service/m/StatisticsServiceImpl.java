package com.hanvon.survey.component.service.m;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.AnswerMapper;
import com.hanvon.survey.component.dao.i.QuestionMapper;
import com.hanvon.survey.component.dao.i.SurveyMapper;
import com.hanvon.survey.component.service.i.StatisticsService;
import com.hanvon.survey.entities.guest.Answer;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.entities.guest.Survey;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private SurveyMapper surveyMapper ;
	
	@Autowired
	private AnswerMapper answerMapper ;
	
	@Autowired
	private QuestionMapper questionMapper ;

	@Override
	public List<String> queryTextStatistics(Integer questionId) {		
		return answerMapper.queryTextStatistics(questionId);
	}

	@Override
	public JFreeChart getChart(Integer questionId) {
		
		//①从请求中获取questionId
			
		//②根据questionId 查询Question对象
		Question question = questionMapper.selectByPrimaryKey(questionId);
			
		//③根据questioinId查询当前问题被参与的次数
		int questionEngagedCount = answerMapper.getQuestionEngagedCount(questionId);
		
		//④从Question对象中读取属性：
		//【1】questionName 问题名称
		String questionName = question.getQuestionName();
		
		//【2】optionArr 选项数组
		String[] questionOptionsToArr = question.getQuestionOptionsToArr();
		
		//在环境项目中引入JFreeChart类库
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		//⑤遍历选项数组
		for (int i = 0; i < questionOptionsToArr.length; i++) {
			//【1】选项本身
			String option = questionOptionsToArr[i];
			
			//【2】选项索引
			String paramIndex = "%," +i +",%";
			
			//⑥根据选项索引查询当前选项被选中的次数
			int optionEngagedCount = answerMapper.getOptionEngagedCount(paramIndex,questionId);
			
			//⑦将选项和选项参与的次数封装到Dataset对象中			
			dataset.setValue(option, optionEngagedCount);
		}
	
		//⑧创建JFreeChart对象
		String title = questionName + " " + questionEngagedCount + "次参与" ;
		JFreeChart pieChart = ChartFactory.createPieChart3D(title, dataset);
		
		//⑨对JFreeChart对象进行必要的修饰设置
		pieChart.getTitle().setFont(new Font("宋体", Font.BOLD, 50));		
		pieChart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 40));		
		PiePlot piePlot = (PiePlot)pieChart.getPlot();
		piePlot.setLabelFont(new Font("宋体", Font.BOLD, 28));			
		piePlot.setForegroundAlpha(0.6F);		
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0},{1}/{3},{2}"));	
		
		return pieChart;
	}

	@Override
	public HSSFWorkbook getWorkBook(Integer surveyId) {
		//一、查询需要用到的数据
		//[1]页面提供surveyId	
		
		//[2]根据surveyId查询Survey对象(深度加载) survey-Set<Bag> -> Set<Question>
		Survey survey = surveyMapper.getSurveyDeeply(surveyId);		
		
		//[3]从Survey对象中获取surveyName
		String surveyName = survey.getSurveyName();
		
		//[4]从Survey对象中获取Set<Bag>
		Set<Bag> bagSet = survey.getBagSet();
		
		//[5]通过Set<Bag>创建List<Question>；将所有包裹问题合并在一起
		List<Question> questionList = new ArrayList<Question>();
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			questionList.addAll(questionSet);
		}		
		
		
		//[6]根据surveyId查询调查被参与的次数
		int surveyEngageCount = answerMapper.getSurveyEngageCount(surveyId);		
		
		//[7]根据surveyId查询调查范围内的所有答案
		List<Answer> answerList =  answerMapper.getAllAnswerBySurveyId(surveyId);		

		//二、处理查询得到的数据:DB->Map
		//[1].创建空的bigMap
		//Map<uuid,Map<questionId,conent>> bigMap ;
		//Map<questionId,conent> smallMap ;
		Map<String,Map<Integer,String>> bigMap = new HashMap<String,Map<Integer,String>>();
		
		//[2].遍历answerList	
		for (Answer answer : answerList) {
			//[3].从每一个Answer对象中取出需要用到的数据	
			String uuid = answer.getUuid();
			
			Integer questionId = answer.getQuestionId();
			String content = answer.getContent();
			
			//---构建Map集合-------------			
			//首先从bigMap中获取smallMap，如果不存在，说明当前smallMap中还没有存储任何一个key/value ;
			Map<Integer,String> smallMap = bigMap.get(uuid);
			if(smallMap == null){
				smallMap = new HashMap<Integer,String>(); 
				bigMap.put(uuid, smallMap);
			}
			//将答案存放到smallMap中	
			smallMap.put(questionId, content);
		}
		

		//三、根据准备好的数据创建工作表:Map->Excel
		//[1]创建HSSFWorkbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		//[2]创建HSSFSheet，设置工作区名称
		String sheetName = surveyName + " " + surveyEngageCount +" 次参与" ;
		HSSFSheet createSheet = workbook.createSheet(sheetName);
		//[3]第一行的内容是所有问题名称(创建行，循环创建单元格)
		HSSFRow createRow = createSheet.createRow(0);
		
		//需要循环将第一行表头创建出来
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			String questionName = question.getQuestionName();
			//Integer questionId = question.getQuestionId();
			
			int cellIndex = i;
			
			HSSFCell createCell = createRow.createCell(cellIndex);
			createCell.setCellValue(questionName);
		}
		
		int rowIndex = 1;
		Set<Entry<String, Map<Integer, String>>> bigMapSet = bigMap.entrySet();  //参与统计次数  uuid - > 每一次

		Iterator<Entry<String, Map<Integer, String>>> iterator = bigMapSet.iterator();
		while(iterator.hasNext()){

			//[4]生成后续每一行
			HSSFRow row = createSheet.createRow(rowIndex);
			
			Entry<String, Map<Integer, String>> entry = iterator.next();
			//String keyUUID = entry.getKey();
			Map<Integer, String> smallMap = entry.getValue(); //一行数据
			
			for (int j = 0; j < questionList.size(); j++) {
				//[5]以当前循环变量的值为索引创建当前单元格
				int cellIndex = j ;
				int questionListIndex = cellIndex ;
				HSSFCell cell = row.createCell(cellIndex);
				//[6]取出对应的Question对象
				Question question = questionList.get(questionListIndex);
				//[7]取出当前Question对象的questionId
				Integer questionId = question.getQuestionId();
				//[8]以questionId为键从smallMap中获取对应的答案内容
				String content = smallMap.get(questionId);
				
				//[9]将答案内容设置到当前单元格中
				cell.setCellValue(content);
			}
			rowIndex ++ ;
		}	
		
		return workbook;
	}

	/*@Override
	public Page<Survey> queryAllAvailableSurvey(String pageNoStr) {
		
		return null;
	}*/
	
}
