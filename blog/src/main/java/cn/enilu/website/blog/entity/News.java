package cn.enilu.website.blog.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on  2018/7/18 0018
 * News
 *
 * @author enilu
 */
@Data
@Entity
@Table(name = "news",
        indexes = {
                @Index(name="idx_category", columnList = "category")
        }
)
public class News     {
    @Id
    private String id;

    @CreationTimestamp
    @Column(name = "create_time",columnDefinition="DATETIME COMMENT '创建时间/注册时间'")
    private Date createTime;

    @Column(name = "create_by",columnDefinition="bigint COMMENT '创建人'")
    private Long createBy;

    @UpdateTimestamp
    @Column(name = "modify_time",columnDefinition="DATETIME COMMENT '最后更新时间'")
    private Date modifyTime;

    @Column(name = "modify_by",columnDefinition="bigint COMMENT '最后更新人'")
    private Long modifyBy;
    private String title;
    private String url;
    private String author;
    private String category;
    private String tag;
    private Integer pv;
    private String summ;
    private Date publishDate;
    private String urlKey;
    private String content;
    @Transient
    private Collector collector;
    public News(){}
    public News(String title,String summ){
        this.title = title;
        this.summ = summ;
    }



}
