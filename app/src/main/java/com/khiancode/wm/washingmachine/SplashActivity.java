package com.khiancode.wm.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.khiancode.wm.washingmachine.helper.PrefUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final PrefUtils prefUtils = new PrefUtils(getApplicationContext());

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (prefUtils.getLogin()) {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                        }
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 3000);
    }
}
