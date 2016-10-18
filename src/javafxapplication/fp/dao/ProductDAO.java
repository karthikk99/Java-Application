/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.dao;

import javafxapplication.fp.model.Product;
import javafxapplication.fp.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafxapplication.fp.Modifiable;
import static javafxapplication.fp.controller.FXMLDocumentController.user;

/**
 *
 * @author karthik
 */
public class ProductDAO implements Modifiable<Product> {
//Connection object

    private Connection connection;
    //Database connection parameters
    private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
    private String username = "fpuser";
    private String password = "510";
    private String suser = user;
    
    public Product create(Product product)
    {
       //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "INSERT INTO kkeshava_Product (Name, Type, Price) VALUES (?, ?, ?) ;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
            statement.setDouble(3, product.getProductPrice());
            //Execute the insert
            statement.executeUpdate();
            //To get the primary key (id) of the newly inserted record
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                //Set the id field of the database to the model
                product.setId(resultSet.getInt(1));
            }
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
        //Return the object that was inserted with the id field set.
        return product; 
    }

    public void delete(int id) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to delete a record to the table
        String query = "DELETE FROM kkeshava_Product WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, id);
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            id = 0;
            System.out.println("Error deleting product: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
    }

    public Product modify(Product product)
    {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to update a record to the table
        String query = "UPDATE kkeshava_Product SET Name = ?, Type = ?, Price = ? WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
            statement.setDouble(3, product.getProductPrice());
            statement.setInt(4, product.getId());
            //Execute the insert
            statement.executeUpdate();
        } catch (SQLException e) {
            user = null;
            System.out.println("Error updating the product details: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return product;
    }

    public Product DisplayProduct(int id) {
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        Product product = new Product();
        //Query to select a record to the table
        String query = "SELECT ID, Name, Type, Price FROM kkeshava_Product WHERE ID = ?;";
        //Use prepared statements to avoid SQL injection attacks
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //Set the parameters to the query
            statement.setInt(1, id);
            //Execute the insert
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setProductName(resultSet.getString(2));
                product.setProductType(resultSet.getString(3));
                product.setProductPrice(resultSet.getDouble(4));
            }
        } catch (SQLException e) {
            user = null;
            System.out.println("Error retrieving the product details: " + e);
        }
        //Close the connection to the database - Very important!!!
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
        return product;
    }

    public List<Product> DisplayList() {
        Statement statement = null;
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        //Query to insert a record to the table
        String query = "SELECT ID,Name,Type,Price FROM kkeshava_Product;";
        List<Product> a = new ArrayList<Product>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setProductName(rs.getString("Name"));
                p.setProductType(rs.getString("Type"));
                p.setProductPrice(rs.getDouble("Price"));
                a.add(p);
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
        Product p = new Product();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT Name, Type, Price FROM kkeshava_Product WHERE ID = ?";
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
    
    public Product searchProduct(int id)
    {
        Product p = new Product();
        //Get a connection
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
        String query = "SELECT ID, Name, Type, Price FROM kkeshava_Product WHERE ID = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            //STEP 5: Extract data from result set
            while (rs.next()) {
                p.setId(rs.getInt("ID"));
                System.out.println(rs.getString("Name"));
                p.setProductName(rs.getString("Name"));
                p.setProductType(rs.getString("Type"));
                p.setProductPrice(rs.getDouble("Price"));
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
        return p;
    }
}
