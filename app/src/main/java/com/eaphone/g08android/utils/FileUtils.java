package com.eaphone.g08android.utils;

import android.os.Environment;

import com.eaphone.g08android.G08Application;
import com.hpw.mvpframe.utils.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

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
    public static boolean exitSDCard(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            return true;
        }
        return false;
    }
    // 文件下载
    // path 保存路径
    public static void fileDown(String url,String path){
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ToastUtils.showToast(G08Application.getInstances(),"下载成功");
                        String  path = task.getPath(); // 得到下载路径
                        String  url = task.getUrl();
                    }
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ToastUtils.showToast(G08Application.getInstances(),"下载失败");
                    }
                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}


