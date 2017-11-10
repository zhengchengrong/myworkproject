package com.hpw.mvpframe.utils;

import com.hpw.mvpframe.base.CoreBaseView;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/13 18:31
 * 修改人：Administrator
 * 修改时间：2017/9/13 18:31
 * 修改备注：
 */
public class ExceptionUtils implements Action1<Throwable> {

    private CoreBaseView view ;

    public ExceptionUtils(CoreBaseView coreBaseView){
        this.view = coreBaseView;
    }

    @Override
    public void call(Throwable throwable) {

        //在此处理错误相关
        view.showError(ErrorInfoUtils.parseHttpErrorInfo(throwable));
    }
}
