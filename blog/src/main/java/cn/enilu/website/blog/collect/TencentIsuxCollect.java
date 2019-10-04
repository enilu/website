package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
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

import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
 * Created  on  2018/7/23 0023
 * TencentIsuxCollect
 *
 * @author enilu
 */
@Service
public class TencentIsuxCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(TencentIsuxCollect.class);
    public static final String SOURCE = "tencent_isux";
    public static final String AUTHOR = "腾讯ISUX";
    public static final String HOME = "https://isux.tencent.com";
    public static final String PAGE_URL = HOME+"/articles";

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
        this.collectAll();
    }

    @Override
    public void collectAll() {
        Document document = null;
        try {
            document = Jsoup.connect(PAGE_URL).timeout(60000).get();
        } catch (IOException e) {
            logger.error("获取腾讯文章异常",e);
            return ;
        }
        Elements elements = document.select((".article-list>a"));
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String url = HOME+element.attr("href");
            String key = Md5Util.getMD5String(url);
            if (collectCache.exist(key)) {
                continue;
            }
            Document record = null;
            try {
                 record = Jsoup.connect(url).timeout(60000).get();
            }catch (Exception e){
                logger.error("获取腾讯文章异常",e);
                continue;
            }
            String summary = record.select("meta[name=\"description\"]").first().attr("content");
            String title = record.select("meta[itemprop=\"name\"]").first().attr("content");
            Date publishDate = new Date();
            News news = new News();
            news.setAuthor(getAuthor());
            news.setSumm(summary);
            news.setTitle(title);
            news.setPv(0);
            news.setPublishDate(publishDate);
            news.setUrl(url);
            news.setCategory("设计");
            Set<String> tags = nlpService.tag(title, summary);
            news.setTag(StringUtil.join(",", tags.toArray()));
            logger.info(Json.toJson(news));
            newsService.save(news);
        }
    }
}
