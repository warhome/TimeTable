package com.example.warhome.mytablayour;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        TextView room;
        TextView teacher;

        PersonViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lesson);
            time = itemView.findViewById(R.id.time);
            room = itemView.findViewById(R.id.room);
            teacher = itemView.findViewById(R.id.teacher);
        }
    }

    private List<Lesson> lessonList;
    RVAdapter(List<Lesson> lessonList){
        this.lessonList = lessonList;
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
    @Override
    public RVAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVAdapter.PersonViewHolder personViewHolder, int i) {
        personViewHolder.title.setText(lessonList.get(i).title);
        personViewHolder.time.setText(lessonList.get(i).time);
        personViewHolder.room.setText(lessonList.get(i).room);
        personViewHolder.teacher.setText(lessonList.get(i).teacher);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}