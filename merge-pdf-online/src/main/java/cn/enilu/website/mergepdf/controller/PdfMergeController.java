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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created  on  2018/7/16 0016
 * PdfMergeController
 *
 * @author enilu
 */
@Controller
@RequestMapping("/")
public class PdfMergeController extends BaseController
{
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${website.upload.dir}")
    private String uploadDir;

    @Autowired
    private PdfService pdfService;


    @RequestMapping("/pdfmerge.html")
    public String student(){
        return "index";
    }

    /**
     * 上传pdf文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> json = new HashMap<String, Object>(20);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = null;
        Map map = multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            Object obj = i.next();
            multipartFile = (MultipartFile) map.get(obj);

        }

        String fileName = UUID.randomUUID().toString() + ".pdf";
        logger.info("upload file:{}", fileName);
        try {
            multipartFile.transferTo(new File(uploadDir + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("fileName", fileName);
        json.put("originFileName", multipartFile.getOriginalFilename());
        json.put("size", multipartFile.getSize());
        json.put("status", true);
        return json;
    }

    @RequestMapping(value = "/merge", method = RequestMethod.GET)
    public String merge(@RequestParam("param") String param, Model model) {

        final String fileName = "merge"+Md5Util.getMD5String(param);
        logger.info("merge file:{}", fileName);
        getSession().setAttribute("mergeFile", fileName);
        final Map<Integer, String> map = new HashMap<Integer, String>(20);

        String[] files = param.split("enilu");
        for (String file : files) {
            if (file.contains(",")) {
                String[] arr = file.split(",");
                map.put(Integer.valueOf(arr[0]) - 1, uploadDir + arr[1]);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                pdfService.mergePdfFiles(map, uploadDir + fileName + ".pdf");
            }
        }).start();


        model.addAttribute("fileName", fileName);
        model.addAttribute("fileCount",files.length);
        return "download";
    }

    @RequestMapping(value = "/getMergeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getMergeInfo(@RequestParam("fileName") String fileName, Model model) {
        logger.info("get file:{}", fileName);
        File file = new File(uploadDir + fileName + ".pdf");
        if (!file.exists()) {
            return Rets.failure("文件合并中");
        }
        getSession().removeAttribute("mergeFile");
        return Rets.success("文件合并完成");
    }
}
