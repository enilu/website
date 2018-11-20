package cn.enilu.website.blog.controller;

import cn.enilu.website.blog.entity.Tag;
import cn.enilu.website.blog.service.NewsService;
import cn.enilu.website.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created  on  2018/7/23 0023
 * TagController
 *
 * @author enilu
 */
@Controller
@RequestMapping("/tag")
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;
    @Autowired
    private NewsService newsService;
    @RequestMapping
    public String index(Model model){
        List<Tag> list = tagService.findAll();
        model.addAttribute("list",list);
        model.addAttribute("categories",newsService.getCategories());
        return "tag";
    }
}
