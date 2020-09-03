package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TopStory implements Parcelable {
    private String image_hue;
    private String title;
    private String hint;
    private String ga_prefix;
    private String image;
    private int type;
    private int id;

    public TopStory() {

    }
    protected TopStory(Parcel in) {
        image_hue = in.readString();
        title = in.readString();
        hint = in.readString();
        ga_prefix = in.readString();
        image = in.readString();
        type = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_hue);
        dest.writeString(title);
        dest.writeString(hint);
        dest.writeString(ga_prefix);
        dest.writeString(image);
        dest.writeInt(type);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopStory> CREATOR = new Creator<TopStory>() {
        @Override
        public TopStory createFromParcel(Parcel in) {
            return new TopStory(in);
        }

        @Override
        public TopStory[] newArray(int size) {
            return new TopStory[size];
        }
    };

    public String getImage_hue() {
        return image_hue;
    }

    public void setImage_hue(String image_hue) {
        this.image_hue = image_hue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
