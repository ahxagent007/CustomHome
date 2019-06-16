package com.secretdevelopersltd.dexian.customhome;

public class Ccontroller {


    private int C_ID;
    private String C_NAME;
    private String C_COMMAND;
    private String C_DELETE;
    private int R_ID;

    public Ccontroller(int c_ID, String c_NAME, String c_COMMAND, String c_DELETE, int r_ID) {
        C_ID = c_ID;
        C_NAME = c_NAME;
        C_COMMAND = c_COMMAND;
        C_DELETE = c_DELETE;
        R_ID = r_ID;
    }

    public int getC_ID() {
        return C_ID;
    }

    public void setC_ID(int c_ID) {
        C_ID = c_ID;
    }

    public String getC_NAME() {
        return C_NAME;
    }

    public void setC_NAME(String c_NAME) {
        C_NAME = c_NAME;
    }

    public String getC_COMMAND() {
        return C_COMMAND;
    }

    public void setC_COMMAND(String c_COMMAND) {
        C_COMMAND = c_COMMAND;
    }

    public String getR_DELETE() {
        return C_DELETE;
    }

    public void setR_DELETE(String c_DELETE) {
        C_DELETE = c_DELETE;
    }

    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }
}
