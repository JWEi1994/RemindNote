package com.note.remindnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

public class UserSettingsActivity extends BaseActivity {
    private Switch darkMode;
    private SharedPreferences sharedPreferences;
    private Boolean night_change;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Intent intent = getIntent();
        initView();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("NIGHT_SWITCH");
                sendBroadcast(intent);
                finish();
            }
        });
//        if (isDarkMode())
//            myToolbar.setNavigationIcon(getDrawable(R.drawable.ic_settings_white_24));
//        else myToolbar.setNavigationIcon(getDrawable(R.drawable.ic_settings_black_24));
    }

    @Override
    protected void needRefresh() {
        Log.d(TAG, "needRefresh: UserSettings");
    }

    public void initView() {
        darkMode = findViewById(R.id.darkMode);
        darkMode.setChecked(sharedPreferences.getBoolean("nightMode", false));
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDarkModePref(b);
                setSelfDarkMode();
            }
        });
    }

    private void setDarkModePref(boolean dark) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("nightMode", dark);
        editor.commit();
    }

    private void setSelfDarkMode() {
        super.setNightMode();
        Intent intent = new Intent(this, UserSettingsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK && e.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent();
            intent.setAction("NIGHT_SWITCH");
            sendBroadcast(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }
}

