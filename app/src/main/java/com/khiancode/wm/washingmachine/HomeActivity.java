package com.khiancode.wm.washingmachine;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.khiancode.wm.washingmachine.fragment.FunctionFragment;
import com.khiancode.wm.washingmachine.fragment.ReportFragment;
import com.khiancode.wm.washingmachine.fragment.SettingFragment;
import com.khiancode.wm.washingmachine.fragment.WorkingFragment;
import com.khiancode.wm.washingmachine.helper.BaseActivity;
import com.khiancode.wm.washingmachine.helper.PrefUtils;
import com.khiancode.wm.washingmachine.helper.TimeReceiver;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private PrefUtils prefUtils;
    int func = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        prefUtils = new PrefUtils(getApplicationContext());

        if (prefUtils.getStatus()) {
            setFram(new WorkingFragment());
            toolbar.setTitle(getString(R.string.function));
        } else {
            setFram(new FunctionFragment());
            toolbar.setTitle(getString(R.string.function));
        }

    }

    public void intentOpen() {
        if (prefUtils.getStatus()) {
            startActivityForResult(new Intent(this, Status2Activity.class), func);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        } else {
            setFram(new FunctionFragment());
        }
    }

    public void intentStart(String[] value) {
        startActivityForResult(new Intent(this, StatusActivity.class).putExtra("value",value),func);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == func) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_function) {
            if (prefUtils.getStatus()) {
                setFram(new WorkingFragment());
                toolbar.setTitle(getString(R.string.function));
            } else {
                setFram(new FunctionFragment());
                toolbar.setTitle(getString(R.string.function));
            }
        } else if (id == R.id.nav_report) {
            setFram(new ReportFragment());
            toolbar.setTitle(getString(R.string.report));
        } else if (id == R.id.nav_setting) {
            setFram(new SettingFragment());
            toolbar.setTitle(getString(R.string.setting));
        } else if (id == R.id.nav_logout) {
            prefUtils.setStatus(false);
            prefUtils.setLogin(false);
            removeAlarmManager();
            startActivity(new Intent(this,SplashActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFram(Fragment fram) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content, fram);
        ft.commit();
    }

    public void removeAlarmManager() {
        Intent intent = new Intent(this, TimeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
