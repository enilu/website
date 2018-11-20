package cn.enilu.website.blog.entity;

import cn.enilu.website.bean.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Collector
 * version 2018/7/26 0026
 *
 * @author enilu
 */
@Table(name = "collector")
@Entity
@Data
public class Collector extends BaseEntity {
    private String source;
    private String home;
    private String author;
    private String className;
    private Boolean state;
    private String category;

    public Collector(String source, String home,  String author, String className, Boolean state) {
        this.source = source;
        this.home = home;
        this.author = author;
        this.className = className;
        this.state = state;
    }

    public Collector() {
    }
}
