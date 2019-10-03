package cn.enilu.website.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created  on  2018/7/16 0016
 * WebsiteBootApplication
 *
 * @author enilu
 */

@Configuration
@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "cn.enilu.website.blog")
@EntityScan(basePackages="cn.enilu.website.blog.entity")
@EnableJpaRepositories(basePackages= "cn.enilu.website.blog.dao")
@ServletComponentScan
@EnableScheduling
@EnableCaching
public class BlogApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(BlogApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("application in http://localhost:8081 when in dev profile");
    }
}
