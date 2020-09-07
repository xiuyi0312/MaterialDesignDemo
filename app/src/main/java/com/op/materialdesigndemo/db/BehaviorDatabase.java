package com.op.materialdesigndemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.UserBehavior;

@Database(entities = {Story.class, UserBehavior.class}, version = 1, exportSchema = false)
public abstract class BehaviorDatabase extends RoomDatabase {

    public static BehaviorDatabase create(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                BehaviorDatabase.class,
                "BEHAVIOR_DB.db").build();
    }

    public abstract UserBehaviorDao getUserBehaviorDao();

    public abstract StoryDao getStoryDao();
}
