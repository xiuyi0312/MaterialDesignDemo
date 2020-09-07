package com.op.materialdesigndemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

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

    public UserBehavior() {

    }

    protected UserBehavior(Parcel in) {
        storyId = in.readInt();
        opType = in.readInt();
        updateTime = in.readLong();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(storyId);
        dest.writeInt(opType);
        dest.writeLong(updateTime);
    }
}