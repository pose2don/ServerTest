package com.java.ServerDB.main;

import java.sql.*;

public class DBConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement pstmt = null;

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/boardcar", "root", "Sd357159!");
            st = con.createStatement();
            System.out.println("1");
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    public boolean isUser(String UserID, String UserPW) {
        try {
            System.out.println("2");

            String SQL = "SELECT * FROM memberinfo WHERE ID = '" + UserID + "' and PW = '" + UserPW + "'";
            rs = st.executeQuery(SQL);

            System.out.println("3");

            if (rs.next()) {

                System.out.println("4");

                return true;
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

        return false;
    }


    public void Save(String RID, String RPW, String Remail) {
        try {

            System.out.println(RID);

            String SQL = "insert into memberinfo(ID, PW, Email) values('" + RID + "' , '"+RPW+"','"+Remail+"')";

            pstmt = con.prepareStatement(SQL);

            pstmt.executeUpdate();

            System.out.println("save ok");

        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        } finally {
             if (pstmt != null) {
                 try {
                     pstmt.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             }

             if (con != null) {
                 try {
                     con.close();
             } catch (SQLException e) {
                     e.printStackTrace(); } }
        }


    }
}



