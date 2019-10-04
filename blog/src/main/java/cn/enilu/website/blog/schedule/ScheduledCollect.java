package cn.enilu.website.blog.schedule;

import cn.enilu.website.blog.collect.AbstractCollect;
import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.entity.Collector;
import cn.enilu.website.utils.DateUtil;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ScheduledCollect
 * version 2018/7/26 0026
 *
 * @author enilu
 */
@Component
public class ScheduledCollect {
    private Logger logger = LoggerFactory.getLogger(ScheduledCollect.class);
    @Autowired
    CollectorRepository collectorRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Scheduled(cron="0 48 9 * * ?")
    public void collect() {
        logger.info("现在时间：" + DateUtil.format(new Date()));
        List<Collector> collectors = collectorRepository.findAllByState(true);
        for(Collector collector :collectors){
            try {
                AbstractCollect collect = (AbstractCollect) applicationContext.getBean(collector.getClassName());
                if(!collect.isValid()){
                    continue;
                }
                collect.setCollect(collector);
                logger.info("采集:{}开始", collector.getAuthor());
                collect.collect();
                logger.info("采集:{}结束", collector.getAuthor());
            }catch (Exception e){
                logger.error("采集{}失败", Json.toJson(collector),e);
            }
        }
    }
}
