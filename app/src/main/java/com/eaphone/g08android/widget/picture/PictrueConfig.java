package com.eaphone.g08android.widget.picture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.alley.van.VanGogh;
import com.alley.van.helper.VanCropType;
import com.alley.van.helper.VanMediaFilter;
import com.alley.van.helper.VanMediaType;
import com.hpw.mvpframe.utils.ErrorInfoUtils;
import com.kevin.crop.UCrop;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.tencent.open.utils.Global.getPackageName;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/26 17:51
 * 修改人：Administrator
 * 修改时间：2017/8/26 17:51
 * 修改备注：
 */
public class PictrueConfig {
    public static final int REQUEST_CODE_CHOOSE = 23;
    public static final int REQUEST_CODE_CAMERA = 32;

    /**
     * 拍照获取
     * @param activity
     */
    public static void fromPhoto(Activity activity) {
        VanGogh.from(activity)
                .choose(VanMediaType.ofAll())//拍照时，无效
                .cameraVisible(true, getPackageName())//拍照时，第一个参数无效
                .withResultSize(1024, 1024)
                .cropEnable(true, VanCropType.CROP_TYPE_RECTANGLE)//第一个参数为FALSE时，第二个参数无效
//                        .theme(R.style.VanTheme_ActivityAnimation)
                .thumbnailScale(0.85f)
//                        .toast(new VanToast())
                .imageLoader(new GlideImageLoader())
                .forCamera(REQUEST_CODE_CAMERA);
    }

    /**
     * 从相册中选择
     * @param activity
     * @param flag   true为单选，false为多选
     */
    public static void formTakePhoto(Activity activity,boolean flag){
        VanGogh.from(activity)
                .choose(VanMediaType.ofAll())
                .countable(true)//若开启裁剪，则无效
                .maxCount(9)
                .rowCount(3)
                .cameraVisible(true, getPackageName())//设置在第一个参数为FALSE时，第二个参数无效
                .withResultSize(1024, 1024)
                .cropEnable(flag, VanCropType.CROP_TYPE_RECTANGLE)//第一个参数为TRUE时，则可选中数量被设为1，此时maxSelectable(9)无效；第一个参数为FALSE时，第二个参数无效
//                        .theme(R.style.VanTheme_Dracula)
                .addFilter(new GifSizeFilter(320, 320, 5 * VanMediaFilter.K * VanMediaFilter.K))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                        .toast(new VanToast())
                .imageLoader(new GlideImageLoader())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    public static Bitmap handleCropResult(Intent result, Context context) {
        final Uri resultUri = UCrop.getOutput(result);

        String filePath = resultUri.getEncodedPath();
        String imagePath = Uri.decode(filePath);

        if (resultUri == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), resultUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bitmap;
    }
    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    public static String handleCropError(Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e("PictrueConfig", "handleCropError: "+ cropError);
            return ErrorInfoUtils.parseHttpErrorInfo(cropError);
        } else {
            return "无法裁剪图片";
        }
    }
}
