package com.secretdevelopersltd.dexian.customhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {

    //public int noOfEntry;

    private static final String TAG = "XIAN";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CustomHomeDB.db";


    private static final String TABLE_CONTROLER = "CONTROLER";
    private static final String TABLE_ROOM = "ROOM";

    private static final String COLUMN_CID = "C_ID";
    private static final String COLUMN_CNAME = "C_NAME";
    private static final String COLUM_CCOMMAND = "C_COMAND";

    private static final String COLUMN_RID = "RID";
    private static final String COLUM_RNAME = "R_NAME";

    private static final String COLUM_BTN_ID = "BTN_ID";




    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG,"DBHandler Constructor done");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG,"DBHandler onCreat started");

        try{
            String query = "CREATE TABLE " +  TABLE_CONTROLER
                    +" ( " + COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                    COLUMN_CNAME + " VARCHAR(255)," +
                    COLUM_CCOMMAND + " VARCHAR(255)," +
                    COLUMN_RID + " INTEGER," +
                    COLUM_BTN_ID+" VARCHAR(255))";

            db.execSQL(query);

            //Secound table
            query = "CREATE TABLE " +  TABLE_ROOM
                    +" ( " + COLUMN_RID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUM_RNAME + " VARCHAR(255)," +
                    COLUM_BTN_ID+ " VARCHAR(255))";

            db.execSQL(query);

        }catch(Exception exp){
            Log.i(TAG,exp.toString());
        }
        Log.i(TAG,"DBHandler onCreate Ended");

        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_PIGEONDB;
        db.execSQL(SQL_DELETE_ENTRIES);
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_PDB2;
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);*/
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //Add Data to SQL Database
    public void AddController(Ccontroller ccontroller){

        ContentValues values = new ContentValues();

        values.put(COLUMN_CNAME, ccontroller.getC_NAME());
        values.put(COLUM_CCOMMAND, ccontroller.getC_COMMAND());
        values.put(COLUMN_RID, ccontroller.getR_ID());
        values.put(COLUM_BTN_ID, ccontroller.getBTN_ID());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTROLER,null,values);

        db.close();
    }

    public void AddRoom(Room room){

        ContentValues values = new ContentValues();

        values.put(COLUM_RNAME, room.getR_NAME());
        values.put(COLUM_BTN_ID, room.getBTN_ID());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ROOM,null,values);

        db.close();

        Log.i(TAG,"ROOM ADDED: "+room.getR_NAME()+" "+room.getBTN_ID());
    }


    //Delete Data from SQL Database
    public void DeleteRoom(String r_id){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM "+ TABLE_ROOM + " WHERE "+ COLUMN_RID + "=\"" + r_id + "\";");

        db.close();
    }

    //Print out database as String
    public String DatabaseToStringController(){

        Log.i(TAG,"PigeonDBHandler ToString");

        String dbString ="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_CONTROLER + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler toSting quety done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler toSting While loop "+ i++);
            if(c.getString(c.getColumnIndex("name"))!= null){
                dbString += c.getString(c.getColumnIndex(COLUMN_CID)) +"\t"+ c.getString(c.getColumnIndex(COLUMN_CNAME)) +"\t"+ c.getString(c.getColumnIndex(COLUMN_RID))+"\n";
            }
        }

        db.close();
        return dbString;
    }

    public void deleteTableController(){
        SQLiteDatabase db = getWritableDatabase();
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE_CONTROLER;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void deleteTableRoom(){
        SQLiteDatabase db = getWritableDatabase();
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE_ROOM;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void up(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    public int getControllerCountByRoomID(int RID) {
        String countQuery = "SELECT  * FROM " + TABLE_CONTROLER +"WHERE "+ COLUMN_RID +" = "+RID;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int noOfEntry  = cursor.getCount();
        cursor.close();
        return noOfEntry;
    }


    public int getRoomCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ROOM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int noOfEntry  = cursor.getCount();
        cursor.close();
        return noOfEntry;
    }

    public Ccontroller getControllerByID(int CID){


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_CONTROLER + " WHERE "+COLUMN_CID +" = "+ CID;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        c.moveToFirst();

        int Controller_ID = c.getInt(c.getColumnIndex(COLUMN_CID));
        String name = c.getString(c.getColumnIndex(COLUMN_CNAME));
        String comand = c.getString(c.getColumnIndex(COLUM_CCOMMAND));
        int RID = c.getInt(c.getColumnIndex(COLUMN_RID));
        String BTN_ID = c.getString(c.getColumnIndex(COLUM_BTN_ID));

        Ccontroller ccontroller = new Ccontroller(Controller_ID, name, comand, RID, BTN_ID);


        db.close();
        return ccontroller;

    }

    public Room getRoomByID(int RID){


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_ROOM + " WHERE "+COLUMN_RID +" = "+ RID;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        c.moveToFirst();

        int Room_ID = c.getInt(c.getColumnIndex(COLUMN_RID));
        String name = c.getString(c.getColumnIndex(COLUM_RNAME));
        String BTN_ID = c.getString(c.getColumnIndex(COLUM_BTN_ID));

        Room room = new Room(Room_ID, name, BTN_ID);


        db.close();
        return room;

    }


    public void deleteController(int CID){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTROLER,COLUMN_CID+ " = " +CID,null);
        db.close();
    }

    public void deleteRoom(int RID){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ROOM,COLUMN_RID+ " = " +RID,null);
        db.close();
    }


    public void updateController (Ccontroller c) {

        //AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        // ContentValues class is used to store a set of values
        //It is like name-value pairs
        // "value" part contains the values that we are going to UPDATE
        ContentValues values = new ContentValues();

        values.put(COLUMN_CID, c.getC_ID());
        values.put(COLUMN_CNAME, c.getC_NAME());
        values.put(COLUM_CCOMMAND, c.getC_COMMAND());
        values.put(COLUMN_RID, c.getR_ID());
        values.put(COLUM_BTN_ID, c.getBTN_ID());

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_CONTROLER, values, COLUMN_CID+" = " + c.getC_ID(), null);

        //myDB.update(TableName, cv, "_id="+id, null);
        // For two whereClauseArguments
        //sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_GPA, contentValues, BaseColumns._ID+"=? AND name=?", whereClauseArgument);
        db.close();

    }



    public ArrayList<Ccontroller> getAllController(){

        ArrayList<Ccontroller> ControllerList = new ArrayList<Ccontroller>();


        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_CONTROLER;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        c.moveToFirst();

        if(!c.isBeforeFirst()){
            do{

                if(c.getString(c.getColumnIndex(COLUMN_CID))!= null){

                    int Controller_ID = c.getInt(c.getColumnIndex(COLUMN_CID));
                    String name = c.getString(c.getColumnIndex(COLUMN_CNAME));
                    String comand = c.getString(c.getColumnIndex(COLUM_CCOMMAND));
                    int RID = c.getInt(c.getColumnIndex(COLUMN_RID));
                    String BTN_ID = c.getString(c.getColumnIndex(COLUM_BTN_ID));

                    Ccontroller ccontroller = new Ccontroller(Controller_ID, name, comand, RID, BTN_ID);


                    ControllerList.add(ccontroller);

                }

            }while(c.moveToNext());
        }

        db.close();

        return ControllerList;
    }

    public ArrayList<Room> getAllRoom(){

        ArrayList<Room> RoomList = new ArrayList<Room>();


        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_ROOM;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        //c.moveToFirst();

        Log.i(TAG, "ROOM COUNT = "+c.getCount());
        if(c.getCount()>0){
            while(c.moveToNext()){
                if(c.getString(c.getColumnIndex(COLUMN_RID))!= null){

                    int Room_id = c.getInt(c.getColumnIndex(COLUMN_RID));
                    String name = c.getString(c.getColumnIndex(COLUM_RNAME));
                    String BTN_ID = c.getString(c.getColumnIndex(COLUM_BTN_ID));

                    Room room = new Room(Room_id, name, BTN_ID);

                    Log.i(TAG,"ROOM ALL : "+room.getR_ID()+" "+room.getR_NAME()+" "+room.getBTN_ID());

                    RoomList.add(room);

                }
            }
        }


        db.close();

        return RoomList;
    }

    public ArrayList<Ccontroller> getAllControllerByRoomID(int RID){

        ArrayList<Ccontroller> ControllerList = new ArrayList<Ccontroller>();


        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_CONTROLER +" WHERE "+COLUMN_RID+" = "+RID;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        c.moveToFirst();

        if(!c.isBeforeFirst()){
            do{

                if(c.getString(c.getColumnIndex(COLUMN_CID))!= null){

                    int Controller_ID = c.getInt(c.getColumnIndex(COLUMN_CID));
                    String name = c.getString(c.getColumnIndex(COLUMN_CNAME));
                    String comand = c.getString(c.getColumnIndex(COLUM_CCOMMAND));
                    int room_ID = c.getInt(c.getColumnIndex(COLUMN_RID));
                    String BTN_ID = c.getString(c.getColumnIndex(COLUM_BTN_ID));

                    Ccontroller ccontroller = new Ccontroller(Controller_ID, name, comand, room_ID, BTN_ID);

                    ControllerList.add(ccontroller);

                }

            }while(c.moveToNext());
        }

        db.close();

        return ControllerList;
    }


}

