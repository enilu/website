package cn.enilu.website.blog.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ：enilu
 * @date ：Created in 2019/9/20 14:34
 */
@Service
@Data
public class ConfigService {
    @Value("${app.img.dir}")
    private String imgDir;

}
