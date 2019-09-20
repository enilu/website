package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.dao.NewsRepository;
import cn.enilu.website.blog.dao.NewsTagRelRepository;
import cn.enilu.website.blog.entity.Collector;
import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.DateUtil;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * MpCollectTest
 * version 2018/7/27 0027
 *
 * @author enilu
 */
public class MpCollectTest extends BaseApplicationStartTest {
    @Autowired
    private MpCollect mpCollect;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsTagRelRepository newsTagRelRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void test1(){
        Date date = DateUtil.parse("2017-12-01","yyyy-MM-dd");
        List<News> list = newsRepository.findAll();
        for(News news:list){
            if("大搜车前端".equals(news.getAuthor())){
                news.setPublishDate(date);
                news.setCreateTime(date);
                news.setModifyTime(date);
                newsRepository.save(news);
            }
        }
    }
    @Test
    public void collectAll(){
        //先运行testCollect 采集最新两个公众号
        List<Collector> collectors = collectorRepository.findAllByState(true);
        boolean collects = false;
        for(Collector collector :collectors){
            try {
                    AbstractCollect collect = (AbstractCollect) applicationContext.getBean(collector.getClassName());
                    collect.setCollect(collector);
                    logger.info("采集:{}", collector.getAuthor());
                    collect.collect();

            }catch (Exception e){
                logger.error("采集{}失败", Json.toJson(collector),e);
            }
        }
    }
    @Test
    public void saveCollect(){
        Collector collector = new Collector("TTTink-D","https://weixin.sogou.com/weixin?type=1&s_from=input&query=%E5%81%9A%E8%AE%BE%E8%AE%A1%E7%9A%84%E9%9D%A2%E6%9D%A1&ie=utf8&_sug_=n&_sug_type_=",
                "做设计的面条","mpCollect",true);
        collector.setCategory("设计");
        collectorRepository.save(collector);
    }
    @Test
    public void testCollect(){
//        做设计的面条
        //你丫才美工
        //做设计的面条
//        Collector collector = collectorRepository.findOneByAuthor("你丫才美工");
//        mpCollect.setCollect(collector);
        mpCollect.collectAll();
    }

}