package cn.enilu.website.blog.dao;

import cn.enilu.website.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created  on  2018/7/23 0023
 * TagRepository
 *
 * @author enilu
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    List<Tag> findAllByOrderByTagAsc();

    /**
     * 查询热门标签
     * @return
     */
    @Query(nativeQuery = true,value = "select  id_tag from news_tag_rel group by  id_tag  having  count(id)>50 order by count(id) desc")
    List findHotTag();

    List<Tag> findAllByIdIn(List idList);
}
