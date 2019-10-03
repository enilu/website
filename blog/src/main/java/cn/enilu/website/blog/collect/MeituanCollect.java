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
 * Created  on  2018/7/22 0022
 * MeituanCollect
 *
 * @author enilu
 */
@Service
public class MeituanCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(MeituanCollect.class);

    public static  final String SOURCE = "meituan";
    public static  final String AUTHOR = "美团点评技术博客";
    public static  final String HOME = "https://tech.meituan.com";
    public static  final String PAGE_URL = "https://tech.meituan.com/archives";
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
            logger.error("collect page:{} error",PAGE_URL);
        }
        Elements elements = document.select(".post-title");
        for(int i=0;i<(elements.size()>20?20:elements.size());i++){
            try{
            Element element = elements.get(i);
            Element titleElement = element.select("a").first();
            String url = titleElement.attr("href");
            String publishDateStr = url.substring(1,11);
            String title = titleElement.text();

            if(!url.startsWith("http")){
                url  = HOME+url;
            }
                String key = Md5Util.getMD5String(url);
                if (collectCache.exist(key)) {
                    continue;
                }
                try {
                    Document detailDoc =  Jsoup.connect(url).timeout(60000).get();
                    String summary = detailDoc.select("meta[name=\"description\"]").first().attr("content");
                    Date publishDate = DateUtil.parse(publishDateStr, "yyyy/MM/dd");
                    News news = new News();
                    news.setAuthor(getAuthor());
                    news.setSumm(summary);
                    news.setTitle(title);
                    news.setPv(0);
                    news.setPublishDate(publishDate);
                    news.setUrl(url);
                    news.setCategory("后端");
                    Set<String> tags = nlpService.tag(title, summary);
                    news.setTag(StringUtil.join(",", tags.toArray()));
                    newsService.save(news);
                } catch (Exception e) {
                    logger.error("parse url:{} error", url, e);
                }


            }catch (Exception e){
                logger.error("collect url:{} error",e.getMessage(),e);
            }

        }
    }
}
