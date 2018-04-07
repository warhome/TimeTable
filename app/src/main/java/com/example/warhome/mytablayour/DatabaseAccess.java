package com.example.warhome.mytablayour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /*--------------Add lesson in database------------------*/
    public void insertLesson(Allessons allessons) {
        ContentValues values = new ContentValues();
        values.put("id", allessons.getId());
        values.put("title", allessons.getTitle());
        values.put("time", allessons.getTime());
        values.put("teacher", allessons.getTeacher());
        values.put("day_week", allessons.getDay_week());
        values.put("room", allessons.getRoom());
        values.put("course", allessons.getCourse());
        values.put("group", allessons.getGroup());
        values.put("isEnum", allessons.getIsEnum());
        database.insert("LessonsDb", null, values);
    }

    /*--------------Update lesson from database---------------*/
    public void updateLesson(Allessons oldAllessons, Allessons newAllessons) {
        ContentValues values = new ContentValues();
        values.put("id", newAllessons.getId());
        values.put("title", newAllessons.getTitle());
        values.put("time", newAllessons.getTime());
        values.put("teacher", newAllessons.getTeacher());
        values.put("day_week", newAllessons.getDay_week());
        values.put("room", newAllessons.getRoom());
        values.put("course", newAllessons.getCourse());
        values.put("group", newAllessons.getGroup());
        values.put("isEnum", newAllessons.getIsEnum());
        database.update("LessonsDb", values, "id = ?", new String[]{String.valueOf(oldAllessons.getId())});
    }

    /*--------------Delete current lesson from database-------*/
    public void deleteLesson(Allessons allessons) {
        database.delete("LessonsDb", "id = ?", new String[]{String.valueOf(allessons.getId())});
        database.close();
    }

    public List<Allessons> getLessons() {
        List<Allessons> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM LessonsDb", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Allessons(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}