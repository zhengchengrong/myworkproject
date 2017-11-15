package com.eaphone.g08android.ui.live.livepic;

import com.eaphone.g08android.http.RxJavaRetrofitService;

/**
 * Description:
 * Author：pz
 * Date：2016/10/24:14:54
 */
public class ApiManager {
    private static ApiManager apiManager = null;
    private ApiService mApiService;

    private ApiManager() {
        mApiService = RxJavaRetrofitService.getInstance().create(ApiService.class);
    }

    /**
     * 得到apiManage实例
     *
     * @return 得到apiManage实例
     */
    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 得到ApiService实例
     *
     * @return ApiService
     */
    public ApiService getApiService() {
        return mApiService;
    }

}
