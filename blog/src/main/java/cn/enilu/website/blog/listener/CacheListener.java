package cn.enilu.website.blog.listener;

import cn.enilu.website.blog.cache.CollectCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created  on  2018/7/19 0019
 * CacheListener
 *
 * @author enilu
 */
//@Component
public class CacheListener implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(CacheListener.class);
    @Autowired
    private CollectCache collectCache;
    @Override
    public void run(String... strings) throws Exception {
        logger.info("...................cache listner..................");
        collectCache.cache();
    }
}
