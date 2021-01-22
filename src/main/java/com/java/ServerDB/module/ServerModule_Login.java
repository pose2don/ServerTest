package com.java.ServerDB.module;

import com.java.ServerDB.main.DBConnection;
import com.java.ServerDB.main.ServerStart;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;

public class ServerModule_Login {

    public ServerModule_Login(){

        HttpServer server = null;
        Integer listenPort = 12339;

        try{
            server = HttpServer.create(new InetSocketAddress(listenPort), 0);
            server.createContext("/Login", new testHandler());
             server.createContext("/Register", new RtestHandler());

            server.start();

            System.out.println("Server Strart Login");
            System.out.println("Server Strart Register");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public class testHandler implements HttpHandler {
        public void handle(HttpExchange t){
            String inputLine = "";
            String resultStr = "";

            try{
                System.out.println("testHandler");
                String reqMethod = t.getRequestMethod();
                InputStream in = t.getRequestBody();

                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer response = new StringBuffer();

                while((inputLine = br.readLine()) != null){
                    response.append(inputLine);
                    System.out.println(".");
                }
                String resStr = response.toString();

                System.out.println(resStr);

                String[] array = resStr.split("&");

                String id = array[0].substring(8);
                String pw = array[1].substring(8);


                System.out.println( id);
                System.out.println( pw);

                DBConnection connection = new DBConnection();
                System.out.println("user : " + connection.isUser(id,pw));


                Headers h = t.getResponseHeaders();
                h.add("Content-Type", "application/json;charset=UTF-8");
                h.add("Access-Control-Allow-Origin", "*");

                t.sendResponseHeaders(200, 0);
                OutputStreamWriter osw = new OutputStreamWriter(t.getResponseBody(), "UTF-8");

                if (connection.isUser(id,pw)) {
                    osw.write("user ok");
                }else if (!connection.isUser(id,pw))
                {
                    osw.write("wrong id or pw");
                }


                //ServerStart.list.clear();



                try{
                    if(osw != null)
                        osw.close();

                    t.close();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    public class RtestHandler implements HttpHandler {
        public void handle(HttpExchange t){
            String inputLine = "";
            String resultStr = "";

            try{
                System.out.println("RtestHandler");
                String reqMethod = t.getRequestMethod();
                InputStream in = t.getRequestBody();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuffer response = new StringBuffer();
                while((inputLine = br.readLine()) != null){
                    response.append(inputLine);
                }
                String resStr = response.toString();

                System.out.println(resStr);

                String[] array = resStr.split("&");

                String id = array[0].substring(8);
                String pw = array[1].substring(8);
                String email = array[2].substring(8).replace("%40", "@");


                System.out.println( id);
                System.out.println( pw);
                System.out.println( email);

                System.out.println( (array[2].substring(8)).replace("%40", "@"));


                DBConnection connection = new DBConnection();
                connection.Save(id,pw,email);


                Headers h = t.getResponseHeaders();
                h.add("Content-Type", "application/json;charset=UTF-8");
                h.add("Access-Control-Allow-Origin", "*");

                t.sendResponseHeaders(200, 0);
                OutputStreamWriter osw = new OutputStreamWriter(t.getResponseBody(), "UTF-8");

                osw.write("save ok");



                try{
                    if(osw != null)
                        osw.close();

                    t.close();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}



