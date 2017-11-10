package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.mvp.contracts.PassportContracts;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/14 12:28
 * 修改人：Administrator
 * 修改时间：2017/8/14 12:28
 * 修改备注：
 */
public class StartImagePresenter extends PassportContracts.StartImagesPresenter {
    @Override
    public void onStart() {
        getImages();
    }

    @Override
    public void getImages() {
        mView.getImages(new int[]{/*R.mipmap.bg_login, R.mipmap.bg_splash,R.mipmap.bg_login, R.mipmap.bg_splash*/});
    }
}
