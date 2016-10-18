/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.User;

/**
 *
 * @author karthik
 */
public class FXMLDocumentController implements Initializable {
    
    public static boolean isAdmin = false;
    private Connection connection;
    //Database connection parameters
    private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
    private String username = "fpuser";
    private String password = "510";
    PreparedStatement stmt = null;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField username_txt;

    @FXML
    private PasswordField password_txt;

    public static String user = null;

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void loginProcess() {
        user = username_txt.getText().trim();
        String pass = password_txt.getText().trim();

        if (user.equals("")) {
            errorMsg.setText("");
            errorMsg.setText("Username cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }

        if (pass.equals("")) {
            errorMsg.setText("");
            errorMsg.setText("Password cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }

        if (user.equals("admin") && pass.equals("admin")) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println("Exception :" + ex);
            }
        } else {
            //Connection object
            //Get a connection
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Error creating connection to database: " + e);
                System.exit(-1);
            }
            try {
                String query = "SELECT UserName,Password,UserID,isAdmin FROM kkeshava_Login WHERE UserName = ?";
                //Use prepared statements to avoid SQL injection attacks
                stmt = connection.prepareStatement(query);
                stmt.setString(1, user);
                ResultSet rs = stmt.executeQuery();
                String password123 = null;
                boolean isaUser = false;
                while(rs.next()) 
                {
                    isaUser = true;
                    String username = rs.getString("UserName");
                    password123 = rs.getString("Password");
                    isAdmin = rs.getBoolean("isAdmin");
                }
                if (isaUser == true) {
                    if (pass.equals(password123)) {
                        if (isAdmin == true) {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException ex) {
                                System.out.println("Exception :" + ex);
                            }
                        } else {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/UserMenu.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException ex) {
                                System.out.println("Exception :" + ex);
                            }
                        }
                    } else {
                        errorMsg.setText("");
                        errorMsg.setText("Sorry, Password is wrong !");
                        errorMsg.setTextFill(Color.web("red"));
                        return;
                    }
                } else {
                    if(user.equals("admin"))
                    {
                        if(!pass.equals("admin"))
                        {
                         errorMsg.setText("");
                         errorMsg.setText("Sorry, password is wrong !");
                         errorMsg.setTextFill(Color.web("red"));
                         return;   
                        }                          
                    }else{
                    errorMsg.setText("");
                    errorMsg.setText("Sorry, username is wrong !");
                    errorMsg.setTextFill(Color.web("red"));
                    return;
                    }
                }
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
            //Close the connection to the database - Very important!!!
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e);
            }
        }
    }
}
