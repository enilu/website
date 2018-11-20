package cn.enilu.website.mergepdf.controller;


import cn.enilu.website.bean.Rets;
import cn.enilu.website.mergepdf.service.PdfService;
import cn.enilu.website.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created  on  2018/7/16 0016
 * PdfSplitController
 *
 * @author enilu
 */
@Controller
@RequestMapping("/pdfsplit")
public class PdfSplitController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${website.upload.dir}")
    private String uploadDir;

    @Autowired
    private PdfService pdfService;

    @RequestMapping("/index.html")
    public String student(){
        return "pdf_split";
    }

    /**
     * 上传pdf文件(上传到项目的webapp/static/img)
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> json = new HashMap<String, Object>(10);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = null;
        Map map = multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            Object obj = i.next();
            multipartFile = (MultipartFile) map.get(obj);

        }

        String fileName = "split"+UUID.randomUUID().toString() + ".pdf";
        logger.info("upload split file:{}", fileName);
        try {
            multipartFile.transferTo(new File(uploadDir + fileName));
        } catch (Exception e) {
           logger.error("upload file error:{}",e.getMessage(),e);
        }
        json.put("fileName", fileName);
        json.put("originFileName", multipartFile.getOriginalFilename());
        json.put("size", multipartFile.getSize());
        json.put("status", true);
        return json;
    }

    @RequestMapping(value = "/split", method = RequestMethod.GET)
    public String split(@RequestParam("uuid") String uuid,
                        @RequestParam("splitPage") String splitPage, Model model) {

        final String fileName = Md5Util.getMD5String(uuid);
        logger.info("split file:{}", fileName);
        getSession().setAttribute("splitFile", fileName);
        final Map<Integer, String> map = new HashMap<Integer, String>(10);


        final String filePath = uploadDir + uuid;
        if(!splitPage.startsWith("1,")){
            splitPage = "1,"+splitPage;
        }
        String[] splitPages = splitPage.split(",");

        List<String> files =  pdfService.splitFile( filePath,splitPages);


        model.addAttribute("files", files);
        return "pdf_split_download";
    }

    @RequestMapping(value = "/getSplitInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getSplitInfo(@RequestParam("fileName") String fileName, Model model) {
        logger.info("get split Info:{}", fileName);
        File file = new File(uploadDir + fileName + ".pdf");
        if (!file.exists()) {
            return Rets.failure("文件分割中");
        }
        getSession().removeAttribute("splitFile");
        return Rets.success("文件分割完成");
    }
}
