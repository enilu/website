package cn.enilu.website.blog.service;

import cn.enilu.website.BaseApplicationStartTest;
import cn.enilu.website.blog.dao.NewsRepository;
import cn.enilu.website.blog.entity.News;
import cn.enilu.website.utils.Md5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created  on  2018/7/18 0018
 * NewsServiceTest
 *
 * @author enilu
 */
public class NewsServiceTest extends BaseApplicationStartTest {
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsRepository newsRepository;
    @Test
    public void findPage() {
        Pageable pageable = new PageRequest(0,10);
        newsService.findPage(pageable);
    }
    @Test
    public void getCategories(){
        Map categories = newsService.getCategories();
        System.out.println(categories.size());
    }
    @Test
    public void saveTag(){
        List<News> list = newsRepository.findAll();
        for(News news:list){
            news.setUrlKey(Md5Util.getMD5String(news.getUrl()));
//            newsRepository.save(news);
        }
    }
}