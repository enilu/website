package cn.enilu.website.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created  on  2018/7/23 0023
 * NewsTagRel
 *
 * @author enilu
 */
@Data
@Entity
@Table(name="news_tag_rel")
public class NewsTagRel extends BaseEntity {

    private Long idTag;
    private String idNews;
}
