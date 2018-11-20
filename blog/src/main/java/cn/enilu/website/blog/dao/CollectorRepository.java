package cn.enilu.website.blog.dao;

import cn.enilu.website.blog.entity.Collector;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CollectorRepository
 * version 2018/7/26 0026
 *
 * @author enilu
 */
@CacheConfig(cacheNames = "collectors")
@Cacheable
public interface CollectorRepository extends JpaRepository<Collector,Long> {

    List<Collector> findAllByState(boolean state);

    Collector findOneByAuthor(String author);
}
