package cn.enilu.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages="cn.enilu.website.blog.entity")
@EnableJpaRepositories(basePackages= "cn.enilu.website.blog.dao")
public class AppConfiguration {


    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class);
    }
}
