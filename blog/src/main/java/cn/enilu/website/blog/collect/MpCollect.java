package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.ImgDownload;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * MpCollect
 * version 2018/7/27 0027
 *
 * @author enilu
 */
@Service
public class MpCollect extends AbstractCollect {
    private Logger logger = LoggerFactory.getLogger(MpCollect.class);
    public static  final String WX_URL = "https://mp.weixin.qq.com";
    @Override
    public String getSource() {
        return collector.getSource();
    }

    @Override
    public String getAuthor() {
        return collector.getAuthor();
    }

    @Override
    public boolean getState() {
        return collector.getState();
    }

    @Override
    public String getHome() {
        return collector.getHome();
    }

    @Override
    public void collect() {
        this.collectAll();
    }

    @Override
    public void collectAll() {
        Document document = null;
        try {
            String url = getRealHome(getHome());
            document = Jsoup.connect(url).timeout(60000).get();
        }catch (Exception e){
            logger.error("parse home error",e);
            return ;
        }
        Element scriptEl = document.getElementsByTag("script").get(8);

        String[] arr = scriptEl.html().split("\\n");
        String msglist = arr[6].split("var msgList =")[1].trim();
        msglist = msglist.substring(0,msglist.length()-1);
        JSONObject jsonObject = new JSONObject(msglist);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject mainJson = json.getJSONObject("app_msg_ext_info");
            JSONObject commenJson = json.getJSONObject("comm_msg_info");
            String url = WX_URL + StringEscapeUtils.unescapeHtml4(mainJson.getString("content_url"));
            String summary = mainJson.getString("digest");
            String title = mainJson.getString("title");
            Date publishDate = new Date(Long.valueOf(commenJson.getLong("datetime")+"000"));
            Element  contentEl = getContent(url);
            String contentTxt = contentEl.text();

            String key = Md5Util.getMD5String(title);
            if (collectCache.exist(key)) {
                continue;
            }
            logger.warn("【没有采集】{}",title);
            String contentHtml = getImg(contentEl);
            News news = new News();
            news.setAuthor(getAuthor());

            if(StringUtil.isEmpty(summary)){
                news.setSumm(contentTxt.length()>200?contentTxt.substring(0,200):contentTxt);
            }else {
                if(summary.length()<100){
                    news.setSumm(contentTxt.length()>200?contentTxt.substring(0,200):contentTxt);
                }else {
                    news.setSumm(summary);
                }
            }
            news.setTitle(title);
            news.setPv(0);
            news.setPublishDate(publishDate);
            news.setUrl(url);
            news.setContent(contentHtml);
            news.setUrlKey(key);
            if(StringUtil.isEmpty(collector.getCategory())) {
                news.setCategory(nlpService.category(title, StringUtil.isNotEmpty(contentTxt) ? contentTxt : summary));
            }else{
                news.setCategory(collector.getCategory());
            }
            Set<String> tags = nlpService.tag(title, StringUtil.isNotEmpty(contentTxt)?contentTxt:summary);
            tags.add(news.getCategory());
            news.setTag(StringUtil.join(",", tags.toArray()));
            newsService.save(news);

        }

    }
    private Element getContent(String url){
        try {
            Document document = Jsoup.connect(url).timeout(60000).get();
            Element contentEl =  document.select("#js_content").get(0);
            contentEl.getElementsByTag("iframe").remove();
            return contentEl;
        }catch (Exception e){
            logger.error("get content error",e);
            return null;
        }
    }
    private String IMG_DIR = "/data/website/mpimg/";
    private String getImg(Element element){
        String html = element.html();
        Elements elements = element.getElementsByTag("img");
        for(int i=0;i<elements.size();i++){
            String src = elements.get(i).attr("data-src");
            String imgName = Md5Util.getMD5String(src)+".jpeg";
            String file = IMG_DIR+getSource()+"/"+imgName;
            ImgDownload.downloadImg(file,src);
            html = html.replace(src,"/mpimg/"+getSource()+"/"+imgName);
        }
        html = html.replaceAll("data-src","src");
        return html;
    }

    private String getRealHome(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(60000).get();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("parse home error",e);
            return null;
        }
        return document.select("a[uigs=\"account_name_0\"]").get(0).attr("href");

    }
}
