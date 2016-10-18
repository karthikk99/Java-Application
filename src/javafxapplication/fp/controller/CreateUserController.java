/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.User;
import javafx.scene.control.ComboBox;
import javafxapplication.fp.dao.UserDAO;

/**
 *
 * @author karthik
 */
public class CreateUserController implements Initializable {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField FirstName_txt;

    @FXML
    private TextField LastName_txt;

    @FXML
    ComboBox department_choice;
    
    @FXML
    CheckBox admin_input;
    
    @FXML
    private TextField username_txt;

    @FXML
    private PasswordField password_txt;

    public static String FName = null;
    public static String LName = null;
    public static String Dept = null;
    public static String UName = null;
    public static String Pass = null;
    public static boolean isAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isAdmin = false;
    }
    //Method to set the parent stage of the current view

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void onSelected(ActionEvent e)
    {
        isAdmin = false;
        if(admin_input.isSelected())
        {
            isAdmin = true;
        }
        else
        {
            isAdmin = false;
        }
    }
    
    public void onComboChoice()
    {
        Dept = (String) department_choice.getSelectionModel().getSelectedItem();
    }
    
    public void createUserProcess() {
        FName = FirstName_txt.getText().trim();
        LName = LastName_txt.getText().trim();    
        UName = username_txt.getText().trim();
        Pass = password_txt.getText().trim();

        if (FName.trim().equals("") || FName == null) {
            errorMsg.setText("");
            errorMsg.setText("First Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (LName.trim().equals("") || LName == null) {
            errorMsg.setText("");
            errorMsg.setText("Last name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (Dept == null) {
            errorMsg.setText("");
            errorMsg.setText("Department cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (UName.trim().equals("") || UName == null) {
            errorMsg.setText("");
            errorMsg.setText("Username cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (Pass.trim().equals("") || Pass == null) {
            errorMsg.setText("");
            errorMsg.setText("Password cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }

        //Create the model object
        User user = new User();
        //Create a DAO instance of the model
        UserDAO udao = new UserDAO();
        //Set the values from the input form
        user.setFirstName(FName);
        user.setLastName(LName);
        user.setDepartment(Dept);
        user.setIsAdmin(isAdmin);
        user.setUserName(UName);
        user.setPassword(Pass);       
        //Use the DAO to persist the model to database
        if((udao.checkUname(UName)) == true)
        {
            errorMsg.setText("");
            errorMsg.setText("Username already exists!");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        udao.create(user);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/success.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void cancelProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void LogoutProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    //This is required as stage.close() in the program will not trigger any events.
    //To have callback listeners on the close event, we trigger the external close event

    private void close() {
        dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
