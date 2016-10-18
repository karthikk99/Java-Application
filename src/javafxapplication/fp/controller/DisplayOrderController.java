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
import javafxapplication.fp.dao.OrderDAO;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.Order;
import static javafxapplication.fp.controller.FXMLDocumentController.isAdmin;

/**
 *
 * @author Admin
 */
public class DisplayOrderController implements Initializable {
    private Stage dialogStage;
    @FXML
    private Label errorMsg;

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
    private TableColumn price_tablecolumn;

    @FXML
    private TableColumn quantity_tablecolumn;

    @FXML
    private TableColumn productID_tablecolumn;

    @FXML
    private TextField OrderID_txt;
    
    public static String OrderID;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        example_tableview.setVisible(false);
    }
    
    public void displayOrderProcess()
    {
        int order;
        OrderID = OrderID_txt.getText().trim();
        try{
            order = Integer.parseInt(OrderID);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check your input data");
            errorMsg.setTextFill(Color.web("red"));
            return;                
        }
        OrderDAO od = new OrderDAO();
        if(!od.search(order))
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Order does not exist in the database");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        List<Order> o = od.DisplayOrderList(order);
        ObservableList<OrderData> data = FXCollections.observableArrayList();
        for (int i = 0; i < o.size(); i++) {
            String dateString = null;
            DateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
            try {
                dateString = df3.format(o.get(i).getOrderDate());
            } catch (Exception ex) {
                System.out.println(ex);
            }
            data.add(new OrderData(o.get(i).getOrderID(), o.get(i).getItemID(),
                    o.get(i).getCustomerID().getId(), dateString,
                    o.get(i).getProductID().getId(), o.get(i).getQuantity(),
                    o.get(i).getProductID().getProductPrice()));
        }
        example_tableview.setVisible(true);
        example_tableview.getItems().clear();
        example_tableview.setItems(data);
        name_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Integer>("ID"));
        email_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Integer>("itemID"));
        address_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Integer>("CustomerID"));
        department_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, String>("Date"));
        productID_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Integer>("ProductID"));
        price_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Double>("Price"));
        quantity_tablecolumn.setCellValueFactory(new PropertyValueFactory<OrderData, Double>("Quantity"));       
    }
    
    public void cancelProcess() {
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
    }

    public static class OrderData {

        private IntegerProperty ID;
        private IntegerProperty itemID;
        private IntegerProperty CustomerID;
        private StringProperty Date;
        private IntegerProperty ProductID;
        private DoubleProperty Price;
        private DoubleProperty Quantity;

        private OrderData(Integer ID, Integer itemID, Integer CustomerID, String Date, Integer ProductID, Double Price, Double Quantity) {
            this.ID = new SimpleIntegerProperty(ID);
            this.itemID = new SimpleIntegerProperty(itemID);
            this.CustomerID = new SimpleIntegerProperty(CustomerID);
            this.Date = new SimpleStringProperty(Date);
            this.ProductID = new SimpleIntegerProperty(ProductID);
            this.Price = new SimpleDoubleProperty(Price);
            this.Quantity = new SimpleDoubleProperty(Quantity);
        }

        public IntegerProperty IDProperty() {
            return ID;
        }

        public IntegerProperty itemIDProperty() {
            return itemID;
        }

        public IntegerProperty CustomerIDProperty() {
            return CustomerID;
        }

        public StringProperty DateProperty() {
            return Date;
        }

        public IntegerProperty ProductIDProperty() {
            return ProductID;
        }

        public DoubleProperty PriceProperty() {
            return Price;
        }

        public DoubleProperty QuantityProperty() {
            return Quantity;
        }
    }
}
