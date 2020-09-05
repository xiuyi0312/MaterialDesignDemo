package com.op.materialdesigndemo.http;

import android.annotation.SuppressLint;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static volatile RetrofitManager instance;
    private Retrofit retrofit;
    private NewsService newsService;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private RetrofitManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newsService = retrofit.create(NewsService.class);
    }

    public Observable<ResponseBody> getNewsDetail(int id) {
        return newsService.getNewsDetail(id);
    }

    public Observable<ResponseBody> post(String url, Map<String, String> map) {
        return newsService.post(url, map);
    }

    public Observable<ResponseBody> postBody(String url, Object body) {
        return newsService.postBody(url, body);
    }

    public Observable<ResponseBody> postBody(String url, RequestBody body) {
        return newsService.postBody(url, body);
    }

    public Observable<ResponseBody> postJson(String url, RequestBody jsonBody) {
        return newsService.postJson(url, jsonBody);
    }

    public Observable<ResponseBody> get(String url, Map<String, String> map) {
        return newsService.get(url, map);
    }

    /**
     * used for result in the form of Class, i.e. {...}
     * @param url
     * @param map
     * @param cls
     * @param callback
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void get(String url, Map<String, String> map, final Class<T> cls,
                        final HttpCallback<T> callback) {
        newsService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        if (callback != null) {
                            T t = new Gson().fromJson(responseBody.string(), cls);
                            callback.onSuccess(t);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callback != null) {
                            callback.onError(new ApiException(throwable.getMessage(), 401));
                        }
                    }
                });
    }

    /**
     * used for result in the form of List<SomeClass>, i.e. [...]
     * @param url
     * @param map
     * @param type
     * @param callback
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void get(String url, Map<String, String> map, final Type type,
                        final HttpCallback<List<T>> callback) {
        newsService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        if (callback != null) {
                            //responseBody.string();
                            String string = "[{\"image_hue\":\"0xb3987d\",\"title\":\"为什么人们通常只吃大型鲨鱼的鳍，而不吃其他部分？\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727536\",\"hint\":\"五莲花开 · 4 分钟阅读\",\"ga_prefix\":\"090407\",\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-dd0c91e759e6ff58dd24613946ea8531.jpg?source=8673f162\"],\"type\":0,\"id\":9727536},{\"image_hue\":\"0x709ab3\",\"title\":\"为什么很多人都误以为「鲨鱼肉没人吃」？\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727544\",\"hint\":\"一个男人在流浪 · 8 分钟阅读\",\"ga_prefix\":\"090407\",\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-52c21b2210981cd0f6f9ad682d6544e8.jpg?source=8673f162\"],\"type\":0,\"id\":9727544},{\"image_hue\":\"0x363f4d\",\"title\":\"人为什么会晕针？\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727560\",\"hint\":\"韩白鲤 · 1 分钟阅读\",\"ga_prefix\":\"090407\",\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-49ad52162878049a7e849270c965b264.jpg?source=8673f162\"],\"type\":0,\"id\":9727560},{\"image_hue\":\"0x202946\",\"title\":\"坐飞机去另一个城市，应该提前化妆还是在飞机上化？\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727545\",\"hint\":\"胖博士 · 3 分钟阅读\",\"ga_prefix\":\"090407\",\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-ea7f0381fe0d349841f94123680a05b3.jpg?source=8673f162\"],\"type\":0,\"id\":9727545},{\"image_hue\":\"0x666e91\",\"title\":\"有哪些超级英雄类的作品，敌我双方同根同源？\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727553\",\"hint\":\"疯癫的A兵者 · 1 分钟阅读\",\"ga_prefix\":\"090407\",\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-72242c6ef1fe7ec08aae7dd05ff5f7f2.jpg?source=8673f162\"],\"type\":0,\"id\":9727553},{\"image_hue\":\"0x7a5656\",\"title\":\"瞎扯 · 如何正确地吐槽\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9727532\",\"hint\":\"VOL.2478\",\"ga_prefix\":\"090406\",\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-528b9f744b248ebaabd6940b8926f0e0.jpg?source=8673f162\"],\"type\":0,\"id\":9727532}]";
                            List<T> t = new Gson().fromJson(string, type);
                            callback.onSuccess(t);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callback != null) {
                            callback.onError(new ApiException(throwable.getMessage(), 401));
                        }
                    }
                });
    }

    public Observable<ResponseBody> downloadFile(String fileUrl) {
        return newsService.downloadFile(fileUrl);
    }
}
