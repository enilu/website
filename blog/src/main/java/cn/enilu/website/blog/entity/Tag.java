package cn.enilu.website.blog.entity;

import cn.enilu.website.bean.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created  on  2018/7/23 0023
 * Tag
 *
 * @author enilu
 */
@Data
@Entity
@Table(name="tag")
public class Tag extends BaseEntity {
    private String tag;
    private Integer count;

}
