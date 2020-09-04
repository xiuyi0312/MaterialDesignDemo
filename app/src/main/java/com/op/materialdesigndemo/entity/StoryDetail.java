package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StoryDetail implements Parcelable {
    private String body;
    private String image_source;
    private String title;
    private String url;
    private String image;
    private String share_url;
    private List<String> images;
    private List<String> js;
    private int type;
    private int id;
    private List<String> css;

    public StoryDetail() {

    }

    protected StoryDetail(Parcel in) {
        body = in.readString();
        image_source = in.readString();
        title = in.readString();
        url = in.readString();
        image = in.readString();
        share_url = in.readString();
        images = in.createStringArrayList();
        js = in.createStringArrayList();
        type = in.readInt();
        id = in.readInt();
        css = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(image_source);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(image);
        dest.writeString(share_url);
        dest.writeStringList(images);
        dest.writeStringList(js);
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeStringList(css);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoryDetail> CREATOR = new Creator<StoryDetail>() {
        @Override
        public StoryDetail createFromParcel(Parcel in) {
            return new StoryDetail(in);
        }

        @Override
        public StoryDetail[] newArray(int size) {
            return new StoryDetail[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
