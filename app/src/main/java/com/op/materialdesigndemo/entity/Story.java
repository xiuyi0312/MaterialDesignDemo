package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.op.materialdesigndemo.db.TypeConvertersForDb;

import java.util.List;

@Entity
public class Story implements Parcelable {
    @PrimaryKey
    private int id;
    @Ignore
    private String image_hue;
    private String title;
    private String hint;
    @Ignore
    private String ga_prefix;
    @TypeConverters(TypeConvertersForDb.class)
    private List<String> images;
    private int type;
    private long publishTime;

    public Story() {

    }


    protected Story(Parcel in) {
        id = in.readInt();
        image_hue = in.readString();
        title = in.readString();
        hint = in.readString();
        ga_prefix = in.readString();
        images = in.createStringArrayList();
        type = in.readInt();
        publishTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image_hue);
        dest.writeString(title);
        dest.writeString(hint);
        dest.writeString(ga_prefix);
        dest.writeStringList(images);
        dest.writeInt(type);
        dest.writeLong(publishTime);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
