package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.DateUtil;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created  on  2018/7/19 0019
 * RuanyifengCollect
 *
 * @author enilu
 */
@Service
public class RuanyifengCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(RuanyifengCollect.class);

    public static  final String SOURCE = "ruanyifeng";
    public static  final String AUTHOR = "阮一峰";
    public static  final String HOME = "http://www.ruanyifeng.com/blog";
    public static  final String PAGE_URL = "http://www.ruanyifeng.com/blog/archives.html";
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
        return true;
    }
    @Override
    public String getHome() {
        return HOME;
    }

    @Override
    public void collect() {
        collectAll();
    }

    @Override
    public void collectAll() {
        Document document = null;
        try {
             document = Jsoup.connect(PAGE_URL).timeout(60000).get();
        }catch (Exception e){
            logger.error("collect ruanyifeng error:{}",e.getMessage(),e);
            return ;
        }
        Elements elements = document.select("#beta-inner > .module > .module-content > .module-list > .module-list-item > a");
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String url = element.attr("href");
            String category = element.text();
            collectCategory(url);

        }
    }

    /**
     * 采集一个分类下的所有文章
     * @param url
     */
    public void collectCategory(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
        }catch (Exception e){
            logger.error("collect category error:{}",e.getMessage(),e);
            return ;
        }
        Elements elements = document.select("#alpha-inner > .module > .module-content > .module-list > .module-list-item > a");
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String title = element.text();
            String oneUrl = element.attr("href");
            try {
                collectOne(oneUrl);
            }catch (Exception e){
                logger.error("collect:{}error:{}",title,e.getMessage());
            }


        }
    }

    public void collectOne(String url){
        String key = Md5Util.getMD5String(url);
        if (collectCache.exist(key)) {
            return;
        }
        logger.info("开始采集:{}",url);
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
        }catch (Exception e){
            logger.error("collect category error:{}",e.getMessage(),e);
            return ;
        }
        String title = document.select("#page-title").first().text();
        String publishDateStr = document.select(".published").first().text();
        publishDateStr = StringUtil.cleanString(publishDateStr);
        Date publishDate = DateUtil.parse(publishDateStr,"yyyy年MM月dd日");
        String content = document.select("#main-content").text();

            String category = nlpService.category(title,content);
            Set<String> tags = nlpService.tag(title,content);
            News news = new News();
            news.setAuthor(getAuthor());
            String summary = content;
            if(content.length()>100){
                summary = content.substring(0,100);
            }
            news.setSumm(summary);
            news.setTitle(title);
            news.setPv(0);
            news.setUrl(url);
            news.setPublishDate(publishDate);
            news.setCategory(category);
            news.setTag(StringUtil.join(",",tags.toArray()));
            logger.info(news.getTitle());
            newsService.save(news);


    }

}
