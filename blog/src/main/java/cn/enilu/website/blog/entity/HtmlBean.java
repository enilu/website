package cn.enilu.website.blog.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created  on  2018/7/19 0019
 * HtmlBean
 *
 * @author enilu
 */
@Data
public class HtmlBean {
    private String url;
    private String title;
    private String content;
    private List<String> tag;
    private String category;
    private String summary;
    private Date publishDate;
}
