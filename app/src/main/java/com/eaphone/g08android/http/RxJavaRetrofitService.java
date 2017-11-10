package com.eaphone.g08android.http;

import com.eaphone.g08android.http.APIUrl.IApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/7/11 10:14
 * 修改人：Administrator
 * 修改时间：2017/7/11 10:14
 * 修改备注：
 */
public class RxJavaRetrofitService {
    static final long CACHE_STATE_SEC = 60 * 60 * 24;//设置缓存时长
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STATE_SEC;

    private static Retrofit mRetrofit;
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 20;

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final TokenInterceptor tokenInterceptor = new TokenInterceptor();

    private RxJavaRetrofitService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(tokenInterceptor)
                .addNetworkInterceptor(mCacheControlInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .baseUrl(IApi.MAIN_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RxJavaRetrofitService INSTANCE = new RxJavaRetrofitService();
    }

    public static RxJavaRetrofitService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 拦截器  设置appid的
     */
    private static Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (request.header("X-AppId") == null) {
                Request newRequest = request.newBuilder()
                        .header("X-AppId", "58057eabb67aec5bc82bc160")
                        .build();
                return chain.proceed(newRequest);
            }
            RequestBody body = request.body();
            Buffer buffer = new Buffer();
            if (body != null) {
                body.writeTo(buffer);
            }
            Response response = chain.proceed(request);
            return response;
        }
    };

    private static Interceptor mCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Response response = chain.proceed(chain.request());


            return response.newBuilder()
                    .header("Cache-Control", "max-age=60")
                    .removeHeader("Pragma").build();
        }
    };

}
