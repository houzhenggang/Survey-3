import com.hanvon.survey.component.dao.i.BagMapper;
import com.hanvon.survey.component.dao.i.SurveyMapper;
import com.hanvon.survey.component.service.i.UserService;
import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.entities.guest.Survey;
import com.hanvon.survey.entities.guest.User;
import com.hanvon.survey.utils.DataProcessUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 汉王蓝天科技发展有限公司
 * Copyright (c):
 * 文件名：
 * 文件描述：
 * @author MiaoJianFei
 * @Date Created in 下午 13:23 2017/6/29 0029
 * </pre>
 */
public class DataSourceTest {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
    DataSource dataSource = ioc.getBean(DataSource.class);
    UserService userService = ioc.getBean(UserService.class);
    SurveyMapper surveyMapper = ioc.getBean(SurveyMapper.class);
    BagMapper bagMapper = ioc.getBean(BagMapper.class);



    @Test
    public void testCopyDeeply(){
        Bag sourceBag = new Bag(1, "a", 3, 4);
        Bag targetObject = (Bag) DataProcessUtils.copyDeeply(sourceBag);


        System.out.println(sourceBag.hashCode());
        System.out.println(targetObject.hashCode());
        System.out.println(sourceBag==(targetObject));



    }
    @Test
    public void testGetSurveyDeeply(){
        Survey surveyDeeply = surveyMapper.getSurveyDeeply(99);
        System.out.println(surveyDeeply);
        Set<Bag> bagSet = surveyDeeply.getBagSet();
        for(Bag bag :bagSet){
            System.out.println(bag);
            Set<Question> questionSet = bag.getQuestionSet();
            for (Question question :questionSet){
                System.out.println(question);
            }
        }

    }
    @Test
    public void testBagMapper(){
        Bag bag = bagMapper.getBagDeeply(2);
        System.out.println(bag);
        Set<Question> questionSet = bag.getQuestionSet();
        for(Question question :questionSet){
            System.out.println(question);
        }
    }
    @Test
    public void teCountSurvey() {
        int countSurvey = surveyMapper.countSurvey(46, false);
        System.out.println(countSurvey);
    }

    @Test
    public void querySurveyListTest(){
        List<Survey> surveys = surveyMapper.querySurveyList(46, true, 1, 3);
      for (Survey survey :surveys){
          System.out.println(survey.getSurveyId()+"==="+survey.getUserId()+"==="+survey.getSurveyName());
      }
    }

    @Test
    public void testRegist() throws Exception {
        System.out.println(UserService.class);
        System.out.println(userService.getClass());
        User user = new User(null, "7851", "123", true, "");

        userService.regist(user);

    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void testServletPath(){
        String servletPath = "/dasjflj/fdsafe/fewaf/fdaf/{fdasfd}";
        String serv = DataProcessUtils.cutServletPath(servletPath);
        System.out.println(serv);
    }


}
