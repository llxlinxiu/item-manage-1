package com.manage.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shkstart
 * @create 2020-09-04 17:04
 */
public class RandomID {
    private static long tmpID = 0;

    private static boolean tmpIDlocked = false;

    public static String getId()
    {
        long ltime = 0;
        while (true)
        {
            if(tmpIDlocked == false)
            {
                tmpIDlocked = true;
                //当前：（年、月、日、时、分、秒、毫秒）*10000
                ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
                if(tmpID < ltime)
                {
                    tmpID = ltime;
                }
                else
                {
                    tmpID = tmpID + 1;
                    ltime = tmpID;
                }
                tmpIDlocked = false;
                return ltime+"";
            }
        }
    }

}
