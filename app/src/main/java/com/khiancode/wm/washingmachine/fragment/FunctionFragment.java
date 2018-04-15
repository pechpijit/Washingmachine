package com.khiancode.wm.washingmachine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;

import com.khiancode.wm.washingmachine.HomeActivity;
import com.khiancode.wm.washingmachine.StatusActivity;
import com.khiancode.wm.washingmachine.R;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FunctionFragment extends Fragment {
    NiceSpinner spinMode, spinFunction, spinTime;
    Button btnStartMachine;
    String[] value;

    public FunctionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        spinMode = view.findViewById(R.id.spin_mode);
        spinFunction = view.findViewById(R.id.spin_function);
        spinTime = view.findViewById(R.id.spin_time);
        btnStartMachine = view.findViewById(R.id.btn_start_machine);

        value = new String[3];
        List<String> mode = new LinkedList<>(Arrays.asList("Automatically wash", "Manually set"));
        List<String> function = new LinkedList<>(Arrays.asList("Colours", "Mixed", "Cotton", "Wool", "Sportswear", "Shirts", "Easy-Care"));
        List<String> time = new LinkedList<>(Arrays.asList("Time 15", "Time 30", "Time 45", "Time 60", "Time 75", "Time 90"));

        List<String> auto = new LinkedList<>(Arrays.asList("Automatically"));

        spinMode.attachDataSource(mode);
        spinFunction.attachDataSource(auto);
        spinTime.attachDataSource(auto);

        spinFunction.setEnabled(false);
        spinTime.setEnabled(false);

        spinnerListener(auto, mode,function, time);

        value[0] = "auto";
        value[1] = "Automatic";
        value[2] = "Automatic";

        btnStartMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).intentStart(value);
            }
        });

        return view;
    }

    private void spinnerListener(final List<String> auto, final List<String> mode, final List<String> function, final List<String> time) {
        spinMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinFunction.setEnabled(false);
                    spinTime.setEnabled(false);
                    spinFunction.attachDataSource(auto);
                    spinTime.attachDataSource(auto);
                    value[0] = "Automatically wash";
                    value[1] = "Automatic";
                    value[2] = "Automatic";
                } else {
                    spinFunction.setEnabled(true);
                    spinTime.setEnabled(true);
                    spinFunction.attachDataSource(function);
                    spinTime.attachDataSource(time);
                    value[0] = mode.get(position);
                    value[1] = function.get(0);
                    value[2] = time.get(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinFunction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value[1] = function.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value[2] = time.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
