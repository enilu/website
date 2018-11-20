package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.DateUtil;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created  on  2018/7/22 0022
 * BaiduUedCollect
 *
 * @author enilu
 */
@Service
public class BaiduUedCollect extends AbstractCollect {


    private Logger logger = LoggerFactory.getLogger(BaiduUedCollect.class);
    public static  final String SOURCE = "baidu_ued";
    public static  final String AUTHOR = "百度用户体验中心";
    public static  final String HOME = "http://ued.baidu.com";
    public static final String PAGE_URL = "http://ued.baidu.com/post_json.php?postsPerPage=35&pageNumber=1";


    @Override
    public String getSource() {
        return SOURCE;
    }

    @Override
    public String getAuthor() {
        return AUTHOR;
    }

    @Override
    public boolean getState() {
        return false;
    }
    @Override
    public String getHome() {
        return HOME;
    }

    @Override
    public void collect() {

    }

    @Override
    public void collectAll() {
        Document document = null;
        try{
            JSONObject jsonObject = new JSONObject(BaiduUedConstant.json);
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                String title = json.getString("title");
                String url = json.getString("link");
                try {
                    getContent(title, url);
                }catch (Exception e){
                    logger.error("get url:{}",url,e);
                }

            }
            logger.info(jsonObject.toString());
        }catch (Exception e){
            logger.error("collect error",e);
        }
    }

    private void getContent(String title, String url) {
        String key = Md5Util.getMD5String(url);
        if (collectCache.exist(key)) {
            return;
        }

            Document document = null;
            try{
                document = Jsoup.connect(url).timeout(60000).get();
            }catch (Exception e){
                logger.error("collect one:{} error",url,e);
            }
            String content = document.select(".article-content").first().text();
            String publishDateStr = document.select(".article-time").first().text();
            News news = new News();
            news.setAuthor(getAuthor());
            news.setPublishDate(DateUtil.parse(publishDateStr,"yyyy.MM.dd"));
            String summary = content.length()>100?content.substring(0,100):content;
            news.setSumm(summary);
            news.setTitle(title);
            news.setPv(0);
            news.setUrl(url);
            news.setCategory("设计");
            Set<String> tags = nlpService.tag(title,content);
            tags.add(news.getCategory());
            news.setTag(StringUtil.join(",",tags.toArray()));
            newsService.save(news);


    }
}
