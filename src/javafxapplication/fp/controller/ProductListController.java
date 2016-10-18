/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxapplication.fp.dao.ProductDAO;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.Product;
import static javafxapplication.fp.controller.FXMLDocumentController.isAdmin;
import static javafxapplication.fp.controller.FXMLDocumentController.user;


/**
 *
 * @author Admin
 */
public class ProductListController implements Initializable {

    @FXML
    private TableView example_tableview;

    @FXML
    private TableColumn name_tablecolumn;

    @FXML
    private TableColumn email_tablecolumn;

    @FXML
    private TableColumn address_tablecolumn;

    @FXML
    private TableColumn department_tablecolumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProductDAO pd = new ProductDAO();
        List<Product> a = pd.DisplayList();
        ObservableList<ProductData> data = FXCollections.observableArrayList();
        for (int i = 0; i < a.size(); i++) {
            data.add(new ProductData(a.get(i).getId(), a.get(i).getProductName(), 
                    a.get(i).getProductType(), a.get(i).getProductPrice()));
        }
        example_tableview.getItems().clear();
        example_tableview.setItems(data);
        name_tablecolumn.setCellValueFactory(new PropertyValueFactory<ProductData, Integer>("ID"));
        email_tablecolumn.setCellValueFactory(new PropertyValueFactory<ProductData, String>("ProductName"));
        address_tablecolumn.setCellValueFactory(new PropertyValueFactory<ProductData, String>("ProductType"));
        department_tablecolumn.setCellValueFactory(new PropertyValueFactory<ProductData, Double>("ProductPrice"));
    }

    public void okProcess() {
        if(user.equals("admin") || isAdmin == true)
        {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
        }
        else
        {
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

    public static class ProductData {

        private IntegerProperty ID;
        private StringProperty ProductName;
        private StringProperty ProductType;
        private DoubleProperty ProductPrice;

        private ProductData(Integer ID, String ProductName, String ProductType, Double price) {
            this.ID = new SimpleIntegerProperty(ID);
            this.ProductName = new SimpleStringProperty(ProductName);
            this.ProductType = new SimpleStringProperty(ProductType);
            this.ProductPrice = new SimpleDoubleProperty(price);

        }

        public IntegerProperty IDProperty() {
            return ID;
        }

        public StringProperty ProductNameProperty() {
            return ProductName;
        }

        public StringProperty ProductTypeProperty() {
            return ProductType;
        }

        public DoubleProperty ProductPriceProperty() {
            return ProductPrice;
        }
    }
}
