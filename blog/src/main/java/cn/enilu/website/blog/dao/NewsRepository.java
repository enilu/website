package cn.enilu.website.blog.dao;

import cn.enilu.website.blog.entity.News;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created  on  2018/7/18 0018
 * NewsRepository
 *
 * @author enilu
 */
@CacheConfig(cacheNames = "news")
@Cacheable
public interface NewsRepository extends JpaRepository<News,String>,JpaSpecificationExecutor<News> {

    @Query(nativeQuery = true,value = "SELECT category ,count(id) FROM news group by category order by count(id) desc")
    List findAllCategories();

    List<News> findAllByAuthor(String author);

    List<News> findByPublishDateBetween(Date start, Date end);
}
