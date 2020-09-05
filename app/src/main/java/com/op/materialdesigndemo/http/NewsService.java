package com.op.materialdesigndemo.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * how to provide uniform http interface
 */
public interface NewsService {

    @GET("/api/4/news/{id}")
    Observable<ResponseBody> getNewsDetail(@Path("id") int id);


    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @POST
        // used in customed request body
    Observable<ResponseBody> postBody(@Url String url, @Body Object body);

    @POST
        // used in customed request body
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);

    @POST()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
        // the jsonBody is from as below
        // RequestBody jsonBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);

    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map);

    // delete

    // put

    // multipart

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
