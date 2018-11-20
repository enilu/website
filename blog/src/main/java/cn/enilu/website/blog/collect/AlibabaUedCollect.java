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
 * AlibabaUedCollect
 *
 * @author enilu
 */
@Service
public class AlibabaUedCollect extends AbstractCollect {

    private Logger logger = LoggerFactory.getLogger(AlibabaUedCollect.class);


    public static final String SOURCE = "alibaba_ued";
    public static final String AUTHOR = "阿里巴巴UED";
    public static final String HOME = "http://www.aliued.com";
    public static final String PAGE_URL = "http://www.aliued.com/?paged=";

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
        int totalPage = 6;
        for (int i = 1; i <= totalPage; i++) {
            logger.info("start collect page:{}", i);
            collectPage(i);
        }
    }

    private void collectPage(int page) {
        Document document = null;
        try {
            document = Jsoup.connect(PAGE_URL + page).timeout(60000).get();
        } catch (Exception e) {
            logger.error("collect page:{} error", page, e);
            return;
        }
        Elements elements = document.select(".only_two_column > ul > li > .post_border > .listbox_img >a");
        for (int i = 0; i < elements.size(); i++) {
            try {
                Element element = elements.get(i);
                String url = element.attr("href");
                collectOne(url);
            } catch (Exception e) {
                logger.error("collect error:{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 抓取一篇文章
     *
     * @param url
     */
    private void collectOne(String url) {
        String key = Md5Util.getMD5String(url);
        if (collectCache.exist(key)) {
            return;
        }
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
        } catch (Exception e) {
            logger.error("collect url:{} error", url, e);
            return;
        }

        try {
            String title = document.select(".title_top").first().text();
            String publishDateStr = document.select(".title_date").first().text();
            Date publishDate = DateUtil.parse(publishDateStr, "yyyy/MM/dd");
            String content = document.select("#J_postContent").first().text();
            String summary = content.length() > 100 ? content.substring(0, 100) : content;
            News news = new News();
            news.setAuthor(getAuthor());
            news.setSumm(summary);
            news.setTitle(title);
            news.setPv(0);
            news.setPublishDate(publishDate);
            news.setUrl(url);
            news.setCategory("设计");
            Set<String> tags = nlpService.tag(title, content);
            tags.add(news.getCategory());
            news.setTag(StringUtil.join(",", tags.toArray()));
            newsService.save(news);
        } catch (Exception e) {
            logger.error("parse url:{} error", url, e);
        }


    }
}
