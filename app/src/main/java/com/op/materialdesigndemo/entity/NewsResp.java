package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NewsResp implements Parcelable {
    private String date;
    private List<Story> stories;
    private List<TopStory> top_stories;

    public NewsResp() {

    }

    protected NewsResp(Parcel in) {
        date = in.readString();
        stories = in.createTypedArrayList(Story.CREATOR);
        top_stories = in.createTypedArrayList(TopStory.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeTypedList(stories);
        dest.writeTypedList(top_stories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsResp> CREATOR = new Creator<NewsResp>() {
        @Override
        public NewsResp createFromParcel(Parcel in) {
            return new NewsResp(in);
        }

        @Override
        public NewsResp[] newArray(int size) {
            return new NewsResp[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<TopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStory> top_stories) {
        this.top_stories = top_stories;
    }
}
