/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.dao.OrderDAO;

/**
 *
 * @author karthik
 */
public class DeleteOrderController {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField OrderID_txt;

    public static String oID = null;

    public void deleteOrderProcess() {
        oID = OrderID_txt.getText().trim();
        if (oID.trim().equals("") || oID == null) {
            errorMsg.setText("");
            errorMsg.setText("Order ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Create a DAO instance of the model
        int oid = 0;
        OrderDAO odao = new OrderDAO();
        //Set the values from the input form
        try{
        oid = Integer.parseInt(oID);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check your input data");
            errorMsg.setTextFill(Color.web("red"));
            return;           
        }
        //Use the DAO to persist the model to database
        if(!odao.search(oid))
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Order does not exist in the database");
            errorMsg.setTextFill(Color.web("red"));
            return;             
        }
        odao.delete(oid);
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
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/UserMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
}
