package com.manage.common.util.xiuTool;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileTool {

    /**
     * 把接受的multipartFile转换为本地临时File对象
     * @param file 接受的multipartFile文件对象
     * @return 本地临时File对象
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file){

        // 创建临时文件夹路径
        String path1 = "/data/learn/";
        // 创建临时文件夹对象
        File file1 = new File(path1);
        if(!file1.exists()){
            file1.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split(".");

        // 创建临时文件路径
        String path2 = path1 + originalFilename;
        // 创建临时文件对象
        File file2 = new File(path2);
        try {
            if(!file2.exists()){
                file2.createNewFile();
            }

            //把文件放到临时文件里面
            if (file.equals("") || file.getSize() <= 0) {
                throw new Exception();
            } else {
                FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file2;
    }

    /**
     * 根据传入临时文件对象，删除该文件
     * @param file 本地临时文件对象
     * @return 执行结果
     */
    public static boolean deleteLocalFile(File file) {
        boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
