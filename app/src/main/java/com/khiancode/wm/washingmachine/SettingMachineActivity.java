package com.khiancode.wm.washingmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.khiancode.wm.washingmachine.adapter.AdapterSettingLanguage;
import com.khiancode.wm.washingmachine.adapter.AdapterSettingMachine;
import com.khiancode.wm.washingmachine.helper.PrefUtils;

import java.util.ArrayList;

public class SettingMachineActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterSettingMachine adapter;

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
        posts.add("Silent");
        posts.add("Low");
        posts.add("Medium");
        posts.add("High");

        adapter = new AdapterSettingMachine(this, posts);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AdapterSettingMachine.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PrefUtils prefUtils = new PrefUtils(getApplicationContext());
                prefUtils.setMachine(position);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
