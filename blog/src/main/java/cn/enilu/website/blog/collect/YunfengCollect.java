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
import java.util.Locale;
import java.util.Set;

/**
 * Created  on  2018/7/24 0024
 * YunfengCollect
 *
 * @author enilu
 */
@Service
public class YunfengCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(YunfengCollect.class);

    public static final String SOURCE = "yunfeng";
    public static final String AUTHOR = "云峰的BLOG";
    public static final String HOME = "https://blog.codingnow.com";
    public static final String PAGE_URL = HOME;

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
        getPage(null, HOME);
    }

    @Override
    public void collectAll() {
        Document document = null;
        try {
            document = Jsoup.connect(HOME).timeout(60000).get();
        } catch (Exception e) {
            logger.info("collect all error", e);
        }
        Elements elements = document.select(".module-list-item > a");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String a = element.toString();
            if (a.contains("title")) {
                String type = element.text();
                String url = element.attr("href");
                logger.info(type);
                getPage(type, url);
            }
        }

    }

    private void getPage(String type, String pageUrl) {
        Document document = null;
        try {
            document = Jsoup.connect(pageUrl).timeout(60000).get();
        } catch (Exception e) {
            logger.error("collect page error", e);
        }

        Elements elements = document.select(".entry");
        for (int i = 0; i < elements.size(); i++) {
            try {
                Element element = elements.get(i);
                Element dateEl = element.previousElementSibling().previousElementSibling();
                String publishDateStr = dateEl.text();
                String title = element.select(".entry-header").first().text();
                String url = element.select(".entry-content > .entry-body  > .entry-footer > .permalink").first().attr("href");
                String content = getContent(url);
                String summary = element.select(".entry-content > .entry-body > p").first().text();
                if (StringUtil.isEmpty(content)) {
                    content = summary;
                }
                logger.info(title + publishDateStr);
                String key = Md5Util.getMD5String(url);

                if (collectCache.exist(key)) {
                    continue;
                }
                Date publishDateEn = DateUtil.parse(publishDateStr, "MMM d, yyyy", Locale.ENGLISH);
                Date publishDateCn = DateUtil.parse(DateUtil.formatDate(publishDateEn, "yyyy-MM-dd"), "yyyy-MM-dd");
                News news = new News();
                news.setAuthor(getAuthor());
                news.setSumm(summary);
                news.setTitle(title);
                news.setPv(0);
                news.setPublishDate(publishDateCn);
                news.setUrl(url);
                if (StringUtil.equals("读书", type)
                        || StringUtil.equals("杂记", type)
                        || StringUtil.equals("Google", type)
                        || StringUtil.equals("简悦", type)
                        || StringUtil.equals("攀岩", type)
                        || StringUtil.equals("我爱折腾", type)) {
                    news.setCategory(nlpService.category(title, content));
                } else {
                    news.setCategory("后端");
                }
                Set<String> tags = nlpService.tag(title, content);
                news.setTag(StringUtil.join(",", tags.toArray()));
                logger.info(news.getTag());
                news.setUrlKey(key);
                newsService.save(news);


            } catch (Exception e) {
                logger.error("parse error", e);
            }
        }

    }

    private String getContent(String url) {
        try {
            return Jsoup.connect(url).timeout(60000).get().text();
        } catch (Exception e) {
            return null;
        }
    }
}
