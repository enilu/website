package cn.enilu.website.blog.collect;

import cn.enilu.website.blog.cache.CollectCache;
import cn.enilu.website.blog.entity.Collector;
import cn.enilu.website.blog.service.NewsService;
import cn.enilu.website.blog.service.NlpService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on  2018/7/19 0019
 * AbstractCollect
 *
 * @author enilu
 */
public abstract  class AbstractCollect {

    @Autowired
    protected CollectCache collectCache;
    @Autowired
    protected NlpService nlpService;
    @Autowired
    protected NewsService newsService;
    protected Collector collector;
    public   void setCollect(Collector collector){
        this.collector = collector;
    }
    /**
     * 获取采集站点名称标识
     * @return
     */
    public String getSource() {
        return collector.getSource();
    }

    /**
     * 获取采集站点作者名称
     * @return
     */
    public String getAuthor() {
        return collector.getAuthor();
    }

    /**
     * 采集器是否启用
     * @return
     */
    public boolean getState() {
        return collector.getState();
    }

    /**
     * 获取采集站点主页地址
     * @return
     */
    public String getHome() {
        return collector.getHome();
    }











    /**
     * 以后定期抓取最新的文章
     */
    public  abstract void collect();

    /**
     * 初次抓取全部文章
     */
    public  abstract void collectAll();
}
