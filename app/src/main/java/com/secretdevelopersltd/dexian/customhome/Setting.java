package com.secretdevelopersltd.dexian.customhome;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Setting extends AppCompatActivity {

    private String TAG = "XIAN";


    private Button btn_room00, btn_room01, btn_room02, btn_room10, btn_room11, btn_room12;
    private Button btn_switch00, btn_switch01, btn_switch02, btn_switch03,
            btn_switch10,btn_switch11,btn_switch12,btn_switch13;
    private TextView TV_SwitchBoard;

    private HashMap<String, Room> roomBTNid;
    private HashMap<String, Ccontroller> controllerBTNid;

    private DBHandler DBH;

    int currentRoom = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initialWorks();

        loadRoomList();


    }


    private void initialWorks(){

        btn_room00 = findViewById(R.id.btn_room00);
        btn_room01 = findViewById(R.id.btn_room01);
        btn_room02 = findViewById(R.id.btn_room02);
        btn_room10 = findViewById(R.id.btn_room10);
        btn_room11 = findViewById(R.id.btn_room11);
        btn_room12 = findViewById(R.id.btn_room12);
        TV_SwitchBoard = findViewById(R.id.TV_SwitchBoard);


        btn_switch00 = findViewById(R.id.btn_switch00);
        btn_switch01 = findViewById(R.id.btn_switch01);
        btn_switch02 = findViewById(R.id.btn_switch02);
        btn_switch03 = findViewById(R.id.btn_switch03);
        btn_switch10 = findViewById(R.id.btn_switch10);
        btn_switch11 = findViewById(R.id.btn_switch11);
        btn_switch12 = findViewById(R.id.btn_switch12);
        btn_switch13 = findViewById(R.id.btn_switch13);




        DBH = new DBHandler(getApplicationContext());

        roomBTNid = new HashMap<String, Room>();
        controllerBTNid = new HashMap<String, Ccontroller>();

    }

    private void resetControllerButton(){
        btn_switch00.setText("+");
        btn_switch01.setText("+");
        btn_switch02.setText("+");
        btn_switch03.setText("+");
        btn_switch10.setText("+");
        btn_switch11.setText("+");
        btn_switch12.setText("+");
        btn_switch13.setText("+");
    }

    private void loadRoomList(){

        ArrayList<Room> roomList = DBH.getAllRoom();
        roomBTNid = new HashMap<String, Room>();

        for(int i=0;i<roomList.size();i++){

            Button tempButton = findViewById(Integer.parseInt(roomList.get(i).getBTN_ID()));

            tempButton.setText(roomList.get(i).getR_NAME());


            roomBTNid.put(""+roomList.get(i).getBTN_ID(), roomList.get(i));

        }

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

    public void onClickRoom(View view){

        Button tempBTN = findViewById(view.getId());

        if(!tempBTN.getText().toString().equals("+")){


            if(roomBTNid.get(""+view.getId()) != null){
                TV_SwitchBoard.setText(roomBTNid.get(""+view.getId()).getR_NAME()+" Switch Board");
            }

            loadControllers(view.getId());


        }else{
            XIAN("+ ROOM FOUND");

            showAddRoom(view.getId());
        }

    }

    public void onClickController(View view){

        Button tempBTN = findViewById(view.getId());

        if(!tempBTN.getText().toString().equals("+")){

            if(controllerBTNid.get(""+view.getId()) != null){
                editCommandToController(currentRoom,controllerBTNid.get(""+view.getId()));
            }



        }else{
            XIAN("+ FOUND");
            addController(currentRoom,view.getId());
        }


    }

    private void editCommandToController(final int r_id, final Ccontroller ccc){

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Setting.this);
        View myView = getLayoutInflater().inflate(R.layout.add_controller_fragment, null);

        final EditText ET_ControllerName, ET_ControllerCommand;
        Button btn_ControllerSave;

        ET_ControllerName = myView.findViewById(R.id.ET_ControllerName);
        ET_ControllerCommand = myView.findViewById(R.id.ET_ControllerCommand);
        btn_ControllerSave = myView.findViewById(R.id.btn_ControllerSave);

        ET_ControllerName.setText(ccc.getC_NAME());
        ET_ControllerCommand.setText(ccc.getC_COMMAND());


        myBuilder.setView(myView);
        final AlertDialog Dialog = myBuilder.create();
        Dialog.show();

        btn_ControllerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ET_ControllerName.getText().toString();
                String command = ET_ControllerCommand.getText().toString();

                DBHandler DBH = new DBHandler(getApplicationContext());

                Ccontroller c = new Ccontroller(0, name, command, r_id, ""+ ccc.getBTN_ID());

                DBH.deleteControllerByBTNid(r_id,""+ccc.getBTN_ID());
                DBH.updateController(c);

                Dialog.cancel();
                restart();

            }
        });
    }

    private void addController(final int r_id, final int BTN_ID){

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Setting.this);
        View myView = getLayoutInflater().inflate(R.layout.add_controller_fragment, null);

        final EditText ET_ControllerName, ET_ControllerCommand;
        Button btn_ControllerSave;

        ET_ControllerName = myView.findViewById(R.id.ET_ControllerName);
        ET_ControllerCommand = myView.findViewById(R.id.ET_ControllerCommand);
        btn_ControllerSave = myView.findViewById(R.id.btn_ControllerSave);


        myBuilder.setView(myView);
        final AlertDialog Dialog = myBuilder.create();
        Dialog.show();

        btn_ControllerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ET_ControllerName.getText().toString();
                String command = ET_ControllerCommand.getText().toString();

                DBHandler DBH = new DBHandler(getApplicationContext());

                Ccontroller c = new Ccontroller(0, name, command, r_id, ""+ BTN_ID);

                DBH.deleteControllerByBTNid(r_id,""+BTN_ID);
                DBH.AddController(c);

                Dialog.cancel();
                restart();
            }
        });
    }

    private void showAddRoom(final int BTN_ID){

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Setting.this);
        View myView = getLayoutInflater().inflate(R.layout.add_room_fragment, null);

        final EditText ET_RoomName;
        Button btn_roomSave;

        ET_RoomName = myView.findViewById(R.id.ET_RoomName);
        btn_roomSave = myView.findViewById(R.id.btn_roomSave);


        myBuilder.setView(myView);
        final AlertDialog Dialog = myBuilder.create();
        Dialog.show();

        btn_roomSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ET_RoomName.getText().toString();

                DBHandler DBH = new DBHandler(getApplicationContext());

                Room r = new Room(0, name, ""+ BTN_ID);

                DBH.deleteRoomByBTNid(""+BTN_ID);
                DBH.AddRoom(r);

                Dialog.cancel();
                restart();
            }
        });
    }

    private void loadControllers(int BTN_ID){

        resetControllerButton();

        ArrayList<Ccontroller> controllersList = new ArrayList<Ccontroller>();
        controllerBTNid = new HashMap<String, Ccontroller>();

        controllersList = DBH.getAllControllerByRoomID(roomBTNid.get(""+BTN_ID).getR_ID());
        currentRoom = roomBTNid.get(""+BTN_ID).getR_ID();

        for(int i=0;i<controllersList.size();i++){

            Button tempButton = findViewById(Integer.parseInt(controllersList.get(i).getBTN_ID()));

            tempButton.setText(controllersList.get(i).getC_NAME());

            controllerBTNid.put(""+controllersList.get(i).getBTN_ID(), controllersList.get(i)); //********


        }

    }



    private void XIAN(String xxx){

        //Toast.makeText(getApplicationContext(),xxx,Toast.LENGTH_LONG).show();
        Log.i(TAG, xxx);

    }

    private void restart(){
        finish();
        startActivity(getIntent());
    }



}

