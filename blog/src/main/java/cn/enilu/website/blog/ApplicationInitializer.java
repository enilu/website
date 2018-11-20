package cn.enilu.website.blog;

import cn.enilu.website.blog.collect.AbstractCollect;
import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.entity.Collector;
import cn.enilu.website.utils.StringUtil;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ApplicationInit
 * version 2018/7/26 0026
 *
 * @author enilu
 */
@Component
public class ApplicationInitializer implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void run(String... args) throws Exception {
        initCollector();
    }
    void initCollector(){
        logger.info("init Collector...");
        List<Collector> list = collectorRepository.findAll();
        if(!list.isEmpty()){
            return ;
        }
       Map<String,AbstractCollect> collectMap = applicationContext.getBeansOfType(AbstractCollect.class);
        for(Map.Entry<String,AbstractCollect> entry: collectMap.entrySet()){
            AbstractCollect collect = entry.getValue();
            Collector collector = new Collector(collect.getSource(),collect.getHome(),collect.getAuthor(),
                    StringUtil.captureName(collect.getClass().getSimpleName()),collect.getState());
            logger.info(Json.toJson(collector));
            collectorRepository.save(collector);
        }

    }
}
