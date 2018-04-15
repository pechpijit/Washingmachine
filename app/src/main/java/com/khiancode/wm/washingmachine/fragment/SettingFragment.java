package com.khiancode.wm.washingmachine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.khiancode.wm.washingmachine.R;
import com.khiancode.wm.washingmachine.SettingLanguageActivity;
import com.khiancode.wm.washingmachine.SettingMachineActivity;
import com.khiancode.wm.washingmachine.SettingNotificationActivity;
import com.khiancode.wm.washingmachine.adapter.AdapterSetting;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSetting adapter;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return AnimationUtils.loadAnimation(getActivity(),
                enter ? android.R.anim.fade_in : android.R.anim.fade_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        recyclerView = view.findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.setHasFixedSize(true);

        ArrayList<String> posts = new ArrayList<>();
        posts.add("Notification");
        posts.add("Language");
        posts.add("Machine Volume");

        adapter = new AdapterSetting(getActivity(), posts);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AdapterSetting.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    startActivity(new Intent(getActivity(), SettingNotificationActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(getActivity(), SettingLanguageActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(getActivity(), SettingMachineActivity.class));
                }
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        return view;
    }
}
