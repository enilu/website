package cn.enilu.website.blog.dao;

import cn.enilu.website.blog.entity.NewsTagRel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created  on  2018/7/23 0023
 * NewsTagRelRepository
 *
 * @author enilu
 */
public interface NewsTagRelRepository extends JpaRepository<NewsTagRel,Long> {
}
