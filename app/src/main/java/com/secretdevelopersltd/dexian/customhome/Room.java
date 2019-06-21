package com.secretdevelopersltd.dexian.customhome;

public class Room {


    private int R_ID;
    private String R_NAME;
    private String BTN_ID;


    public Room(int r_ID, String r_NAME) {
        R_ID = r_ID;
        R_NAME = r_NAME;
    }

    public Room(int r_ID, String r_NAME, String BTN_ID) {
        R_ID = r_ID;
        R_NAME = r_NAME;
        this.BTN_ID = BTN_ID;
    }

    public String getBTN_ID() {
        return BTN_ID;
    }

    public void setBTN_ID(String BTN_ID) {
        this.BTN_ID = BTN_ID;
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

}
