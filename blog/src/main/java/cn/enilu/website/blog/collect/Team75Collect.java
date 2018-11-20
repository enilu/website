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

import java.util.Set;

/**
 * Created  on  2018/7/22 0022
 * Team75Collect
 *
 * @author enilu
 */
@Service
public class Team75Collect extends AbstractCollect {

    private Logger logger = LoggerFactory.getLogger(Team75Collect.class);
    public static final String SOURCE = "75team";
    public static final String AUTHOR = "奇舞团博客";
    public static final String HOME = "https://75team.com";
    public static final String PAGE_URL = "https://75team.com/archives";

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
        } catch (Exception e) {
            logger.error("collect page :{} error", PAGE_URL, e);
        }
        Elements elements = document.select(".entry-content > ul >li");
        for (int i = 0; i < elements.size(); i++) {
            Element hrefEl = elements.get(i).select("a").first();
            String publishDateStr = elements.get(i).select(".date").first().text();
            String url = HOME + hrefEl.attr("href");

            String title = hrefEl.text();
            try {
                String key = Md5Util.getMD5String(url);
                if (collectCache.exist(key)) {
                    continue;
                }
                News news = new News();
                news.setAuthor(getAuthor());
                news.setPublishDate(DateUtil.parse(publishDateStr, "yyyy-MM-dd"));
                String content = getContent(url);
                String summary = content.length() > 100 ? content.substring(0, 100) : content;
                news.setSumm(summary);
                news.setTitle(title);
                news.setPv(0);
                news.setUrl(url);
                news.setCategory("前端");
                Set<String> tags = nlpService.tag(title, content);
                tags.add(news.getCategory());
                news.setTag(StringUtil.join(",", tags.toArray()));
                newsService.save(news);


            } catch (Exception e) {
                logger.error("collect url error:{} ", url, e);
            }
        }
    }

    private String getContent(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
            String content = document.select(".entry-content").first().text();
            return content;
        } catch (Exception e) {
            logger.error("collect url:{} error", url, e);
        }
        return null;
    }
}
