package com.op.materialdesigndemo.vm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.db.BehaviorDatabaseHelper;
import com.op.materialdesigndemo.entity.NewsResp;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.StoryDetail;
import com.op.materialdesigndemo.entity.UserBehavior;
import com.op.materialdesigndemo.http.ApiException;
import com.op.materialdesigndemo.http.HttpCallback;
import com.op.materialdesigndemo.http.RetrofitManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class NewsViewModel extends AndroidViewModel {
    public final MutableLiveData<List<Story>> newsMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<StoryDetail> storyDetailMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<Bitmap> bitmapMutableLiveData = new MutableLiveData<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyDDmm");

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void getLatestNews() {
        RetrofitManager.getInstance().get(
                "api/4/news/latest", new HashMap<String, String>(),//参数部分
                NewsResp.class, new HttpCallback<NewsResp>() {// 响应部分
                    @Override
                    public void onSuccess(NewsResp result) {
                        List<Story> stories = result.getStories();
                        long publishTime = -1L;
                        try {
                            publishTime = simpleDateFormat.parse(result.getDate()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (Story story : stories) {
                            story.setPublishTime(publishTime);
                        }
                        BehaviorDatabaseHelper.getInstance(getApplication()).insertOrReplaceStory(stories);
                        newsMutableLiveData.postValue(result.getStories());
                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });

//        RetrofitManager.getInstance().get(
//                "api/4/news/latest", new HashMap<String, String>(),//参数部分
//                new TypeToken<List<Story>>() {}.getType()
//                , new HttpCallback<List<Story>>() {// 响应部分
//                    @Override
//                    public void onSuccess(List<Story> result) {
//                        newsMutableLiveData.postValue(result);
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//
//                    }
//                });

    }

    @SuppressLint("CheckResult")
    public void getNewsDetail(int id) {
        RetrofitManager.getInstance().getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String string = responseBody.string();
                        StoryDetail storyDetail = new Gson().fromJson(string, StoryDetail.class);
                        storyDetailMutableLiveData.postValue(storyDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("News", "=" + throwable.getLocalizedMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getAvatar(String imageUrl) {
        RetrofitManager.getInstance().get(imageUrl, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        bitmapMutableLiveData.postValue(BitmapFactory.decodeStream(responseBody.byteStream()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void insertUserBehavior(int opType, int storyId) {
        UserBehavior behavior = new UserBehavior();
        behavior.setOpType(opType);
        behavior.setStoryId(storyId);
        behavior.setUpdateTime(System.currentTimeMillis());
        BehaviorDatabaseHelper.getInstance(getApplication()).insertOrReplaceBehavior(behavior);
    }

    public void updateUserBehaviorWithComment(int opType, int storyId, String comment) {
        UserBehavior behavior = new UserBehavior();
        behavior.setOpType(opType);
        behavior.setStoryId(storyId);
        behavior.setUpdateTime(System.currentTimeMillis());
        behavior.setComment(comment);
        BehaviorDatabaseHelper.getInstance(getApplication()).insertOrReplaceBehavior(behavior);
    }

    public String getStatistics() {
        return String.format(getApplication().getString(R.string.statistics),
                BehaviorDatabaseHelper.getInstance(getApplication()).getStoryCount(),
                BehaviorDatabaseHelper.getInstance(getApplication()).getUserBehaviorCount());
    }
}
