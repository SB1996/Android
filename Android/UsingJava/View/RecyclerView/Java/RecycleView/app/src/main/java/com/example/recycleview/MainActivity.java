package com.example.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = (RecyclerView) findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        String[] data = new String[100];// = {"Content 00", "Content 01", "Content 02", "Content 03", "Content 04", "Content 05", "Content 06", "Content 07", "Content 08", "Content 09"};
        for(int i =0; i < 100; ++i){
            data[i] = "Content 0"+i;
        }
        recycleView.setAdapter(new RecycleAdapter(data));
    }
}
