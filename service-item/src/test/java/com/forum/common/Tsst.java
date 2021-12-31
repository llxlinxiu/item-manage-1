package com.forum.common;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author llx
 * @create 2021-12-15 10:17
 */
public class Tsst {

    @Test
    public void tes(){
        String path = "https://forum-file-1254292961.cos.ap-beijing.myqcloud.com/image/652a226e-4879-48fa-bdcf-4f459cb1abed.jpg";
        String ds = "https://forum-file-1254292961.cos.ap-beijing.myqcloud.com/";
        char[] chars = ds.toCharArray();
        System.out.println(chars.length);
        System.out.println(path.substring(58));
    }
}
