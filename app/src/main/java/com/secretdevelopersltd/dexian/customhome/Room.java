package com.secretdevelopersltd.dexian.customhome;

public class Room {


    private int R_ID;
    private String R_NAME;
    private String R_DELETE;

    public Room(int r_ID, String r_NAME, String r_DELETE) {
        R_ID = r_ID;
        R_NAME = r_NAME;
        R_DELETE = r_DELETE;
    }

    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }

    public String getR_NAME() {
        return R_NAME;
    }

    public void setR_NAME(String r_NAME) {
        R_NAME = r_NAME;
    }

    public String getR_DELETE() {
        return R_DELETE;
    }

    public void setR_DELETE(String r_DELETE) {
        R_DELETE = r_DELETE;
    }
}
