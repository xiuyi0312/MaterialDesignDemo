package com.op.materialdesigndemo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.op.materialdesigndemo.entity.Story;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("select * from story where publishTime >= :startTime and publishTime <= :endTime ")
    List<Story> getStoryDuring(long startTime, long endTime);
    @Query("select count(*) from story")
    int getStoryCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Story... stories);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Story> stories);

    @Update
    void update(Story... stories);

    @Delete
    void delete(Story... stories);

}
