package junit.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hanvon.survey.component.dao.i.AdminMapper;
import com.hanvon.survey.component.dao.i.BagMapper;
import com.hanvon.survey.component.dao.i.LogMapper;
import com.hanvon.survey.component.dao.i.SurveyMapper;
import com.hanvon.survey.component.service.i.UserService;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.entities.manager.Auth;
import com.hanvon.survey.entities.manager.Log;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.entities.manager.Role;
import com.hanvon.survey.log.thread.local.RoutingKeyBinder;

public class IOCTest {

	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	
	//DataSource dataSource = ioc.getBean(DataSource.class);
	
	UserService userService = ioc.getBean(UserService.class);
	//UserServiceImpl userService = ioc.getBean(UserServiceImpl.class);
	
	SurveyMapper surveyMapper = ioc.getBean(SurveyMapper.class);
	
	BagMapper bagMapper = ioc.getBean(BagMapper.class);
	
	AdminMapper amdinMapper = ioc.getBean(AdminMapper.class);
	
	LogMapper logMapper =  ioc.getBean(LogMapper.class);
	
	
	@Test
	public void testTotalCountLog(){
		Set<String> logTables = logMapper.getLogTables();
		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		int totalCountForLog = logMapper.getTotalCountForLog(logTables);
		System.out.println("totalCountForLog="+totalCountForLog);

		RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		List<Log> logList = logMapper.getPage(logTables, 0, 5);
		System.out.println(logList);
	}
	
	@Test
	public void testGetLogTables(){
		//RoutingKeyBinder.setKey(RoutingKeyBinder.DATA_SOURCE_LOG);
		Set<String> logTables = logMapper.getLogTables();
		for (String tableName : logTables) {
			System.out.println(tableName);
		}
	}
	
	
	@Test
	public void testGetRoleDeeply(){
		Set<Role> roleSet = amdinMapper.getDeeplyRoleSet(2);
		for (Role role : roleSet) {
			System.out.println(role);
			Set<Auth> authSet = role.getAuthSet();
			for (Auth auth : authSet) {
				System.out.println(auth);
				Set<Res> resSet = auth.getResSet();
				for (Res res : resSet) {
					System.out.println(res);
				}
				System.out.println("-----------------");
			}
			System.out.println("-----------------");
		}
		
		
	}
	
	
	
	@Test
	public void testGetSurveyDeeply(){
		Survey surveyDeeply = surveyMapper.getSurveyDeeply(80);
		System.out.println(surveyDeeply);
		Set<Bag> bagSet = surveyDeeply.getBagSet();
		for (Bag bag : bagSet) {
			System.out.println(bag);
			
			Set<Question> questionSet = bag.getQuestionSet();
			for (Question question : questionSet) {
				System.out.println(question);
			}
		}
	}
	
	@Test
	public void testGetBagDeeply(){
		Bag bag = bagMapper.getBagDeeply(2);
		System.out.println(bag);
		
		Set<Question> questionSet = bag.getQuestionSet();
		for (Question question : questionSet) {
			System.out.println(question);
		}
	}
	
	@Test
	public void testCountSurvey(){
		int countSurvey = surveyMapper.countSurvey(27, false);
		System.out.println("count = "+countSurvey);
	}
	
	@Test
	public void testQuerySurveyList(){
		List<Survey> querySurveyList = surveyMapper.querySurveyList(26, false, 5, 5);
		for (Survey survey : querySurveyList) {
			System.out.println(survey.getSurveyId() + " - " + survey.getSurveyName());
		}
	}
	
	
	//实验2.测试注册功能
	@Test
	public void testRegist() throws Exception{
		//代理机制：
		//Spring给业务层增加事务时，采用的代理方式：
		//业务层有接口，采用JDK动态代理：class com.sun.proxy.$Proxy15
		// 业务层没有接口：采用Cglib动态代理？
		//class com.hanvon.survey.component.service.m.UserServiceImpl$$EnhancerByCGLIB$$945df1eb
		System.out.println(userService.getClass());
		
		User user = new User(null,"tianqi2","123",true,"");
		userService.regist(user);
	}
	
	//实验1.测试数据源
	@Test
	public void testDataSource() throws SQLException{
		
		/*Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
		
		connection.close();*/
	}
	
}
