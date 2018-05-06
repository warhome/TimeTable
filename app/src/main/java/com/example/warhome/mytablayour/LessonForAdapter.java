package com.example.warhome.mytablayour;

import android.support.annotation.NonNull;

class LessonForAdapter implements Comparable<LessonForAdapter> {
    String title;
    String time;
    String teacher;
    String room;

    LessonForAdapter(String title, String time, String teacher, String room) {
        this.title = title;
        this.time = time;
        this.teacher = teacher;
        this.room = room;
    }


    @Override
    public int compareTo(@NonNull LessonForAdapter lessonForAdapter) {
        String curr = time;
        String input = lessonForAdapter.time;
        return Integer.valueOf(curr.substring(0,curr.indexOf(':'))) - Integer.valueOf(input.substring(0,input.indexOf(':')));
    }
}
