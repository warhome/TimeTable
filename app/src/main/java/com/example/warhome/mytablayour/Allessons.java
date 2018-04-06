package com.example.warhome.mytablayour;

public class Allessons {
    private int id;
    private String title;
    private String time;
    private String teacher;
    private String day_week;
    private String room;
    private String course;
    private String group;
    private String isEnum; // is 0 if it enum/denum lesson, 1 if enum only and 2 if denum only

    Allessons(int id, String title, String time, String teacher, String day_week, String room, String course, String group, String isEnum) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.teacher = teacher;
        this.day_week = day_week;
        this.room = room;
        this.course = course;
        this.group = group;
        this.isEnum = isEnum;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getDay_week() {
        return day_week;
    }

    public String getRoom() {
        return room;
    }

    public String getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    public String getIsEnum() {
        return isEnum;
    }
}
