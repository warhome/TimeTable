package com.example.warhome.mytablayour;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends android.support.v4.app.Fragment {

    private RVAdapter adapter;
    private int identical;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_layout, container, false);
        RecyclerView recyclerView = mView.findViewById(R.id.rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        Bundle bundle = getArguments();
        if(bundle != null) {
            identical = bundle.getInt("key");
        }

        switch (identical) {
            case 0:
                adapter = new RVAdapter(MainActivity.mondayList);
                break;
            case 1:
                adapter = new RVAdapter(MainActivity.tuesdayList);
                break;
            case 2:
                adapter = new RVAdapter(MainActivity.wednesdayList);
                break;
            case 3:
                adapter = new RVAdapter(MainActivity.thursdayList);
                break;
            case 4:
                adapter = new RVAdapter(MainActivity.fridayList);
                break;
            case 5:
                adapter = new RVAdapter(MainActivity.saturdayList);
                break;
            default:
        }
        recyclerView.setAdapter(adapter);
        return mView;
    }

}
