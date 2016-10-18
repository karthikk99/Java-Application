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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafxapplication.fp.model.Order;
import javafxapplication.fp.dao.*;
import javafxapplication.fp.model.Product;
import javafxapplication.fp.model.Customer;


/**
 *
 * @author karthik
 */
public class OrderDAO {
    //Connection object

    private Connection connection;
    //Database connection parameters
    private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
    private String username = "fpuser";
    private String password = "510";
    public int CustomerID = 0;
    public Order CreateOrder(Order order) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        if(order.getCustomerID().getCustomerName() != null)
        {
            CustomerDAO cdao = new CustomerDAO();
            cdao.create(order.getCustomerID());
            CustomerID = order.getCustomerID().getId();
        }
        //Query to insert a record to the table
        String query = "INSERT INTO kkeshava_Order (ID, itemID, CustomerID, Date, ProductID, Price, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?);";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, order.getOrderID());
            statement.setInt(2, order.getItemID());
            statement.setInt(3, CustomerID);
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(order.getOrderDate().getTime());
            statement.setDate(4, sqlDate);
            statement.setInt(5, order.getProductID().getId());
            statement.setDouble(6, order.getProductID().getProductPrice());
            statement.setDouble(7, order.getQuantity());
            //Execute the 
            statement.executeUpdate();
        } catch (SQLException e) {
            order = null;
            System.out.println("Error Creating a new user: " + e);
        }
        return order;
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
        String query = "DELETE FROM kkeshava_Order WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, id);
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            id = 0;
            System.out.println("Error deleting order: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
    }

    public int getOrderNo() {
        Statement statement = null;
        int max = 0;
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to select a record to the table
        String query = "SELECT MAX(ID) FROM kkeshava_Order;";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Order o = new Order();
                max = rs.getInt("MAX(ID)");
                o.setOrderID(max);
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
        return max;
    }
    
    public List<Order> DisplayOrderList(int oid)
    {
      PreparedStatement statement = null;
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to select a record to the table
        String query = "SELECT ID, itemID, CustomerID, Date, ProductID, Price, Quantity FROM kkeshava_Order WHERE ID = ?;";
        List<Order> a = new ArrayList<Order>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, oid);
            ResultSet rs = statement.executeQuery();
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Order o = new Order();
                o.setOrderID(rs.getInt("ID"));
                o.setItemID(rs.getInt("itemID"));
                Customer c = new Customer();
                c.setId(rs.getInt("CustomerID"));
                o.setCustomerID(c);
                o.setOrderDate(rs.getDate("Date"));
                Product p = new Product();
                p.setId(rs.getInt("ProductID"));
                p.setProductPrice(rs.getDouble("Price"));
                o.setProductID(p);
                o.setQuantity(rs.getDouble("Quantity"));
                a.add(o);
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
        Order c = new Order();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT ID FROM kkeshava_Order WHERE ID = ?";
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
        //Return the object that was inserted with the id field set.
        return flag;
    }
}
