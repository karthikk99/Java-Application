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
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.dao.UserDAO;
import javafxapplication.fp.model.User;

/**
 *
 * @author karthik
 */
public class ModifyUserController implements Initializable {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField UserID_txt;

    @FXML
    private TextField FName_txt;

    @FXML
    private TextField LName_txt;

    @FXML
    private TextField Dept_txt;

     @FXML
    ComboBox department_choice;
    
    private static String uid = null;
    private static String FName = null;
    private static String LName = null;
    private static String Dept = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void onComboChoice()
    {
        Dept = (String) department_choice.getSelectionModel().getSelectedItem();
    }
    
    public void modifyUserProcess() {
        uid = UserID_txt.getText().trim();
        FName = FName_txt.getText().trim();
        LName = LName_txt.getText().trim();
        if (uid.equals("") || uid == null) {
            errorMsg.setText("");
            errorMsg.setText("User ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (FName.trim().equals("") || FName == null) {
            errorMsg.setText("");
            errorMsg.setText("First Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (LName.trim().equals("") || LName == null) {
            errorMsg.setText("");
            errorMsg.setText("Last Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (Dept == null) {
            errorMsg.setText("");
            errorMsg.setText("Department cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        User u = new User();
        UserDAO ud = new UserDAO();
        try{
        u.setId(Integer.parseInt(uid));
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check input");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        u.setFirstName(FName);
        u.setLastName(LName);
        u.setDepartment(Dept);
        if(!ud.search(Integer.parseInt(uid)))
        {
            errorMsg.setText("");
            errorMsg.setText("Invalid User. Check again");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        ud.modify(u);
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
}
