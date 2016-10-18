/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.dao;

import javafxapplication.fp.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafxapplication.fp.Modifiable;
import static javafxapplication.fp.controller.FXMLDocumentController.user;
import javafxapplication.fp.controller.ProductListController;

/**
 *
 * @author karthik
 */
public class UserDAO implements Modifiable<User> {
//Connection object

    private Connection connection;
    private ObservableList<User> data;
    //Database connection parameters
    private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
    private String username = "fpuser";
    private String password = "510";

    public User create(User user) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "INSERT INTO kkeshava_User (FirstName, LastName, Department) VALUES (?, ?, ?) ;";
        String query2 = "INSERT INTO kkeshava_Login (UserName, Password, UserID, isAdmin) VALUES (?, ?, ?, ?) ;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getDepartment());
            //Execute the insert
            statement.executeUpdate();
            //To get the primary key (id) of the newly inserted record
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                //Set the id field of the database to the model
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            user = null;
            System.out.println("Error Creating a new user: " + e);
        }
        try (PreparedStatement statement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, Integer.toString(user.getId()));
            statement.setBoolean(4, user.isIsAdmin());
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            user = null;
            System.out.println("Error Creating a new user: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        //Return the bank object that was inserted with the id field set.
        return user;
    }

    public void delete(int id) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "DELETE FROM kkeshava_User WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, id);
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            user = null;
            System.out.println("Error deleting a user: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
    }

    public List<User> DisplayUser() {
        Statement statement = null;
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "SELECT ID,FirstName,LastName,Department FROM kkeshava_User;";
        List<User> a = new ArrayList<User>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("ID"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                u.setDepartment(rs.getString("Department"));
                a.add(u);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Got an exception!" + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return a;
    }

    public User modify(User user) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the bank table
        String query = "UPDATE kkeshava_User SET FirstName = ?, LastName = ?, Department = ? WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getDepartment());
            statement.setInt(4, user.getId());
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            user = null;
            System.out.println("Error updating user details: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return user;
    }

    public boolean search(int id) {
        boolean flag = false;
        User u = new User();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT ID FROM kkeshava_User WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            //STEP 5: Extract data from result set
            while (rs.next()) {
                flag = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Got an exception!" + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return flag;
    }

    public boolean checkUname(String uname) {
        boolean flag = false;
        User u = new User();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT UserName,Password,UserID,isAdmin FROM kkeshava_Login WHERE UserName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uname);
            ResultSet rs = statement.executeQuery();
            //STEP 5: Extract data from result set
            while (rs.next()) {
                flag = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Got an exception!" + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return flag;
    }
}
