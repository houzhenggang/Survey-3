package junit.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.utils.DataProcessUtils;

//该测试类，在不需要使用IOC容器时测试时使用。
public class SurveyTest {


	
	@Test
	public void testCreateTable(){
		for (int i = -30; i < 30; i++) {
			String tableName = DataProcessUtils.getGenerateTableName(i);
			System.out.println(tableName + " i="+i);
		}
		
	}
	
	
	@Test
	public void testBit(){
		int systemAllowedMaxCode0 = 1 << 29 ;
		int systemAllowedMaxCode1 = 1 << 30 ; //1073741824
		int systemAllowedMaxCode2 = 1 << 31 ; //-2147483648
		int systemAllowedMaxCode3 = 1 << 32 ; //1
		
		System.out.println(systemAllowedMaxCode0);
		System.out.println(systemAllowedMaxCode1);
		System.out.println(systemAllowedMaxCode2);
		System.out.println(systemAllowedMaxCode3);
	}
	
	
	@Test
	public void testCutServletPath(){
		//String servletPath = "/manager/res/showList";
		String servletPath = "/manager/res/showList/22/33";
		servletPath = DataProcessUtils.cutServletPath(servletPath);
		System.out.println(servletPath);
	}
	
	
	@Test
	public void testCopyDeeply(){
		Bag sourceBag = new Bag(1,"a",101,10);		
		sourceBag.getQuestionSet().add(new Question(11,"aaa",2,"",1));
		sourceBag.getQuestionSet().add(new Question(12,"bbb",1,"选项01,选项02",1));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Bag targetObject = (Bag) DataProcessUtils.copyDeeply(sourceBag);
		
		System.out.println("sourceBag == targetObject :" + (sourceBag == targetObject));
		
		System.out.println("sourceBag="+sourceBag);
		System.out.println(sourceBag.getQuestionSet());
		
		System.out.println("targetObject="+targetObject);
		System.out.println(targetObject.getQuestionSet());
		
		System.out.println(sourceBag.hashCode());
		System.out.println(targetObject.hashCode());
		
	}
	
	
	@Test
	public void testMd5(){
		String source = "123123";
		String newSource = DataProcessUtils.md5(source);
		System.out.println(newSource);
	}
	
	@Test
	public void md5() throws NoSuchAlgorithmException{		
		String source = "abc";
		byte[] bytes = source.getBytes();
		
		MessageDigest instance = MessageDigest.getInstance("MD5");
		byte[] newBytes = instance.digest(bytes); //已经进行加密后字节数组
	}
	
}
