package cn.enilu.website.mergepdf.service;


import cn.enilu.website.utils.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created  on  2018/7/12 0012
 * PdfService
 *
 * @author enilu
 */
@Service
public class PdfService {
    private Logger logger = LoggerFactory.getLogger(PdfService.class);
    public static void main(String[] args) {
        Map<Integer,String> files = new HashMap(10);
        files.put(0,"e:\\1.pdf");
        files.put(1, "e:\\2.pdf");
        files.put(2, "e:\\3.pdf" );
        String savepath = "e:\\temp.pdf";

//        mergePdfFiles(files, savepath);
//        new PdfService().splitFile("/Users/maggie/test.pdf",new String[]{1,3,5,7});
    } /*
     * * 合並pdf文件 * * @param files 要合並文件數組(絕對路徑如{ "e:\\1.pdf", "e:\\2.pdf" ,
     * "e:\\3.pdf"}) * @param newfile
     * 合並後新產生的文件絕對路徑如e:\\temp.pdf,請自己刪除用過後不再用的文件請 * @return boolean
     * 產生成功返回true, 否則返回false
     */

    public  boolean mergePdfFiles(Map<Integer,String> files, String newfile) {
        logger.info("merge to :{}",newfile);
        boolean retValue = false;
        Document document = null;
        try {
            document = new Document(new PdfReader(files.get(0)).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            for (int i = 0; i < files.size(); i++) {
                PdfReader reader = new PdfReader(files.get(i));
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            logger.info("close merge document:{}",newfile);
            document.close();
        }
        return retValue;
    }

    /**
     * 分割pdf文件
     * @param pdfFile
     * @param splitPage
     * @return
     */
    public List<String> splitFile(String pdfFile,String[] splitPage ){
        logger.info("split file:{}",pdfFile);
        List<String> files = new ArrayList<>(10);
        for(int i=0;i<splitPage.length;i++){
            if(StringUtil.isEmpty(splitPage[i])){
                continue;
            }
            int start = Integer.valueOf(splitPage[i]);

            int end = 0;
            if(i!=splitPage.length-1) {
                end = Integer.valueOf(splitPage[i + 1]) - 1;
            }
           String fileName =  splitFile(pdfFile,start,end);
            files.add(fileName);
        }
        return files;

    }

    /**
     * 分割pdf文件
     * @param pdfFile
     * @param from
     * @param end
     * @return
     */
    public String splitFile(String pdfFile,Integer from,Integer end){

        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            if(end==0){
                end = n;
            }
            List<String> savepaths = new ArrayList<String>();
            int a = pdfFile.lastIndexOf(".pdf");
            String staticpath = pdfFile.substring(0, a);
            String savepath = staticpath+ "_from_"+from+"_to_"+end+"_.pdf";
            savepaths.add(savepath);
            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));
            document.open();
            for(int j=from; j<=end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();
            return new File(savepath).getName();
        } catch (IOException e) {
            logger.error("split pdf file error:{}",e.getMessage(),e);
            return null;
        } catch(DocumentException e) {
            logger.error("split pdf file error:{}",e.getMessage(),e);
            return null;
        }
    }
}
