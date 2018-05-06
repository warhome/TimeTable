package com.example.warhome.mytablayour;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Lesson implements Comparable<Lesson> {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("day_week")
    @Expose
    private String dayWeek;
    @SerializedName("course")
    @Expose
    private Integer course;
    @SerializedName("group")
    @Expose
    private Integer group;
    @SerializedName("numerator")
    @Expose
    private String numerator;

    Lesson(Integer id, String title, String time, String teacher, String room, String dayWeek, Integer course, Integer group, String numerator) {
        this.id = id;
        this.title =title;
        this.time = time;
        this.teacher = teacher;
        this.room = room;
        this.dayWeek = dayWeek;
        this.course = course;
        this.group = group;
        this.numerator = numerator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getNumerator() {
        return numerator;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    @Override
    public int compareTo(@NonNull Lesson lesson) {

        String curr = this.getTime();
        String input = lesson.getTime();
        return Integer.valueOf(curr.substring(0,curr.indexOf(':'))) - Integer.valueOf(input.substring(0,input.indexOf(':')));
    }
}