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
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.Customer;
import static javafxapplication.fp.controller.FXMLDocumentController.isAdmin;
import static javafxapplication.fp.controller.FXMLDocumentController.user;
import javafxapplication.fp.dao.CustomerDAO;


/**
 *
 * @author Admin
 */
public class CustomerListController implements Initializable {

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
    
    @FXML
    private TableColumn age_tablecolumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerDAO cdao = new CustomerDAO();
        List<Customer> a = cdao.DisplayCustomerList();
        ObservableList<CustomerData> data = FXCollections.observableArrayList();
        for (int i = 0; i < a.size(); i++) {
            data.add(new CustomerData(a.get(i).getId(), a.get(i).getCustomerName(), a.get(i).getCustomerAddress(), a.get(i).getCustomerContactNo(), a.get(i).getAge()));
        }
        example_tableview.getItems().clear();
        example_tableview.setItems(data);
        name_tablecolumn.setCellValueFactory(new PropertyValueFactory<CustomerData, Integer>("ID"));
        email_tablecolumn.setCellValueFactory(new PropertyValueFactory<CustomerData, String>("Name"));
        address_tablecolumn.setCellValueFactory(new PropertyValueFactory<CustomerData, Integer>("Address"));
        department_tablecolumn.setCellValueFactory(new PropertyValueFactory<CustomerData, Long>("ContactNo"));
        age_tablecolumn.setCellValueFactory(new PropertyValueFactory<CustomerData, Integer>("Age"));
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

    public static class CustomerData {

        private IntegerProperty ID;
        private StringProperty Name;
        private StringProperty Address;
        private LongProperty ContactNo;
        private IntegerProperty Age;

        private CustomerData(Integer ID, String Name, String Address, Long ContactNo, Integer Age) {
            this.ID = new SimpleIntegerProperty(ID);
            this.Name = new SimpleStringProperty(Name);
            this.Address = new SimpleStringProperty(Address);
            this.ContactNo = new SimpleLongProperty(ContactNo);
            this.Age = new SimpleIntegerProperty(Age);

        }

        public IntegerProperty IDProperty() {
            return ID;
        }

        public StringProperty NameProperty() {
            return Name;
        }
        
        public StringProperty AddressProperty() {
            return Address;
        }

        public LongProperty ContactNoProperty() {
            return ContactNo;
        }

        public IntegerProperty AgeProperty() {
            return Age;
        }
    }
}
