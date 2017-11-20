package com.eaphone.g08android.utils;

import java.io.File;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/20 17:29
 * 修改人：Administrator
 * 修改时间：2017/11/20 17:29
 * 修改备注：
 */
public class FileUtils {
    // 判断文件夹是否存在
    public static boolean isFileExits(File file) {
        if(file.exists()){
            return true;
        } else {
            return false;
        }

    }
}


