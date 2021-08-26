package com.wizeline.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wizeline.model.User;
import com.wizeline.exception.WizeLineException;
import com.wizeline.exception.DataBaseConnectionException;

public class DataSource {
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static String QUERY_GET_USER = "select * from users where username =  '%s' ";

    private DataSource() { }

    public static User getUser(String username) throws Exception {
        User user =  null;
        try {
            Connection con = DataSource.getConnection();
            PreparedStatement pst = con.prepareStatement(String.format(QUERY_GET_USER, username));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString( "salt" ) );
                user.setRole(rs.getString( "role" ) );
            }
        } finally {
            closeConnection();
        }

        return user;
    }

    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://bootcamp-tht.sre.wize.mx:3306/bootcamp_tht??characterEncoding=utf8&autoReconnect=true&useSSL=false";
            String user = "secret";
            String password = "noPow3r";

            connect = DriverManager.getConnection(url, user, password);
            if (connect == null) {
                throw new WizeLineException("It was NOT possible to connect the database bootcamp_tht");
            }
        } catch (Exception ex) {
            closeConnection();
            throw new DataBaseConnectionException(ex.getMessage());
        }
        return connect;
    }

    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}