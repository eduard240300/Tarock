package DBManager;

import Domain.User;
import Exception.DBException;

import java.sql.*;

public class DBManager {
    public static Statement stmt;
    public static String ipAddress;

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ipAddress + "/Tarock", "root", "0000");
            stmt = con.createStatement();
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
                user = new User(username, password);
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

    /*public boolean updateAsset(Book book) {
        int r = 0;
        try {
            r = stmt.executeUpdate("update assets set description='"+ book.getDescription()+"', value="+ book.getValue()+
                    " where id="+ book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (r>0) return true;
        else return false;
    }*/

}