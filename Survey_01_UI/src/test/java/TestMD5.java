import com.hanvon.survey.utils.DataProcessUtils;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * 汉王蓝天科技发展有限公司
 * Copyright (c):
 * 文件名：
 * 文件描述：
 * @author MiaoJianFei
 * @Date Created in 下午 17:07 2017/7/3 0003
 * </pre>
 */
public class TestMD5 {

    @Test
    public void testMD501(){
        String source ="123";
        System.out.println(DataProcessUtils.md5(source));

    }
    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        String source = "55saf";
        byte[] bytes = source.getBytes();
        MessageDigest instance = MessageDigest.getInstance("MD5");
        byte[] digest = instance.digest(bytes);
        System.out.println(digest);
    }


}
