package cn.enilu.website.blog.service;

import com.baidu.aip.nlp.AipNlp;
import org.ansj.app.summary.SummaryComputer;
import org.ansj.app.summary.pojo.Summary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created  on  2018/7/19 0019
 * NlpService
 *
 * @author enilu
 */
@Service
public class NlpService {
    @Value("${api.baidu.nlp.app.id}")
    private String appId;
    @Value("${api.baidu.nlp.app.key}")
    private String appKey;
    @Value("${api.baidu.nlp.secret.key}")
    private String secretKey;

    AipNlp client = null;

    public Set<String> tag(String title, String content){
        Set<String> tags = new HashSet<String>(20);
        HashMap<String, Object> options = new HashMap<String, Object>();
        // 文章标签
        JSONObject res = client().keyword(title, content, options);
        if(res.toString(2).contains("items")) {
            JSONArray jsonArray = res.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                String tag = jsonArray.getJSONObject(i).getString("tag");
                tags.add(tag);
            }
            return tags;
        }
        return tags;
    }

    public String category(String title,String content){
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client().topic(title, content, options);
        if(res.toString(2).contains("item")) {
            String category = res.getJSONObject("item").getJSONArray("lv1_tag_list").getJSONObject(0).getString("tag");
            return category;
        }
        return "未知";
    }
    public String getSummary(String title,String content){
        SummaryComputer summaryComputer = new SummaryComputer(500, title, content);
        Summary summary = summaryComputer.toSummary();
        return summary.getSummary();
    }

    private   AipNlp client(){
        if(client==null){
            client = new AipNlp(appId  ,appKey,secretKey);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
        }
        return client;
    }
}
