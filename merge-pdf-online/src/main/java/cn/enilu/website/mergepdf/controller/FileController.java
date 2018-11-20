package cn.enilu.website.mergepdf.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created  on  2018/7/10 0010
 * UploadController
 *
 * @author enilu
 */
@Controller
@RequestMapping("/")
public class FileController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${website.upload.dir}")
    private String uploadDir;



    @RequestMapping(value = "/download")

    public void download(@RequestParam("fileName") String uuid,
                         @RequestParam("downloadName") String downloadName,
                         HttpServletResponse resp) {
        try {
            String filePath = uploadDir + uuid;
            if(!uuid.endsWith(".pdf")){
                filePath += ".pdf";
            }

            downloadFile(downloadName, filePath);
        } catch (Exception e) {
            logger.error("download file error:{}", e.getMessage(), e);
        }
    }
}