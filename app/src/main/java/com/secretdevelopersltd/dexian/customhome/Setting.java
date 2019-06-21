package com.secretdevelopersltd.dexian.customhome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class Setting extends AppCompatActivity {

    private String TAG = "XIAN";


    private Button btn_room00, btn_room01, btn_room02, btn_room10, btn_room11, btn_room12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initialWorks();

        loadRoomList();


    }

    public void showPopupSetting(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.setting_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit_room:
                        //your code
                        // EX : call intent if you want to swich to other activity

                        Log.i(TAG,"edit Room");
                        startActivity(new Intent(getApplicationContext(), EditRoom.class));

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

    private void loadRoomList(){

        DBHandler DBH = new DBHandler(getApplicationContext());

        ArrayList<Room> roomList = DBH.getAllRoom();

        for(int i=0;i<roomList.size();i++){

            Button tempButton = findViewById(Integer.parseInt(roomList.get(i).getBTN_ID()));

            tempButton.setText(roomList.get(i).getR_NAME());

        }

    }

    private void initialWorks(){

        btn_room00 = findViewById(R.id.btn_room00);
        btn_room01 = findViewById(R.id.btn_room01);
        btn_room02 = findViewById(R.id.btn_room02);
        btn_room10 = findViewById(R.id.btn_room10);
        btn_room11 = findViewById(R.id.btn_room11);
        btn_room12 = findViewById(R.id.btn_room12);


    }

}

