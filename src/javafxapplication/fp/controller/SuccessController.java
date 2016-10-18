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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import static javafxapplication.fp.JavaFXApplication.stage;
import static javafxapplication.fp.controller.FXMLDocumentController.user;
import static javafxapplication.fp.controller.FXMLDocumentController.isAdmin;
import static javafxapplication.fp.controller.FXMLDocumentController.user;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class SuccessController implements Initializable {

    @FXML
    private Label success_lbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        success_lbl.setText("Database update successful!");
        success_lbl.setTextFill(Color.web("green"));
    }

    public void DoneProcess() {
        if (user.equals("admin") || isAdmin == true) {
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
    }

}
