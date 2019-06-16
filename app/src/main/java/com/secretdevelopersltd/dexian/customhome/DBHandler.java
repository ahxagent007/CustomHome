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
    private static final String COLUMN_CDELETE = "C_DELETE";

    private static final String COLUMN_RID = "RID";
    private static final String COLUM_RNAME = "R_NAME";
    private static final String COLUMN_RDELETE = "R_DELETE";


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
                    COLUMN_CDELETE + " VARCHAR(255)," +
                    COLUMN_RID + " INTEGER)";

            db.execSQL(query);

            //Secound table
            query = "CREATE TABLE " +  TABLE_ROOM
                    +" ( " + COLUMN_RID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                    COLUM_RNAME + " VARCHAR(255)," +
                    COLUMN_RDELETE + " VARCHAR(255))";

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
        values.put(COLUMN_CDELETE, ccontroller.getR_DELETE());
        values.put(COLUMN_RID, ccontroller.getR_ID());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTROLER,null,values);

        db.close();
    }

    public void AddRoom(Room room){

        ContentValues values = new ContentValues();

        values.put(COLUM_RNAME, room.getR_NAME());
        values.put(COLUMN_RDELETE, room.getR_DELETE());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ROOM,null,values);

        db.close();
    }
    //Delete Data from SQL Database
    public void DeleteRoom(String r_id){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM "+ TABLE_ROOM + " WHERE "+ COLUMN_RID + "=\"" + r_id + "\";");

        db.close();
    }

    //Print out database as String
    public String DatabaseToString(){

        Log.i(TAG,"PigeonDBHandler ToString");

        String dbString ="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler toSting quety done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler toSting While loop "+ i++);
            if(c.getString(c.getColumnIndex("name"))!= null){
                dbString += "Pigeon No: ";
                dbString += c.getString(c.getColumnIndex("id"));
                dbString += "\n";
                dbString += c.getString(c.getColumnIndex("name"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("ringNo"));
                dbString += "\nFather: ";
                dbString += c.getString(c.getColumnIndex("father"));
                dbString += "    Mother: ";
                dbString += c.getString(c.getColumnIndex("mother"));
                dbString += "\nDetails: ";
                dbString += c.getString(c.getColumnIndex("details"));
                dbString += "\n\n\n";
            }
        }

        db.close();
        return dbString;
    }

    public void deleteTable(){
        SQLiteDatabase db = getWritableDatabase();
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE_PIGEONDB;
        db.execSQL(SQL_DELETE_ENTRIES);

        Log.i(TAG,"PigeonDBHandler DELETED");
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE_PDB2;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public int getID(String ring){

        SQLiteDatabase db = getWritableDatabase();
        int ID = 0;
        String query;
        query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler getID quety done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"getID While loop "+ i++);
            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ring)){
                ID = c.getInt(c.getColumnIndex("id"));

                break;
                }
        }

        db.close();
        return ID;
    }

    public int getIDbideshi(String ring){

        SQLiteDatabase db = getWritableDatabase();
        int ID = 0;
        String query;
        query = "SELECT * FROM "+ TABLE_PDB2 + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler getID bideshi quety done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"getID bideshi While loop "+ i++);
            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ring)){
                ID = c.getInt(c.getColumnIndex("id"));

                break;
            }
        }

        db.close();
        return ID;
    }

    public void up(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    public String[] getStringForListView (){


        String[] MamaString = new String[getPigeonsCount()];

        Log.i(TAG,"PigeonDBHandler getStringForListView");

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT name,ringNo FROM "+ TABLE_PIGEONDB + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler getStringForListView query done");

        //move to the very first in Database
        //c.moveToFirst();
        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler getStringForListView While loop "+ i);
            if(c.getString(c.getColumnIndex("name"))!= null || c.getString(c.getColumnIndex("ringNo"))!= null){
                MamaString[i] = c.getString(c.getColumnIndex("ringNo")) +"\n"+c.getString(c.getColumnIndex("name"));

                //MamaString[i][1] = c.getString(c.getColumnIndex("imageLocation")) + " " + c.getString(c.getColumnIndex("imageName"));
            }
            i++;
        }

        db.close();

        return MamaString;
    }

    public String[] getStringForListViewBackground (){


        String[] MamaString = new String[getPigeonsCountBackground()];

        Log.i(TAG,"PigeonDBHandler getStringForListView");

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT name,ringNo FROM "+ TABLE_PDB2 + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler getStringForListViewBackground query done");

        //move to the very first in Database
        //c.moveToFirst();
        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler getStringForListView Background While loop "+ i);
            if(c.getString(c.getColumnIndex("name"))!= null || c.getString(c.getColumnIndex("ringNo"))!= null){
                MamaString[i] = c.getString(c.getColumnIndex("ringNo")) +"\n"+c.getString(c.getColumnIndex("name"));

                //MamaString[i][1] = c.getString(c.getColumnIndex("imageLocation")) + " " + c.getString(c.getColumnIndex("imageName"));
            }
            i++;
        }

        db.close();

        return MamaString;
    }

    public int getPigeonsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PIGEONDB;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int noOfEntry  = cursor.getCount();
        cursor.close();
        return noOfEntry;
    }


    public int getPigeonsCountBackground() {
        String countQuery = "SELECT  * FROM " + TABLE_PDB2;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int noOfEntry  = cursor.getCount();
        cursor.close();
        return noOfEntry;
    }

    public PigeonData getPigeonData(String ringNO){
        PigeonData pgnData = new PigeonData();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler toSting quety done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler getPigeonData While loop "+ i++);

            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNO)){
                pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                pgnData.set_name(c.getString(c.getColumnIndex("name")));
                pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                pgnData.set_father(c.getString(c.getColumnIndex("father")));
                pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                pgnData.set_details(c.getString(c.getColumnIndex("details")));
                pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

                Log.i(TAG,"PigeonDBHandler getPigeonData While loop PIGEON FOUND at "+ i);

                break;

            }
        }

        db.close();
        return pgnData;

    }

    public PigeonData getPigeonDataExperiment(String ringNO){
        PigeonData pgnData = new PigeonData();

        int id = getID(ringNO);

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE id MATCH "+"\'"+id+"\'";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"PigeonDBHandler toSting quety done");

        pgnData.set_id(c.getInt(c.getColumnIndex("id")));
        pgnData.set_name(c.getString(c.getColumnIndex("name")));
        pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
        pgnData.set_father(c.getString(c.getColumnIndex("father")));
        pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
        pgnData.set_details(c.getString(c.getColumnIndex("details")));
        pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
        pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
        pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
        pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

        pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
        pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
        pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
        pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

        pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
        pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
        pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
        pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

        Log.i(TAG,"PigeonDBHandler getPigeonData While loop PIGEON FOUND at "+id);
        db.close();
        return pgnData;
    }

    public String[]  getFatherMother(String ringNo){
        String[] FM = new String[2];
        FM[0] = null;
        FM[1] = null;
        boolean found = false;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_PDB2 + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        Log.i(TAG,"PigeonDBHandler TABLE_PDB2 query done");

        int i = 0;
        do{
            if(getPigeonsCountBackground() == 0){
                Log.i(TAG,"Bideshi Pigeon is Zero");
                break;
            }
            Log.i(TAG,"getFatherMother TABLE_PDB2 While loop "+ i++);
            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNo)){
               FM[0] =  c.getString(c.getColumnIndex("father"));
                FM[1] = c.getString(c.getColumnIndex("mother"));
                found = true;
                break;
            }
        }while(c.moveToNext());
        db.close();

        if(!found){

            Log.i(TAG,"Bideshi koitor not found");
            db = getReadableDatabase();
            String query2 = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";
            //Cursor point the location in the database
            c = db.rawQuery(query2,null);
            c.moveToFirst();
            Log.i(TAG,"PigeonDBHandler TABLE_PIGEONDB query done");

            i = 0;
            do{
                if(getPigeonsCount() == 0){
                    Log.i(TAG,"Bideshi Pigeon is Zero");
                    break;
                }
                //Log.i(TAG,"getFatherMother TABLE_PIGEONDB While loop "+ i++);
                if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNo)){
                    FM[0] =  c.getString(c.getColumnIndex("father"));
                    FM[1] = c.getString(c.getColumnIndex("mother"));
                    break;
                }
            }while(c.moveToNext());
        }
        db.close();
        return FM;
    }

    public void updatePigeonData(PigeonData pgnData) {

        //AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        // ContentValues class is used to store a set of values
        //It is like name-value pairs
        // "value" part contains the values that we are going to UPDATE
        ContentValues values = new ContentValues();

        values.put(COLUMN_RINGNO, pgnData.get_ringNo());
        values.put(COLUMN_NAME, pgnData.get_name());
        values.put(COLUM_GENDER, pgnData.get_gender());
        values.put(COLUMN_FATHER, pgnData.get_father());
        values.put(COLUMN_MOTHER, pgnData.get_mother());
        values.put(COLUMN_DETAILS, pgnData.get_details());

        values.put(COLUMN_GFATHER_F,pgnData.get_fatherFather());
        values.put(COLUMN_GMOTHER_F,pgnData.get_fatherMother());
        values.put(COLUMN_GFATHER_M,pgnData.get_motherFather());
        values.put(COLUMN_GMOTHER_M,pgnData.get_motherMother());

        values.put(COLUMN_FFF,pgnData.get_fatherFF());
        values.put(COLUMN_FFM,pgnData.get_fatherFM());
        values.put(COLUMN_FMF,pgnData.get_fatherMF());
        values.put(COLUMN_FMM,pgnData.get_fatherMM());
        values.put(COLUMN_MFF,pgnData.get_motherFF());
        values.put(COLUMN_MFM,pgnData.get_motherFM());
        values.put(COLUMN_MMF,pgnData.get_motherMF());
        values.put(COLUMN_MMM,pgnData.get_motherMM());


        // If we are using multiple whereClauseArguments, array size should have to change

        String ringNo[] = new String[1];
        ringNo[0] = pgnData.get_ringNo();

        int ID = getID(pgnData.get_ringNo());

        /**
         * This is the normal SQL query for UPDATE
         UPDATE table_name
         SET column1=value, column2=value2,...
         WHERE some_column=some_value
         */
        SQLiteDatabase db = getWritableDatabase();
        Log.i(TAG,"Query is starting now................................");

        db.update(TABLE_PIGEONDB, values, "id = "+ID, null);
        //Log.i(TAG,"db.update(TABLE_PIGEONDB, values, \"id = \"+ID, null)");
        db.close();
        Log.i(TAG,"Query is DONE now................................");
        //myDB.update(TableName, cv, "_id="+id, null);
        // For two whereClauseArguments
        //sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_GPA, contentValues, BaseColumns._ID+"=? AND name=?", whereClauseArgument);
    }

    public void updatePigeonDataBideshi(PigeonData pgnData) {

        //AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        // ContentValues class is used to store a set of values
        //It is like name-value pairs
        // "value" part contains the values that we are going to UPDATE
        ContentValues values = new ContentValues();

        values.put(COLUMN_RINGNO, pgnData.get_ringNo());
        values.put(COLUMN_NAME, pgnData.get_name());
        values.put(COLUM_GENDER, pgnData.get_gender());
        values.put(COLUMN_FATHER, pgnData.get_father());
        values.put(COLUMN_MOTHER, pgnData.get_mother());
        values.put(COLUMN_DETAILS, pgnData.get_details());

        values.put(COLUMN_GFATHER_F,pgnData.get_fatherFather());
        values.put(COLUMN_GMOTHER_F,pgnData.get_fatherMother());
        values.put(COLUMN_GFATHER_M,pgnData.get_motherFather());
        values.put(COLUMN_GMOTHER_M,pgnData.get_motherMother());

        values.put(COLUMN_FFF,pgnData.get_fatherFF());
        values.put(COLUMN_FFM,pgnData.get_fatherFM());
        values.put(COLUMN_FMF,pgnData.get_fatherMF());
        values.put(COLUMN_FMM,pgnData.get_fatherMM());
        values.put(COLUMN_MFF,pgnData.get_motherFF());
        values.put(COLUMN_MFM,pgnData.get_motherFM());
        values.put(COLUMN_MMF,pgnData.get_motherMF());
        values.put(COLUMN_MMM,pgnData.get_motherMM());


        // If we are using multiple whereClauseArguments, array size should have to change

        String ringNo[] = new String[1];
        ringNo[0] = pgnData.get_ringNo();

        int ID = getIDbideshi(pgnData.get_ringNo());

        /**
         * This is the normal SQL query for UPDATE
         UPDATE table_name
         SET column1=value, column2=value2,...
         WHERE some_column=some_value
         */
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_PDB2, values, "id = "+ID, null);
        db.close();

        //myDB.update(TableName, cv, "_id="+id, null);
        // For two whereClauseArguments
        //sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_GPA, contentValues, BaseColumns._ID+"=? AND name=?", whereClauseArgument);
    }

    public PigeonData getPigeonDataByID(int ID){
        Log.i(TAG,"PigeonDBHandler getPigeonDataByID Started");

        PigeonData pgnData = new PigeonData();

        String id = ""+ID;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PIGEONDB + " WHERE 1";
        Log.i(TAG,query);

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query, null);
        Log.i(TAG,"PigeonDBHandler getPigeonDataByID query done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler getPigeonDataByID While loop "+ i++);

            if(c.getString(c.getColumnIndex("id")).equals(id)){

                Log.i(TAG,"PIGEON DATA FOUND FOR ID = "+id);

                pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                pgnData.set_name(c.getString(c.getColumnIndex("name")));
                pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                pgnData.set_father(c.getString(c.getColumnIndex("father")));
                pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                pgnData.set_details(c.getString(c.getColumnIndex("details")));
                pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

                //pgnData.set_whosPigeon(c.getString(c.getColumnIndex("Who_sPigeon")));

                break;

            }
        }
        db.close();

        return pgnData;
    }

    public PigeonData getPigeonDataByIDBideshi(int ID){
        Log.i(TAG,"PigeonDBHandler getPigeonDataByID Bideshi Started");

        PigeonData pgnData = new PigeonData();

        String id = ""+ID;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PDB2 + " WHERE 1";
        Log.i(TAG,query);

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query, null);
        Log.i(TAG,"PigeonDBHandler getPigeonDataByID query done");

        //move to the very first in Database
        //c.moveToFirst();

        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"PigeonDBHandler getPigeonDataByID While loop "+ i++);

            if(c.getString(c.getColumnIndex("id")).equals(id)){

                Log.i(TAG,"PIGEON DATA FOUND FOR ID = "+id);

                pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                pgnData.set_name(c.getString(c.getColumnIndex("name")));
                pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                pgnData.set_father(c.getString(c.getColumnIndex("father")));
                pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                pgnData.set_details(c.getString(c.getColumnIndex("details")));
                pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

                //pgnData.set_whosPigeon(c.getString(c.getColumnIndex("Who_sPigeon")));

                break;

            }
        }
        db.close();

        return pgnData;
    }

    public void deletePigeonData(String ringNo){
        int id = getID(ringNo);

        Log.i(TAG,"PIGEON DATA IS DELETED FOR RING NO:"+ringNo);

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PIGEONDB,"id = "+id,null);
        db.close();

    }

    public void deletePigeonDataBideshi(String ringNo){
        int id = getIDbideshi(ringNo);

        Log.i(TAG,"PIGEON DATA IS DELETED FOR RING NO:"+ringNo+ "FROM TABLE"+TABLE_PDB2);

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PDB2,"id = "+id,null);
        db.close();

    }

    public void updatePigeonDataByID (PigeonData pgnData,int ID) {

        //AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        // ContentValues class is used to store a set of values
        //It is like name-value pairs
        // "value" part contains the values that we are going to UPDATE
        ContentValues values = new ContentValues();

        values.put(COLUMN_RINGNO, pgnData.get_ringNo());
        values.put(COLUMN_NAME, pgnData.get_name());
        values.put(COLUM_GENDER, pgnData.get_gender());
        values.put(COLUMN_FATHER, pgnData.get_father());
        values.put(COLUMN_MOTHER, pgnData.get_mother());
        values.put(COLUMN_DETAILS, pgnData.get_details());

        values.put(COLUMN_GFATHER_F,pgnData.get_fatherFather());
        values.put(COLUMN_GMOTHER_F,pgnData.get_fatherMother());
        values.put(COLUMN_GFATHER_M,pgnData.get_motherFather());
        values.put(COLUMN_GMOTHER_M,pgnData.get_motherMother());

        values.put(COLUMN_FFF, pgnData.get_fatherFF());
        values.put(COLUMN_FFM, pgnData.get_fatherFM());
        values.put(COLUMN_FMF, pgnData.get_fatherMF());
        values.put(COLUMN_FMM, pgnData.get_fatherMM());
        values.put(COLUMN_MFF, pgnData.get_motherFF());
        values.put(COLUMN_MFM, pgnData.get_motherFM());
        values.put(COLUMN_MMF, pgnData.get_motherMF());
        values.put(COLUMN_MMM, pgnData.get_motherMM());

        SQLiteDatabase db = getWritableDatabase();
        Log.i(TAG, "Update Pigeon by ID Query is starting now................................");
        db.update(TABLE_PIGEONDB, values, "id = " + ID, null);
        Log.i(TAG, "Update Pigeon by ID Query is DONE now................................");
        //myDB.update(TableName, cv, "_id="+id, null);
        // For two whereClauseArguments
        //sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_GPA, contentValues, BaseColumns._ID+"=? AND name=?", whereClauseArgument);
        db.close();

    }

    public PigeonData getPigeonDataBoth(String ringNO){
        PigeonData pgnData = new PigeonData();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);
        Log.i(TAG,"getPigeonDataBoth quety done");

        //move to the very first in Database
        //c.moveToFirst();
        boolean found = true;
        int i = 0;
        while(c.moveToNext()){
            Log.i(TAG,"getPigeonDataBoth TABLE_PDB2 While loop "+ i++);
            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNO)){
                pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                pgnData.set_name(c.getString(c.getColumnIndex("name")));
                pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                pgnData.set_father(c.getString(c.getColumnIndex("father")));
                pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                pgnData.set_details(c.getString(c.getColumnIndex("details")));
                pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

                Log.i(TAG,"getPigeonDataBoth While loop My PIGEON FOUND at "+ i);
                found = false;
                break;
            }
        }

        if(found){
            query = "SELECT * FROM "+ TABLE_PDB2 + " WHERE 1";
            //Cursor point the location in the database
            c = db.rawQuery(query,null);
            Log.i(TAG,"getPigeonDataBoth TABLE_PIGEONDB query done");
            i = 0;
            while(c.moveToNext()){
                Log.i(TAG,"getPigeonDataBoth TABLE_PIGEONDB While loop "+ i++);
                if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNO)){
                    pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                    pgnData.set_name(c.getString(c.getColumnIndex("name")));
                    pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                    pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                    pgnData.set_father(c.getString(c.getColumnIndex("father")));
                    pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                    pgnData.set_details(c.getString(c.getColumnIndex("details")));
                    pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                    pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                    pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                    pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                    pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                    pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                    pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                    pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                    pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                    pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                    pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                    pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));

                    Log.i(TAG,"getPigeonDataBoth While loop My PIGEON FOUND at "+ i);
                    break;
                }
            }
        }

        db.close();
        return pgnData;

    }

    public PigeonData getBideshiPigeonData(String ringNO){
        PigeonData pgnData = new PigeonData();

        SQLiteDatabase db = getWritableDatabase();
        String query =  "SELECT * FROM "+ TABLE_PDB2 + " WHERE 1";
        int i =0;
        Cursor c = db.rawQuery(query,null);

        while(c.moveToNext()){
                if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNO)){
                    pgnData.set_id(c.getInt(c.getColumnIndex("id")));
                    pgnData.set_name(c.getString(c.getColumnIndex("name")));
                    pgnData.set_gender(c.getString(c.getColumnIndex("Gender")));
                    pgnData.set_ringNo(c.getString(c.getColumnIndex("ringNo")));
                    pgnData.set_father(c.getString(c.getColumnIndex("father")));
                    pgnData.set_mother(c.getString(c.getColumnIndex("mother")));
                    pgnData.set_details(c.getString(c.getColumnIndex("details")));
                    pgnData.set_fatherFather(c.getString(c.getColumnIndex("grandFather_f")));
                    pgnData.set_fatherMother(c.getString(c.getColumnIndex("grandMother_f")));
                    pgnData.set_motherFather(c.getString(c.getColumnIndex("grandFather_m")));
                    pgnData.set_motherMother(c.getString(c.getColumnIndex("grandMother_m")));

                    pgnData.set_fatherFF(c.getString(c.getColumnIndex("fatherFF")));
                    pgnData.set_fatherFM(c.getString(c.getColumnIndex("fatherFM")));
                    pgnData.set_fatherMF(c.getString(c.getColumnIndex("fatherMF")));
                    pgnData.set_fatherMM(c.getString(c.getColumnIndex("fatherMM")));

                    pgnData.set_motherFF(c.getString(c.getColumnIndex("motherFF")));
                    pgnData.set_motherFM(c.getString(c.getColumnIndex("motherFM")));
                    pgnData.set_motherMF(c.getString(c.getColumnIndex("motherMF")));
                    pgnData.set_motherMM(c.getString(c.getColumnIndex("motherMM")));


                    Log.i(TAG,"getPigeonDataBoth While loop My PIGEON FOUND at "+ i);
                    break;
                }
            }
        db.close();
        return pgnData;
    }

    public boolean pigeonFound(String ringNo){
        boolean Fcuk=false;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        while(c.moveToNext()){
            if(c.getString(c.getColumnIndex("ringNo")).equalsIgnoreCase(ringNo)){
                Fcuk= true;
                break;
            }
        }
        db.close();
        if(Fcuk){
            return Fcuk;
        }else {
            return Fcuk;
        }
    }

    public ArrayList<PigeonData> getAllPigeons(){

        ArrayList<PigeonData> pigeonList = new ArrayList<PigeonData>();


        Log.i(TAG,"PigeonDBHandler getAllPigeons");

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_PIGEONDB;

        //Cursor point the location in the database
        Cursor c = db.rawQuery(query,null);

        //move to the very first in Database
        c.moveToFirst();

        if(!c.isBeforeFirst()){
            do{
                Log.i(TAG,"PigeonDBHandler TABLE = "+TABLE_PIGEONDB+" getAllPigeons ");
                if(c.getString(c.getColumnIndex("ringNo"))!= null){

                    int _id = c.getInt(c.getColumnIndex("id"));
                    String _ringNo = c.getString(c.getColumnIndex("ringNo"));
                    String _name = c.getString(c.getColumnIndex("name"));
                    String _gender = c.getString(c.getColumnIndex("Gender"));
                    String _father = c.getString(c.getColumnIndex("father"));
                    String _mother = c.getString(c.getColumnIndex("mother"));
                    String _details = c.getString(c.getColumnIndex("details"));

                    String _fatherFather = c.getString(c.getColumnIndex("grandFather_f"));
                    String _fatherMother = c.getString(c.getColumnIndex("grandMother_f"));
                    String _motherFather = c.getString(c.getColumnIndex("grandFather_m"));
                    String _motherMother = c.getString(c.getColumnIndex("grandMother_m"));

                    String _fatherFF = c.getString(c.getColumnIndex("fatherFF"));
                    String _fatherFM = c.getString(c.getColumnIndex("fatherFM"));
                    String _fatherMF = c.getString(c.getColumnIndex("fatherMF"));
                    String _fatherMM = c.getString(c.getColumnIndex("fatherMM"));
                    String _motherFF = c.getString(c.getColumnIndex("motherFF"));
                    String _motherFM = c.getString(c.getColumnIndex("motherFM"));
                    String _motherMF = c.getString(c.getColumnIndex("motherMF"));
                    String _motherMM = c.getString(c.getColumnIndex("motherMM"));


                    PigeonData p = new PigeonData(_id, _ringNo,_name,_gender,_father,_mother,_details,_fatherFather,_fatherMother,_motherFather,_motherMother,_fatherFF,_fatherFM,_fatherMF,_fatherMM,_motherFF,_motherFM,_motherMF,_motherMM);
                    pigeonList.add(p);
                    Log.i(TAG,pigeonList.size()+" = "+_ringNo);
                }

            }while(c.moveToNext());
        }


        String query2 = "SELECT * FROM "+ TABLE_PDB2;

        //Cursor point the location in the database
        c = db.rawQuery(query2,null);

        //move to the very first in Database
        c.moveToFirst();

        if(!c.isBeforeFirst()){
            do{
                Log.i(TAG,"PigeonDBHandler TABLE = "+TABLE_PDB2+" getAllPigeons");
                if(c.getString(c.getColumnIndex("ringNo"))!= null){

                    int _id = c.getInt(c.getColumnIndex("id"));
                    String _ringNo = c.getString(c.getColumnIndex("ringNo"));
                    String _name = c.getString(c.getColumnIndex("name"));
                    String _gender = c.getString(c.getColumnIndex("Gender"));
                    String _father = c.getString(c.getColumnIndex("father"));
                    String _mother = c.getString(c.getColumnIndex("mother"));
                    String _details = c.getString(c.getColumnIndex("details"));

                    String _fatherFather = c.getString(c.getColumnIndex("grandFather_f"));
                    String _fatherMother = c.getString(c.getColumnIndex("grandMother_f"));
                    String _motherFather = c.getString(c.getColumnIndex("grandFather_m"));
                    String _motherMother = c.getString(c.getColumnIndex("grandMother_m"));

                    String _fatherFF = c.getString(c.getColumnIndex("fatherFF"));
                    String _fatherFM = c.getString(c.getColumnIndex("fatherFM"));
                    String _fatherMF = c.getString(c.getColumnIndex("fatherMF"));
                    String _fatherMM = c.getString(c.getColumnIndex("fatherMM"));
                    String _motherFF = c.getString(c.getColumnIndex("motherFF"));
                    String _motherFM = c.getString(c.getColumnIndex("motherFM"));
                    String _motherMF = c.getString(c.getColumnIndex("motherMF"));
                    String _motherMM = c.getString(c.getColumnIndex("motherMM"));


                    PigeonData p = new PigeonData(_id, _ringNo,_name,_gender,_father,_mother,_details,_fatherFather,_fatherMother,_motherFather,_motherMother,_fatherFF,_fatherFM,_fatherMF,_fatherMM,_motherFF,_motherFM,_motherMF,_motherMM);
                    pigeonList.add(p);
                    Log.i(TAG,pigeonList.size()+" = "+_ringNo);
                }
            }while(c.moveToNext());
        }


        db.close();

        return pigeonList;
    }


}

