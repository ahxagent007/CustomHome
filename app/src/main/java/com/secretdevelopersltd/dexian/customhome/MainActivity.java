package com.secretdevelopersltd.dexian.customhome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    private String TAG= "XIAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.setting:
                        //your code
                        // EX : call intent if you want to swich to other activity

                        startActivity(new Intent(getApplicationContext(), Setting.class));

                        Log.i(TAG,"setting");
                        return true;
                    case R.id.about:
                        Log.i(TAG,"about");

                        startActivity(new Intent(getApplicationContext(), About.class));

                        //your code
                        return true;
                    default:
                        return false;
                }

            }
        });
    }


}
