package com.example.warhome.mytablayour;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LessonDao {

    @Query("SELECT * FROM lesson")
    List<Lesson> getAll();


    @Query("SELECT * FROM lesson WHERE id = :id")
    Lesson getById(long id);

    @Insert
    void insert(Lesson lesson);

    @Update
    void update(Lesson lesson);

    @Delete
    void delete(Lesson lesson);
}
