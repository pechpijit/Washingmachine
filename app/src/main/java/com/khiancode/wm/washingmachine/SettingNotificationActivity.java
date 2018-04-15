package com.khiancode.wm.washingmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.khiancode.wm.washingmachine.adapter.AdapterSettingMachine;
import com.khiancode.wm.washingmachine.adapter.AdapterSettingNotification;

import java.util.ArrayList;

public class SettingNotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterSettingNotification adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setLayoutManager( new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        ArrayList<String> posts = new ArrayList<>();
        posts.add("Machine Alert");
        posts.add("Machine Start");
        posts.add("Machine Finish");

        adapter = new AdapterSettingNotification(this, posts);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AdapterSettingNotification.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

}
