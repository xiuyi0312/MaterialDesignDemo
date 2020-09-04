package com.op.materialdesigndemo.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsService {
    @GET("/api/4/news/latest")
    Observable<ResponseBody> getLatestNews();

    @GET("/api/4/news/{id}")
    Observable<ResponseBody> getNewsDetail(@Path("id") int id);
}
