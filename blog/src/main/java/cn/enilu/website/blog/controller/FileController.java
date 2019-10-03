package cn.enilu.website.blog.controller;

import cn.enilu.website.blog.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping
public class FileController extends BaseController {
    @Autowired
    private static  final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private ConfigService configService;



    /**
     * 获取图片流
     * @param response
     * @param userName
     * @param fileName
     */
    @RequestMapping(value="/mpimg/{userName}/{fileName:.+}",method = RequestMethod.GET)
    public void getImgStream(HttpServletResponse response,
                             @PathVariable("userName")String userName,
                             @PathVariable("fileName")String fileName){

        String imgPath = configService.getImgDir()+userName+File.separator+fileName;

        FileInputStream fis = null;
        response.setContentType("image/"+imgPath.split("\\.")[1]);
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(imgPath);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            logger.error("getImgStream error",e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("close getImgStream error",e);
                }
            }
        }
    }
}
