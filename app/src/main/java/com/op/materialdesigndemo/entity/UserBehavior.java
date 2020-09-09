package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * record the user behavior for certain story, which can be shown and analyzed later
 */
@Entity(tableName = "user_behavior")
public class UserBehavior implements Parcelable {
    public static final int OP_CLICK = 0x0001;
    public static final int OP_READ = 0x0010;
    public static final int OP_COMMENT = 0x0100;
    public static final int OP_SHARE = 0x1000;

    @PrimaryKey
    @ForeignKey(entity = Story.class, parentColumns = {"id"}, childColumns = {"storyId"},
            onDelete = ForeignKey.CASCADE)
    private int storyId;
    private int opType;// click read comment share
    private long updateTime;// when user operate the last time
    private String comment;

    public UserBehavior() {

    }

    protected UserBehavior(Parcel in) {
        storyId = in.readInt();
        opType = in.readInt();
        updateTime = in.readLong();
        comment = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(storyId);
        dest.writeInt(opType);
        dest.writeLong(updateTime);
        dest.writeString(comment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBehavior> CREATOR = new Creator<UserBehavior>() {
        @Override
        public UserBehavior createFromParcel(Parcel in) {
            return new UserBehavior(in);
        }

        @Override
        public UserBehavior[] newArray(int size) {
            return new UserBehavior[size];
        }
    };

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean hasRead() {
        return (opType & OP_READ) > 0;
    }

    public boolean hasComment() {
        return (opType & OP_COMMENT) > 0 && !TextUtils.isEmpty(comment);
    }

    public boolean hasClicked() {
        return (opType & OP_CLICK) > 0;
    }
}