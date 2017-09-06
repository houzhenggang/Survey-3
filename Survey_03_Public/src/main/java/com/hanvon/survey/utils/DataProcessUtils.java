package com.hanvon.survey.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.LoopTagStatus;

import com.hanvon.survey.entities.guest.Bag;
import com.hanvon.survey.entities.guest.Question;
import com.hanvon.survey.entities.manager.Auth;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.entities.manager.Role;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

//@SuppressWarnings("restriction")
public class DataProcessUtils {

	/**
	 * 将图片压缩按本来的长宽比例压缩为100宽度的jpg图片
	 * @param inputStream
	 * @param realPath /surveyLogos目录的真实路径，后面没有斜杠
	 * @return 将生成的文件路径返回 surveyLogos/4198393905112.jpg
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {
		
		OutputStream out = null;
		
		try {
			//构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			//获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();
			
			//计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;
			
			if(sourceWidth > 100) {
				//按比例压缩目标图片的尺寸
				targetWidth = 100;
				targetHeight = sourceHeight/(sourceWidth/100);
				
			}
			
			//创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			
			//绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
			
			//构造目标图片文件名
			//System.currentTimeMillis(); //从1970 1 1 
			String targetFileName = System.nanoTime() + ".jpg";
			
			//创建目标图片对应的输出流
			out = new FileOutputStream(realPath+"/"+targetFileName);
			
			//获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			
			//JPEG编码
			encoder.encode(targetImage);
			
			//返回文件名
			return "surveyLogos/"+targetFileName;
			
		} catch (Exception e) {
			
			return null;
		} finally {
			//关闭流
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	/**
	 * 采用MD5进行加密
	 * @param source 需要加密字符串
	 * @return 加密后的字符串；如果无法加密，返回null
	 */
	public static String md5(String source){
		//判断需要加密字符串是否为空
		if(source==null || source.length()==0){
			return null ;
		}
		
		//获取需要加密字符串的字节数组
		byte[] bytes = source.getBytes();		
		
		try {
			//获取用于加密工具类对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			//将需要加密的字符串的字节数组进行加密
			byte[] digestBytes = messageDigest.digest(bytes); //MD5加密后的字节数组长度固定：16个字节			
			
			return convertToString(digestBytes);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 将字节数组转换为字符串
	 * @param digestBytes 
	 * @return 
	 * 		加密后的密文：4297F44B13955235245B2497399D7A93
	 */
	private static String convertToString(byte[] digestBytes) {
		
		StringBuilder builder = new StringBuilder();
		
		int length = digestBytes.length; //固定值，长度16
		
		char [] codeArr = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		for (int i = 0; i < digestBytes.length; i++) { 
			
			byte codeByte = digestBytes[i];
			
			int lowCode = codeByte & 15 ; //低四位
			int hightCode = ( codeByte >> 4 ) & 15 ; //高四位
			
			//以高四位和低四位从字符池中来获取字符
			char lowChar = codeArr[lowCode];
			char hightChar = codeArr[hightCode];
			
			//将32字符拼串
			builder.append(hightChar).append(lowChar);
		}
		
		return builder.toString();
	}
	
	/**
	 * 深度赋值
	 * @param sourceObject 被深度复制的源对象
	 * @return targetObject 深度赋值后的目标对象
	 * @throws IOException 
	 */
	public static Serializable copyDeeply(Serializable sourceObject){
		
		Serializable targetObject = null ;
		
		//声明四个流变量
		ObjectOutputStream oos = null ;
		ObjectInputStream ois = null ;
		ByteArrayOutputStream baos = null ;
		ByteArrayInputStream bais = null ;		
		
		try {
			baos = new ByteArrayOutputStream(); //内存
			oos = new ObjectOutputStream(baos); //写流
			
			//序列化
			oos.writeObject(sourceObject); //将源对象写入到内存
			
			byte[] byteArray = baos.toByteArray(); //内存数据
			
			bais = new ByteArrayInputStream(byteArray); 
			ois = new ObjectInputStream(bais); //读流
			
			//反序列化
			targetObject = (Serializable) ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭流时，只需要将外层流关闭就可以了，内层流可以自动关闭；
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return targetObject ; //返回复制后目标对象
	}
	
	
	
	/**
	 * radio和checkbox回显问题：
	 * @param pageContext 当前JSP页面上下文对象
	 * @return 判断当前答案的选项是否被选中：需要回显返回checkec="checked";不需要回显返回"";
	 */
	public static String checkedReDisplay(PageContext pageContext){
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		
		//获取之前这个包裹提交答案
		//从Session域中获取allBagMap
		Map<Integer,Map<String,String[]>> allBagMap = 
					(Map<Integer,Map<String,String[]>>)session.getAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);
		
		//从请求域中获取Bag对象
		Bag currentBag = (Bag)request.getAttribute(GlobalSettings.REQUEST_NAME_CURRENT_BAG);
		
		//从Bag对象中获取bagId
		Integer bagId = currentBag.getBagId();
		
		//从allBagMap中根据bagId获取对应的paramMap
		Map<String, String[]> paramMap = allBagMap.get(bagId);

		
		//由于刚刚从入口进入参与调查页面时，尚未将任何一个paramMap保存到allBagMap中，所以paramMap可能为null
		if(paramMap==null){
			return "";
		}
		

		//在属性域中按照从小到大的顺序将question对象取出来
		//属性域：pageContext,request,session,application
		Question question = (Question)pageContext.findAttribute("question"); //var="question"
		
		//通过Question对象获取questionId
		Integer questionId = question.getQuestionId();
		
		//拼参数名称name属性的字符串
		String name = "q"+questionId; //name="q${question.questionId}"
		
		//根据paramName从paramMap中获取paramValueArr
		String[] paramValueArr = paramMap.get(name); //当前问题，之前填写的答案
		

		//如果第一次进入当前包裹时没有填写任何数据就离开了，那么paramValueArr就不存在，也就是null，所以为避免空指针异常，需要进行判断
		if(paramValueArr==null){
			return "";
		}		
		
		//获取当前标签的value属性值
		//获取LoopTagStatus对象
		LoopTagStatus status = (LoopTagStatus)pageContext.getAttribute("status");//varStatus="status"
		
		//获取index
		Integer index = status.getIndex();
		
		//将index转换为String类型作为value属性值
		String currentValue = index + "";

		//将paramValueArr转换为集合对象
		List<String> paramValueList = Arrays.asList(paramValueArr);

		//检查currentValue是否在paramValueList中
		if(paramValueList.contains(currentValue)){
			return "checked='checked'";
		}
		return "";
	}
	
	/**
	 * 文本域的表单回显
	 * @param pageContext
	 * @return 返回文本域需要回显的值
	 */
	public static String textReDisplay(PageContext pageContext){
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		
		//获取之前这个包裹提交答案
		//从Session域中获取allBagMap
		Map<Integer,Map<String,String[]>> allBagMap = 
					(Map<Integer,Map<String,String[]>>)session.getAttribute(GlobalSettings.SESSION_NAME_ALL_BAG_MAP);
		
		//从请求域中获取Bag对象
		Bag currentBag = (Bag)request.getAttribute(GlobalSettings.REQUEST_NAME_CURRENT_BAG);
		
		//从Bag对象中获取bagId
		Integer bagId = currentBag.getBagId();
		
		//从allBagMap中根据bagId获取对应的paramMap
		Map<String, String[]> paramMap = allBagMap.get(bagId);

		
		//由于刚刚从入口进入参与调查页面时，尚未将任何一个paramMap保存到allBagMap中，所以paramMap可能为null
		if(paramMap==null){
			return "";
		}		

		//在属性域中按照从小到大的顺序将question对象取出来
		//属性域：pageContext,request,session,application
		Question question = (Question)pageContext.findAttribute("question"); //var="question"
		
		//通过Question对象获取questionId
		Integer questionId = question.getQuestionId();
		
		//拼参数名称name属性的字符串
		String name = "q"+questionId; //name="q${question.questionId}"
		
		//根据paramName从paramMap中获取paramValueArr
		String[] paramValueArr = paramMap.get(name); //当前问题，之前填写的答案
		

		//如果第一次进入当前包裹时没有填写任何数据就离开了，那么paramValueArr就不存在，也就是null，所以为避免空指针异常，需要进行判断
		if(paramValueArr==null || paramValueArr.length==0){
			return "";
		}		

		return paramValueArr[0];
	}

	/**
	 * 将servletPath多余部分去掉
	 * 		/manager/res/showList
	 * 		/manager/auth/dispatcher/1
	 * 		/manager/auth/dispatcher/1/4
	 * 		只保留前三节，后面部分去掉；
	 * @param servletPath
	 * @return
	 */
	public static String cutServletPath(String servletPath) {
		
		String[] strPath = servletPath.split("/");
		
		String newServletPath = "/" + strPath[1] + "/" + strPath[2] + "/" +strPath[3] ;
		
		return newServletPath;
	}
	

	/**
	 * 用于计算用户（user/admin）的权限码
	 * @param roleSet  需要深入加载： 当前用户角色集合->权限集合->资源集合
	 * @param maxPos select max(resPos) from manager_res
	 * @return 当前用户权限码数组字符串
	 */
	public static String calculateCode(Set<Role> roleSet,Integer maxPos){

		//创建空的权限码数组：codeArr
		int[] codeArr = new int[maxPos+1]; //[0,0,0]

		//遍历roleSet，得到每一个role对象
		for (Role role : roleSet) {
			//通过role对象获取authSet
			Set<Auth> authSet = role.getAuthSet();
			//遍历authSet，得到每一个auth对象
			for (Auth auth : authSet) {
				//通过auth对象获取resSet
				Set<Res> resSet = auth.getResSet();
				//遍历resSet，得到每一个res对象
				for (Res res : resSet) { //当前用户的所有可以访问的资源；
					//通过res对象获取resCode和resPos
					Integer resPos = res.getResPos();
					Integer resCode = res.getResCode();
					//以resPos为数组下标，从codeArr中取出对应的元素：
					// oldValue （第一次，数组里没有，就使用默认值0）
					int oldValue = codeArr[resPos];
					//用resCode和oldValue做或运算，得到：newValue
					int newValue = resCode | oldValue ;
					//将newValue放回codeArr中原来的位置
					codeArr[resPos] = newValue; //[16,6,2]
				}				
			}			
		}
		
		StringBuilder builder = new StringBuilder();		
		
		//将当前用户权限码数组变为字符串存储
		for (int i = 0; i < codeArr.length; i++) {
			builder.append(",").append(codeArr[i]);
		}
		
		return builder.substring(1);  //"16,6,2"
	}

	//验证是否具有权限
	public static boolean checkedAuthority(String codeArrStr, Res res) {
		//获取资源权限位和权限码
		Integer resPos = res.getResPos();
		Integer resCode = res.getResCode();
		
		//获取用户的权限码数组
		String[] codeArray = codeArrStr.split(",");
		
		//根据权限位到权限码数组中获取该权限位上的权限码
		String codeForPos = codeArray[resPos];
		Integer code = Integer.parseInt(codeForPos);
		
		//将用于在该资源权限位上的权限码（用户拥有资源访问全信息）和资源权限码进行与运算；
		int isAuth = code & resCode ;		

		return isAuth!=0;
	}
	
	
	/**
	 * 用户创建表的名称的工具方法
	 * 
	 * 表名称规则：
	 * AUTO_LOG_YYYY_MM
	 * 	AUTO_LOG_2016_12
	 *  AUTO_LOG_2017_01
	 * 
	 * @param offset 月份偏移量
	 * 		-1 上一个月
	 * 		0 当前月
	 * 		1 下一个月
	 * @return 表的名称(表名称中不能含有"-"中杠)
	 */
	public static String getGenerateTableName(int offset){

		Calendar instance = Calendar.getInstance();
		
		instance.add(Calendar.MONTH,offset);
		
		Date time = instance.getTime();

		return "AUTO_LOG_" + new SimpleDateFormat("yyyy_MM").format(time).toString();
	} 
	
	
	
	
	
	
	
	
	
	
}
