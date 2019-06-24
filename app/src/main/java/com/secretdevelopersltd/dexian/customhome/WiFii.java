package com.secretdevelopersltd.dexian.customhome;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class WiFii {

    String TAG = "XIAN";

    //Server variable
    private static Socket s;
    private static SocketAddress socketAddress;
    private static PrintWriter printWriter;
    private static String IP;
    private static int PORT;
    private boolean serverStatus = false;

    private String Command = "";

    WiFii(String IP, int PORT){

        this.IP = IP;
        this.PORT = PORT;

        sendCommand("READY");

    }

    public void sendCommand(String command){

        Command = command;

        SendData sd = new SendData();
        sd.execute();

    }

    //SERVER WORKS
    class SendData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                s = new Socket(IP,PORT);                                        //Connect at the socket
                printWriter = new PrintWriter(s.getOutputStream());             //Set the output stream
                printWriter.write(Command);                             //send the message through the socket
                printWriter.flush();
                printWriter.close();
                s.close();


            }catch (IOException e){
                Log.i(TAG,"Server Exception "+e);
            }

            return null;
        }

    }

}
