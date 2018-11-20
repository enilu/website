package cn.enilu.website.blog.cache;

import cn.enilu.website.blog.dao.NewsRepository;
import cn.enilu.website.blog.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created  on  2018/7/19 0019
 * CollectCache
 *
 * @author enilu
 */
@Service
public class CollectCache {
    @Autowired
    private NewsRepository newsRepository;
    public static TimeCacheMap<String, String> cache = new TimeCacheMap<String, String>(3600, 2);


    public boolean exist(String key){
        if(cache.size()==0){
            cache();
        }
        return cache.get(key)!=null;
    }

    public void saveKey(String key){
        cache.put(key,key);
    }
    public void cache() {
        List<News> list = newsRepository.findAll();
        if (list != null && !list.isEmpty()) {
            for (News news:list) {
                 String key =news.getUrlKey();
                 cache.put(key,news.getUrlKey());
            }
        }
    }
}
