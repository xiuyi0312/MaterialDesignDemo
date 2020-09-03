package com.op.materialdesigndemo.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface NewsService {
    @GET("/api/4/news/latest")
    Observable<ResponseBody> getLatestNews();
}
