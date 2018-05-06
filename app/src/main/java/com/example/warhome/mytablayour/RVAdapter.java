package com.example.warhome.mytablayour;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LessonViewHolder>{

    static class LessonViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        TextView room;
        TextView teacher;

        LessonViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lesson);
            time = itemView.findViewById(R.id.time);
            room = itemView.findViewById(R.id.room);
            teacher = itemView.findViewById(R.id.teacher);
        }
    }

    private List<LessonForAdapter> lessonForAdapterList;
    RVAdapter(List<LessonForAdapter> lessonForAdapterList){
        this.lessonForAdapterList = lessonForAdapterList;
    }

    @Override
    public int getItemCount() {
        return lessonForAdapterList.size();
    }
    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout, viewGroup, false);
        return new LessonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder lessonViewHolder, int i) {
        lessonViewHolder.title.setText(lessonForAdapterList.get(i).title);
        lessonViewHolder.time.setText(lessonForAdapterList.get(i).time);
        lessonViewHolder.room.setText(lessonForAdapterList.get(i).room);
        lessonViewHolder.teacher.setText(lessonForAdapterList.get(i).teacher);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}