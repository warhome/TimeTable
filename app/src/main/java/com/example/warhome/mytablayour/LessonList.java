package com.example.warhome.mytablayour;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonList {

    @SerializedName("lessons")
    @Expose
    private List<Lesson> lessons = null;

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}