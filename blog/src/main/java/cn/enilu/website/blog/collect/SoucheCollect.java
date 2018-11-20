package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
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
 * SoucheCollect
 *
 * @author enilu
 */
@Service
public class SoucheCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(SoucheCollect.class);
    public static final String SOURCE = "souche";
    public static final String AUTHOR = "大搜车前端";
    public static final String HOME = "http://f2e.souche.com";
    public static final String PAGE_URL = "http://f2e.souche.com/blog/page/";


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
        collectPage(1);

    }

    @Override
    public void collectAll() {
        for (int i = 1; i <= 4; i++) {
            logger.info("start collect page:{}", i);
            collectPage(i);
        }
    }

    private void collectPage(int page) {
        try {
            Document document = Jsoup.connect(PAGE_URL + page).timeout(60000).get();
            Elements elements = document.select("article");
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                Element hrefElement = element.select(".post-title > a").first();
                String title = hrefElement.text();
                String url = hrefElement.attr("href");
                String summary = element.select(".post-excerpt >p").first().text();
                String key = Md5Util.getMD5String(url);
                if (collectCache.exist(key)) {
                    continue;
                }


                News news = new News();
                news.setAuthor(getAuthor());
                news.setUrlKey(key);
                if (summary.endsWith("»")) {
                    summary = summary.substring(0, summary.length() - 1);
                }
                news.setSumm(summary);
                news.setTitle(title);
                news.setPv(0);
                news.setPublishDate(new Date());

                news.setUrl(HOME + url);
                news.setCategory("前端");
                Set<String> tags = nlpService.tag(title, summary);
                tags.add(news.getCategory());
                news.setTag(StringUtil.join(",", tags.toArray()));
                newsService.save(news);


            }
        } catch (Exception e) {
            logger.error("collect error:{}", e.getMessage(), e);
        }
    }
}
