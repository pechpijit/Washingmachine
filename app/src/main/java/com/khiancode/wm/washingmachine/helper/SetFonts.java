package com.khiancode.wm.washingmachine.helper;

import android.app.Application;

import com.khiancode.wm.washingmachine.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SetFonts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Mitr.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
