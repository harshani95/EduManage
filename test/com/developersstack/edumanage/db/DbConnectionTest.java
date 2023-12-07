package com.developersstack.edumanage.db;

import java.sql.Connection;

class DbConnectionTest {
    public static void main(String[] args) {
        new DbConnectionTest().getInstance();
    }


    void getInstance() {
        try{
            Connection con1 = DbConnection.getInstance().getConnection();
            Connection con2 = DbConnection.getInstance().getConnection();
            Connection con3 = DbConnection.getInstance().getConnection();

            System.out.println(con1);
            System.out.println(con2);
            System.out.println(con3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}