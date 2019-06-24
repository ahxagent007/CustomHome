package com.secretdevelopersltd.dexian.customhome;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditRoom extends AppCompatActivity {

    private HashMap<String, Room> roomBTNid;

    private DBHandler DBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        initialWorks();
        loadRoomList();
    }

    public void pressed(View view) {

        Toast.makeText(getApplicationContext(),""+view.getId(),Toast.LENGTH_LONG).show();
        showAddRoom(view.getId());


    }

    private void initialWorks(){

        DBH = new DBHandler(getApplicationContext());

        roomBTNid = new HashMap<String, Room>();

    }

    private void showAddRoom(final int BTN_ID){

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditRoom.this);
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
            }
        });
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
}
