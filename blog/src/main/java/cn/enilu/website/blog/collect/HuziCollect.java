package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.DateUtil;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HuziCollect
 *
 * @author zt
 * @version 2018/8/6 0006
 */
@Service
public class HuziCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(HuziCollect.class);
    public static final String PAGE_URL = "http://www.barretlee.com/blog/categories/JavaScript";
    public static  final String WWW_HOME = "http://www.barretlee.com";

    @Override
    public void collect() {
        Document document = null;
        try {
            document = Jsoup.connect(getHome()).timeout(60000).get();
        }catch (Exception e){
            logger.info("collect {} error",getHome(),e);
        }
        Elements elements = document.select(".entry-recent-posts > ul > li >a");
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String url = WWW_HOME+element.attr("href");
            String title = element.text();
            try {
                collectOne(title,url);
            }catch (Exception e){
                logger.error("parse one error:{}",url,e);
            }
        }
    }

    @Override
    public void collectAll() {
        String url = "http://www.barretlee.com/blog/categories/JavaScript/";
        Document document = null;
        try {
            document = Jsoup.connect(getHome()).timeout(60000).get();
        }catch (Exception e){
            logger.info("collect one {} error",url,e);
            return ;
        }
        Elements elements = document.select(".entry-cates > ul >li > a");
        for(int i=0;i<elements.size()-1;i++){
            Element element = elements.get(i);
            String listUrl = WWW_HOME+element.attr("href");
            String listTitle = element.text();
            logger.info(listTitle+"--"+listUrl);

            collectPage(listTitle,listUrl);
        }

    }
    private void collectPage(String category,String url){

        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
        }catch (Exception e){
            logger.info("collect one {} error",url,e);
            return ;
        }
        logger.info("1");

    }
    private void collectOne(String title, String url){

        logger.info("url:{}",url);
        Document document = null;
        try {
            document = Jsoup.connect(getHome()).timeout(60000).get();
        }catch (Exception e){
            logger.info("collect one {} error",url,e);
            return ;
        }
        String publishDateStr = document.select("time").first().text();
        Date publishDate  = DateUtil.parse(publishDateStr,"yyyy-MM-dd");
        String  category = getCategory(document.select(".category-link").first().text());

        String key = Md5Util.getMD5String(url);
        if (collectCache.exist(key)) {
            logger.warn("【已经采集过】{}",title);
            return;
        }
        logger.warn("【没有采集】{}",title);

        News news = new News();
        news.setAuthor(getAuthor());

        news.setTitle(title);
        news.setPv(0);
        news.setPublishDate(publishDate);
        news.setUrl(url);
        news.setUrlKey(key);
        news.setCategory(category);
        Set<String> tags = new HashSet<>();

        tags.add(news.getCategory());

        news.setTag(StringUtil.join(",", tags.toArray()));
        logger.info(Json.toJson(news));
//        newsService.save(news);


    }

    public String getCategory(String category){
        String result = "科技";
        switch (category){
            case "Linux":
            case "网络安全":
                result = "运维";
                break;
            case "随笔":
                result = "旅游";
                break;
            case "后端杂烩":
                result = "后端";
                break;
            default:
                result = "前端";
                break;
        }
        return result;
    }
}
