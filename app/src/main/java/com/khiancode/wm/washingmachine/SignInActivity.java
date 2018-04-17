package com.khiancode.wm.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.khiancode.wm.washingmachine.helper.BaseActivity;
import com.khiancode.wm.washingmachine.helper.PrefUtils;

public class SignInActivity extends BaseActivity {
    final private static String TAG = "SignInActivity";

    EditText inputEmail;
    Button btnSignin;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail = findViewById(R.id.input_email);
        btnSignin = findViewById(R.id.btn_signin);
    }

    public void onClickSignIn(View view) {
        Log.d(TAG, "onClickSignIn");
        btnSignin.setEnabled(false);

        if (!validate()) {
            btnSignin.setEnabled(true);
            return;
        }

        showProgressDialog(AUTH);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onLoginSuccess();

                        hideProgressDialog();
                    }
                }, 3000);

    }

    private void onLoginSuccess() {
        final PrefUtils prefUtils = new PrefUtils(getApplicationContext());
        prefUtils.setLogin(true);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public boolean validate() {
        Log.d(TAG, "validate");
        boolean valid = true;

        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            dialogTM("Error","Please enter your machine id.");
            valid = false;
        } else {
            if (email.length() < 6 || email.length() > 10) {
                dialogTM("Error","ID between 6 and 10");
                valid = false;
            }
        }

        Log.d(TAG, "validate:"+valid);
        return valid;
    }
}
