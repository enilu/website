package cn.enilu.website.mergepdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created  on  2018/7/16 0016
 * WebsiteBootApplication
 *
 * @author enilu
 */
@SpringBootApplication
@Controller
@EnableCaching
public class WebsiteBootApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(WebsiteBootApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(WebsiteBootApplication.class, args);
    }

    @RequestMapping("/")
    public String toIndex(Model model) {
        return "index";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("application in http://localhost:8081 when in dev profile");
    }
}
