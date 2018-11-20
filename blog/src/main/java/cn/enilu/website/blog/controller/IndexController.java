package cn.enilu.website.blog.controller;

import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.dao.NewsRepository;
import cn.enilu.website.blog.entity.Collector;
import cn.enilu.website.blog.entity.News;
import cn.enilu.website.blog.service.NewsService;
import cn.enilu.website.blog.service.TagService;
import cn.enilu.website.utils.DateUtil;
import cn.enilu.website.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created  on  2018/7/18 0018
 * IndexController
 *
 * @author enilu
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private TagService tagService;
    public static  final int PAGE_SIZE=20;
    @RequestMapping("/")
    public String toIndex(Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "publishDate");
        Pageable pageable = new PageRequest(0,PAGE_SIZE,sort);

        Page<News> page = newsService.findPage(pageable);
        model.addAttribute("list",page.getContent());
        model.addAttribute("currentCategory","all");
        model.addAttribute("pageNum",1);
        model.addAttribute("title","首页");
        return "index";
    }
    @RequestMapping("/author/{author}")
    public String toIndex(@PathVariable("author") String author, Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "publishDate");
        Pageable pageable = new PageRequest(0,PAGE_SIZE,sort);
        Map map = new HashMap<>(10);
        map.put("author",author);

        Page<News> page = newsService.findPage(null,map,pageable);
        model.addAttribute("list",page.getContent());
        Collector collector = collectorRepository.findOneByAuthor(page.getContent().get(0).getAuthor());
        model.addAttribute("currentCategory","all");
        model.addAttribute("pageNum",1);
        model.addAttribute("author",author);
        if(StringUtil.equals("mpCollect",collector.getClassName())){
            model.addAttribute("collector",collector);
        }
        model.addAttribute("title",collector.getAuthor()+"的文章列表");
        return "index";
    }

    @RequestMapping("/category/{category}")
    public String toCategory(@PathVariable("category") String category, Model model) {

        Sort sort = new Sort(Sort.Direction.DESC, "publishDate");
        Pageable pageable = new PageRequest(0,PAGE_SIZE,sort);
        Page<News> page = newsService.findPage(category,null,pageable);
        model.addAttribute("list",page.getContent());
        model.addAttribute("currentCategory",category);
        model.addAttribute("pageNum",1);
        model.addAttribute("title",category+"类文章");
        return "index";
    }

    @RequestMapping(value = "/category/{category}/{pageNum}",method = RequestMethod.POST)
    @ResponseBody
    public Object page(@PathVariable("category") String category,
                       @PathVariable("pageNum") Integer pageNum ,
                       @RequestParam(value = "author",required = false) String author ) {

        Sort sort = new Sort(Sort.Direction.DESC, "publishDate");
        Pageable pageable = new PageRequest(pageNum,PAGE_SIZE,sort);
         Map map = new HashMap<>(10);
         map.put("author",author);
        if(StringUtil.equals("all",category)){
            return  newsService.findPage(null,map,pageable);
        }else {
            return newsService.findPage(category,map, pageable);
        }
    }
    @RequestMapping(value ="/article/{id}",method = RequestMethod.GET)
    public String article(@PathVariable("id")String id,Model model)throws Exception{
        try {
            News article = newsRepository.findOne(id);
            Collector collector = collectorRepository.findOneByAuthor(article.getAuthor());
            model.addAttribute("article", article);
            model.addAttribute("author", collector);
            return "article";
        }catch (Exception e){
            return "404";
        }
    }

    /**
     * 一周好文
     * @param date
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="/weekly/{date}",method = RequestMethod.GET)
    public String weekly(@PathVariable("date")String date,Model model)throws Exception{
        try {
            Date end = null;
            if("latest".equalsIgnoreCase(date)){
                date = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
                end = new Date();
            }else{
                end = DateUtil.parse(date+" 23:59:59","yyyy-MM-dd hh:mm:ss");
            }

            Date start = DateUtil.getDateBefore(end,5);
            List<News> articles = newsRepository.findByPublishDateBetween(start,end);
            Map<String,Collector> authorMap = new HashMap<String,Collector>();
            for(News article:articles){
                String author = article.getAuthor();
                if(authorMap.get(author)==null){
                    Collector collector = collectorRepository.findOneByAuthor(author);
                    authorMap.put(author,collector);
                }
                article.setCollector(authorMap.get(author));
            }

            model.addAttribute("articles", articles);
            model.addAttribute("date",date);
            return "weekly";
        }catch (Exception e){
            return "404";
        }
    }
//    @RequestMapping("/error")
//    public String handleError(){
//            return "404";
//    }
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
}
