package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    public static final String HOME = "https://isux.tencent.com/articles/";
    public static final String PAGE_URL = "https://isux.tencent.com/list";

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

        for (int i = 0; i < TencentIsuxConstant.data.length; i++) {
            String data = TencentIsuxConstant.data[i];
            getPage(data);
        }
    }

    private void getPage(String data) {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject json = jsonArray.getJSONObject(i);
                String url = HOME + json.getString("permalink") + ".html";
                String key = Md5Util.getMD5String(url);
                if (collectCache.exist(key)) {
                    continue;
                }
                String title = json.getString("title");

                String summary = json.getString("abstract");
                // DateUtil.parse(publishDateStr, "MMM d, yyyy", Locale.ENGLISH);
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
//                    newsService.save(news);


            } catch (Exception e) {
                logger.error("解析异常", e);
            }

        }
    }
}
