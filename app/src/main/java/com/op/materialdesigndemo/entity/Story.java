package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Story implements Parcelable {
    private String image_hue;
    private String title;
    private String hint;
    private String ga_prefix;
    private List<String> images;
    private int type;
    private int id;

    public Story() {

    }

    protected Story(Parcel in) {
        image_hue = in.readString();
        title = in.readString();
        hint = in.readString();
        ga_prefix = in.readString();
        images = in.createStringArrayList();
        type = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_hue);
        dest.writeString(title);
        dest.writeString(hint);
        dest.writeString(ga_prefix);
        dest.writeStringList(images);
        dest.writeInt(type);
        dest.writeInt(id);
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
