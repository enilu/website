package cn.enilu.website.mergepdf.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created  on  2018/7/16 0016
 * LanguageInterceptor
 *
 * @author enilu
 */
@Component
public class LanguageInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LanguageInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        Locale locale= LocaleContextHolder.getLocale();
        logger.info("client language:{}",locale.getLanguage());
        request.setAttribute("lang",locale.getLanguage());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
