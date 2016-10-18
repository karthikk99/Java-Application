/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.Product;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafxapplication.fp.dao.ProductDAO;

/**
 *
 * @author karthik
 */
public class DisplayProductController implements Initializable {
    
    private Stage dialogStage;
    @FXML
    private Label errorMsg;
    
    @FXML
    private TextField ProductID_txt;
    
    @FXML
    private Label success_lbl1;
    
    @FXML
    private Label success_lbl2;
    
    @FXML
    private Label success_lbl3;
    
    @FXML
    private Label success_lbl4;
    
    private static String ProductID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void displayProductProcess() {
        ProductID = ProductID_txt.getText().trim();
        if(ProductID.trim().equals("") || ProductID.trim() == null)
        {
            errorMsg.setText("");
            errorMsg.setText("Product ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
         ProductDAO pdao = new ProductDAO();
         Product prod;
         try{
         prod = pdao.DisplayProduct(Integer.parseInt(ProductID));
         }catch(Exception e)
         {
            errorMsg.setText("");
            errorMsg.setText("Error! Check input");
            errorMsg.setTextFill(Color.web("red"));
            return;
         }
         success_lbl1.setText(Integer.toString(prod.getId()));
         success_lbl1.setTextFill(Color.web("green"));
         success_lbl2.setText(prod.getProductName());
         success_lbl2.setTextFill(Color.web("green"));
         success_lbl3.setText(prod.getProductType());
         success_lbl3.setTextFill(Color.web("green"));
         success_lbl4.setText(Double.toString(prod.getProductPrice()));
         success_lbl4.setTextFill(Color.web("green"));
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
