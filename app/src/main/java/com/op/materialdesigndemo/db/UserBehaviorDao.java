package com.op.materialdesigndemo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.op.materialdesigndemo.entity.UserBehavior;

import java.util.List;

@Dao
public interface UserBehaviorDao {
    @Query("select * from user_behavior where updateTime >= :startTime and updateTime <= :endTime ")
    List<UserBehavior> getBehaviorsDuring(long startTime, long endTime);
    @Query("select count(*) from user_behavior")
    int getBehaviorCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserBehavior... behaviors);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserBehavior... behaviors);

    @Delete
    void delete(UserBehavior... behaviors);

}
