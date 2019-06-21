package com.secretdevelopersltd.dexian.customhome;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
    }

    public void pressed(View view) {

        Toast.makeText(getApplicationContext(),""+view.getId(),Toast.LENGTH_LONG).show();
        showAddRoom(view.getId());


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

                DBH.AddRoom(r);

                Dialog.cancel();
            }
        });
    }
}
