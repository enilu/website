package cn.enilu.website.mergepdf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created  on 2018/2/2 0002.
 *
 * @author zt
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping("/about.html")
    public String about(Model model){

        return "upload";
    }

    @RequestMapping("/student.html")
    public String student(){
        return "student";
    }



}
