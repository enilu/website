package cn.enilu.website.nlp;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created  on  2018/7/19 0019
 * BaiduTest
 *
 * @author enilu
 */
public class BaiduTest {
    public static final String APP_ID = "11555761";
    public static final String API_KEY = "Tt3yACl8Qr9DmuDFaTCGbhGs";
    public static final String SECRET_KEY = "ifCMCwpuzAturDEa3v7vxZHuvA2zyHGd";
    // 初始化一个AipNlp
    AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

    @Before
    public void before(){

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }
    @Test
    public void test(){


        // 调用接口
        String text = "百度是一家高科技公司";
        JSONObject res = client.lexer(text, null);
        System.out.println(res.toString(2));
    }
    @Test
    public void tag(){
        String title = "iphone手机出现“白苹果”原因及解决办法，用苹果手机的可以看下";
        String content = "如果下面的方法还是没有解决你的问题建议来我们门店看下成都市锦江区红星路三段99号银石广场24层01室。";

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 文章标签
        JSONObject res = client.keyword(title, content, options);
        JSONArray jsonArray = res.getJSONArray("items");
        List<String> tags = new ArrayList<>(20);
        for(int i=0;i<jsonArray.length();i++){
            String tag = jsonArray.getJSONObject(i).getString("tag");
            tags.add(tag);
        }
        System.out.println(res.toString(2));

    }
    @Test
    public void category(){
        String title = "小程序开发入坑之旅";
        String content = "小程序开发入坑之旅";

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 文章分类
        JSONObject res = client.topic(title, content, options);

        System.out.println(res.toString(2));
        String category = res.getJSONObject("item").getJSONArray("lv1_tag_list").getJSONObject(0).getString("tag");
        System.out.println(category);
    }
}
