package cn.enilu.website.blog.interceptor;

import cn.enilu.website.blog.controller.BaseController;
import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.service.NewsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * WebInterceptor
 * version 2018/7/26 0026
 *
 * @author enilu
 */
@Aspect
@Component
public class WebInterceptor extends BaseController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CollectorRepository collectorRepository;

    @Pointcut("execution(* cn.enilu.website.blog.controller.*.*(..))")
    public void cutService() {
    }
    @Around("cutService()")
    public Object prepareData(ProceedingJoinPoint point)throws Throwable{
        HttpServletRequest request = super.getHttpServletRequest();
        request.setAttribute("categories",newsService.getCategories());
        request.setAttribute("authors",collectorRepository.findAllByState(true));
        return point.proceed();
    }

}
