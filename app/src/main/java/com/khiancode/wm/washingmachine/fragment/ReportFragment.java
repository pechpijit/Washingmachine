package com.khiancode.wm.washingmachine.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.khiancode.wm.washingmachine.HomeActivity;
import com.khiancode.wm.washingmachine.R;
import com.khiancode.wm.washingmachine.helper.BaseActivity;

public class ReportFragment extends Fragment {

    final private static String TAG = "ReportFragment";
    EditText inputTopic, inputDetail;
    Button btnSendReport;

    public ReportFragment() {
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
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        inputTopic = view.findViewById(R.id.input_topic);
        inputDetail = view.findViewById(R.id.input_detail);
        btnSendReport = view.findViewById(R.id.btn_send_report);

        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSubmit();
            }
        });

        return view;
    }

    private void dialogSubmit() {
        Log.d(TAG, "onClickSendReport");
        btnSendReport.setEnabled(false);

        if (!validate()) {
            btnSendReport.setEnabled(true);
            return;
        }

        new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog)
                .setTitle("Send report")
                .setMessage("Confirm send report")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendReport();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnSendReport.setEnabled(true);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void sendReport() {
        ((HomeActivity)getActivity()).showProgressDialog(BaseActivity.VERIFY);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSendSuccess();

                        ((HomeActivity)getActivity()).hideProgressDialog();
                    }
                }, 3000);
    }

    private void onSendSuccess() {
        inputTopic.setText("");
        inputDetail.setText("");

        new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog)
                .setTitle("Send report statud")
                .setMessage(" send report : SUCCESS")
                .setPositiveButton("OK",null)
                .setCancelable(false)
                .show();
    }

    public boolean validate() {
        Log.d(TAG, "validate");
        boolean valid = true;

        String topic = inputTopic.getText().toString().trim();
        String detail = inputDetail.getText().toString().trim();

        if (topic.isEmpty()) {
            inputTopic.setError("Please enter your machine id.");
            valid = false;
        } else {
            inputTopic.setError(null);
        }

        if (detail.isEmpty()) {
            inputDetail.setError("Please enter your machine id.");
            valid = false;
        } else {
            inputDetail.setError(null);
        }

        Log.d(TAG, "validate:"+valid);
        return valid;
    }
}
