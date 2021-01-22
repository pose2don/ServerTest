package com.java.ServerDB.main;

import com.java.ServerDB.module.ServerModule_Login;

public class ServerStart {

    //public static ArrayList<String> list = new ArrayList<String>();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("HTTP Server Start!!!local");
        new ServerModule_Login();
        //new ServerModule_Register();




    }
}

