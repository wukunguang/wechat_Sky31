package util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by wukunguang on 15-9-4.
 */
public class FileUtil {

    public static String multipartFile2f(MultipartFile file ,String realpath){

        //文件处理方法，该方法主要用于上传文件的URL和路径之间的转化。

        String url = "/resource/images/statics/";

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String path = url+System.currentTimeMillis()+type;


        File file1 =new File(realpath+"/"+System.currentTimeMillis()+type);

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), file1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }
}
