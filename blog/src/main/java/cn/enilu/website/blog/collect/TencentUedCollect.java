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
 * Created  on  2018/7/21 0021
 * TencentUedCollect
 *
 * @author enilu
 */
@Service
public class TencentUedCollect extends AbstractCollect {

    private Logger logger = LoggerFactory.getLogger(TencentUedCollect.class);


    public static final String SOURCE = "tencent_ued";
    public static final String AUTHOR = "腾讯UED";
    public static final String HOME = "http://ued.qq.com";
    public static final String PAGE_URL = "http://ued.qq.com/page/";
    public static final String PAGE_EXAMPLE_URL = "http://ued.qq.com/category/example/page/";

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
        int totalPage = 4;
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
        Elements elements = document.select(".conList > .text");
        for (int i = 0; i < elements.size(); i++) {
            try {
                Element element = elements.get(i);
                Element hrefElement = element.select(".hd > h2 > a").first();
                Element publishDateElement = element.select(".hd > .f12 > .time").first();
                String publishDateStr = publishDateElement.text();
                Date publishDate = DateUtil.parse(publishDateStr, "yyyy.MM.dd");
                String title = hrefElement.text();
                String url = hrefElement.attr("href");
                String summary = element.select(".bd >p").first().text();
                String key = Md5Util.getMD5String(url);
                if (collectCache.exist(key)) {
                    continue;
                }
                News news = new News();
                news.setAuthor(getAuthor());
                news.setSumm(summary);
                news.setTitle(title);
                news.setPv(0);

                news.setPublishDate(publishDate);
                news.setUrl(url);
                news.setCategory("设计");
                Set<String> tags = nlpService.tag(title, summary);
                tags.add(news.getCategory());
                news.setTag(StringUtil.join(",", tags.toArray()));
                newsService.save(news);
            } catch (Exception e) {
                logger.error("collect error:{}", e.getMessage(), e);
            }
        }

    }

    /**
     * 获取文章详情
     *
     * @param url
     * @return
     */
    private String getContent(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();

            String ret = document.select(".bd").first().text();
            return ret;
        } catch (Exception e) {
            logger.error("collect article:{} error", url, e);
            return "";
        }
    }
}
