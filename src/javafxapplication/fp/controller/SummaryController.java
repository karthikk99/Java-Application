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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import static javafxapplication.fp.JavaFXApplication.stage;
import static javafxapplication.fp.controller.BillProductController.total;
import static javafxapplication.fp.JavaFXApplication.count;
import static javafxapplication.fp.JavaFXApplication.item_cnt;
import static javafxapplication.fp.JavaFXApplication.ordLi;
import static javafxapplication.fp.JavaFXApplication.saved;
import java.text.DecimalFormat;
/**
 *
 * @author karthik
 */
public class SummaryController implements Initializable {
    
    @FXML
    private Label success_lbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DecimalFormat df = new DecimalFormat("#.00");
        String text = "Total amount to pay: $" + df.format(total);
        success_lbl.setText(text);
        success_lbl.setTextFill(Color.web("springgreen"));
        total = 0.0;
        count = 0;
        item_cnt = 1;
        ordLi.clear();
        saved = false;
    }

    public void DoneProcess() {
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
