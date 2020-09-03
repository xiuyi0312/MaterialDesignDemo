package com.op.materialdesigndemo.vm;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.op.materialdesigndemo.entity.NewsResp;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.http.RetrofitManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class NewsViewModel extends ViewModel {
    public final MutableLiveData<List<Story>> newsMutableLiveData = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getLatestNews() {
        RetrofitManager.getInstance().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String string = responseBody.string();
                        NewsResp newsResp = new Gson().fromJson(string, NewsResp.class);
                        newsMutableLiveData.postValue(newsResp.getStories());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("News", "=" + throwable.getLocalizedMessage());
                    }
                });
    }
}
