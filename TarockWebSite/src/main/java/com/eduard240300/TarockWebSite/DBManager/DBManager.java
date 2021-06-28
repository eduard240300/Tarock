package com.eduard240300.TarockWebSite.DBManager;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Service.BCrypt;

import java.sql.*;

public class DBManager {
    public static boolean notConnected = true;
    public static Statement stmt;
    public static String ipAddress = "localhost";

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ipAddress + "/Tarock", "root", "");
            stmt = con.createStatement();
            notConnected = false;
        } catch(Exception ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static User getUser(String username) {
        ResultSet rs;
        User user = new User(null, null);
        try {
            String statement = "SELECT * FROM Users WHERE Username = " + "'" + username + "'";
            rs = stmt.executeQuery(statement);
            try{
                rs.next();
                String password = rs.getString("Password");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                user = new User(name, username, password, email);
            }
            catch (Exception e)
            {
                throw new DBException("Username does not exist !");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return user;
    }

    public static void registerUser(User inputUser) {
        int error;
        try {
            String statement = "INSERT INTO Users(Name, Username, Password, Email)\nVALUES(" +
                    "'" + inputUser.getName() + "', " +
                    "'" + inputUser.getUsername() + "', " +
                    "'" + BCrypt.hashpw(inputUser.getPassword(), BCrypt.gensalt(12)) + "', " +
                    "'" + inputUser.getEmail() + "'" +
                    ")";
            error = stmt.executeUpdate(statement);
        } catch (SQLException e) {
            throw new DBException("Username already exists !");
        }
    }
}