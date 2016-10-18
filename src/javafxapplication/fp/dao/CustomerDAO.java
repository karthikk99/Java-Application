/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.util.List;
import javafxapplication.fp.model.Customer;
import javafxapplication.fp.Modifiable;

/**
 *
 * @author karthik
 */
public class CustomerDAO implements Modifiable<Customer> {
    //Connection object

    private Connection connection;
    //Database connection parameters
    private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
    private String username = "fpuser";
    private String password = "510";
    
    public Customer create(Customer customer)
    {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "INSERT INTO kkeshava_Customer (Name, Address, ContactNo, Age) VALUES (?, ?, ?, ?);";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getCustomerAddress());
            statement.setLong(3, customer.getCustomerContactNo());
            statement.setInt(4, customer.getAge());
            //Execute the insert
            statement.executeUpdate();
            //To get the primary key (id) of the newly inserted record
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                //Set the id field of the database to the model
                customer.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            customer = null;
            System.out.println("Error Creating a new user: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        //Return the object that was inserted with the id field set.
        return customer;
    }
    
    public Customer modify(Customer customer)
    {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to update a record to the table
        String query = "UPDATE kkeshava_Customer SET Name = ?, Address = ?, ContactNo = ?, Age = ? WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getCustomerAddress());
            statement.setLong(3, customer.getCustomerContactNo());
            statement.setInt(4, customer.getAge());
            statement.setInt(5, customer.getId());
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            customer = null;
            System.out.println("Error updating the product details: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return customer;        
    }
    
    public void delete(int id)
    {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to delete a record to the table
        String query = "DELETE FROM kkeshava_Customer WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, id);
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            id = 0;
            System.out.println("Error deleting customer: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }        
    }
    
    public List<Customer> DisplayCustomerList() {
        Statement statement = null;
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to select a record to the table
        String query = "SELECT ID,Name,Address,ContactNo,Age FROM kkeshava_Customer;";
        List<Customer> a = new ArrayList<Customer>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("ID"));
                c.setCustomerName(rs.getString("Name"));
                c.setCustomerContactNo(rs.getLong("ContactNo"));
                c.setCustomerAddress(rs.getString("Address"));
                c.setAge(rs.getInt("Age"));
                a.add(c);
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
        //Return the object that was inserted with the id field set.
        return a;
    }
    
    public boolean search(int id) {
        boolean flag = false;
        Customer c = new Customer();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT ID FROM kkeshava_Customer WHERE ID = ?";
        try{
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
}
