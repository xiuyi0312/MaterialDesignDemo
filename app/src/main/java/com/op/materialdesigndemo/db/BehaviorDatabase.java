package com.op.materialdesigndemo.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.UserBehavior;

@Database(entities = {Story.class, UserBehavior.class}, version = 2, exportSchema = false)
public abstract class BehaviorDatabase extends RoomDatabase {

    public static BehaviorDatabase create(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                BehaviorDatabase.class,
                "BEHAVIOR_DB.db")
                .addMigrations(MIGRATION_1_2).build();
    }

    public abstract UserBehaviorDao getUserBehaviorDao();

    public abstract StoryDao getStoryDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table user_behavior add column comment text");
        }
    };
}
