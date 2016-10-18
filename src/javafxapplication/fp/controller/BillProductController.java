/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.*;
import javafxapplication.fp.dao.*;
import javafx.scene.input.KeyCode;
import java.util.*;
import static javafxapplication.fp.JavaFXApplication.count;
import static javafxapplication.fp.JavaFXApplication.ordLi;
import static javafxapplication.fp.JavaFXApplication.saved;
import static javafxapplication.fp.JavaFXApplication.item_cnt;


/*
 *
 * @author karthik
 */
public class BillProductController implements Initializable {

    public double total1 = 0.0;
    public double total2 = 0.0;
    public static double total = 0.0;
    public static boolean flag = false;
    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField CName_txt;

    @FXML
    private TextField CAddress_txt;

    @FXML
    private DatePicker OrderDate_txt;
    @FXML
    private TextField CAge_txt;

    @FXML
    private TextField CCNo_txt;

    @FXML
    private TextField Order_txt;

    @FXML
    private TextField ItemNo_txt;

    @FXML
    private TextField ProductID_txt;

    @FXML
    private TextField Quantity_txt;

    @FXML
    private TextField ProductPrice_txt;

    @FXML
    private TextField PType_txt;

    public static String CName = null;
    public static String CAddress = null;
    public static String CCNo = null;
    public static String CAge = null;
    public static String OrderNo = null;
    public static String ItemNo = null;
    public static String ProductID = null;
    public static String PPrice = null;
    public static String Quantity = null;
    public static String Type = null;
    //private int count = 0;
    Date odate;
    OrderDAO o = new OrderDAO();
    Order od = new Order();
    //Create the model object
    Order order = new Order();
    //Create a DAO instance of the model
    OrderDAO odao = new OrderDAO();
    Product p = new Product();
    ProductDAO pdao = new ProductDAO();
    //Set the values from the input form
    Customer cus = new Customer();
    CustomerDAO cdao = new CustomerDAO();
    //List<Order> ordLi = new ArrayList<Order>();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (count == 0) {
            int ord = o.getOrderNo();
            ord = ord + 1;
            OrderNo = String.valueOf(ord);
            Order_txt.setText(OrderNo);
            ItemNo_txt.setText(String.valueOf(item_cnt));
        } else {
            OrderNo = String.valueOf(ordLi.get(count - 1).getOrderID());
            Order_txt.setText(OrderNo);
            item_cnt = item_cnt + 1;
            ItemNo_txt.setText(String.valueOf(item_cnt));
        }
    }

    public void onClickProducts() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/ProductList.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void fetchProductDetails(final KeyEvent keyEvent) {
        ProductID = ProductID_txt.getText().trim();
        AlcoholicBeverages a = new AlcoholicBeverages();
        NonAlcoholicBeverages b = new NonAlcoholicBeverages();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            ProductDAO pad = new ProductDAO();
            Product pa = new Product();
            try {
                pa.setId(Integer.parseInt(ProductID));
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Check input data");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            Product pt = pad.searchProduct(pa.getId());
            Type = pt.getProductType();
            PType_txt.setText(Type);
            PPrice = String.valueOf(pt.getProductPrice());
            ProductPrice_txt.setText(PPrice);
            if (PPrice.equals("0.0")) {
                errorMsg.setText("");
                errorMsg.setText("Product Not Found");
                errorMsg.setTextFill(Color.web("red"));
                return;
            } else {
                errorMsg.setText("");
                errorMsg.setText("Product Found");
                errorMsg.setTextFill(Color.web("springgreen"));
            }
        }
    }

    public void OnDate() {
        odate = Date.valueOf(OrderDate_txt.getValue());
    }

    public void NextItemProcess() {
        flag = false;
        count = count + 1;
        if (saved == false) {
            errorMsg.setText("");
            errorMsg.setText("The item has to be saved before billing the order.");
            errorMsg.setTextFill(Color.web("red"));
            return;
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/BillProduct_1.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("Exception :" + ex);
            }
        }
        flag = false;
        saved = false;
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
        ordLi.clear();
        count = 0;
        saved = false;
        item_cnt = 1;
        flag = false;

    }

    public void completeProcess() {
        Product p = new Product();
        AlcoholicBeverages a = new AlcoholicBeverages();
        if (saved == false) {
            errorMsg.setText("");
            errorMsg.setText("The item has to be saved before billing the order.");
            errorMsg.setTextFill(Color.web("red"));
            return;
        } else {
            saved = false;
            for (int i = 0; i < ordLi.size(); i++) {
                o.CreateOrder(ordLi.get(i));
                if (ordLi.get(i).getProductID().getProductType().equals("Alcoholic")) {
                    total1 = total1 + (ordLi.get(i).getQuantity() * ordLi.get(i).getProductID().getProductPrice());
                } else {
                    total2 = total2 + (ordLi.get(i).getQuantity() * ordLi.get(i).getProductID().getProductPrice());
                }
            }
            if (total1 != 0.0 && total2 != 0.0) {
                total1 = total1 + (total1 * a.productStateTax() / 100);
                total2 = total2 + (total2 * p.productStateTax() / 100);
                total = total1 + total2;
            } else if (total1 == 0.0 && total2 != 0.0) {
                total = total2 + (total2 * p.getProductPrice() / 100);
            } else if (total1 != 0.0 && total2 == 0.0) {
                total = total1 + (total1 * a.productStateTax() / 100);
            }
        }
        ordLi.clear();
        count = 0;
        flag = false;
        item_cnt = 1;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/CompleteBill.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void saveProcess() {
        if (flag == true) {
            errorMsg.setText("");
            errorMsg.setText("Item already saved. Click on Next Item");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (count == 0 && saved == false) {
            CName = CName_txt.getText().trim();
            CAddress = CAddress_txt.getText().trim();
            CCNo = CCNo_txt.getText().trim();
            CAge = CAge_txt.getText().trim();
            OrderNo = Order_txt.getText().trim();
            ItemNo = ItemNo_txt.getText().trim();
            ProductID = ProductID_txt.getText().trim();
            PPrice = ProductPrice_txt.getText().trim();
            Quantity = Quantity_txt.getText().trim();

            if (CName.trim().equals("") || CName == null) {
                errorMsg.setText("");
                errorMsg.setText("Customer Name cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (CAddress.trim().equals("") || CAddress == null) {
                errorMsg.setText("");
                errorMsg.setText("Customer Address cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (CCNo.trim().equals("") || CCNo == null) {
                errorMsg.setText("");
                errorMsg.setText("Customer Contact Number cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (CAge.trim().equals("") || CAge == null) {
                errorMsg.setText("");
                errorMsg.setText("Customer Age cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (OrderNo.trim().equals("") || OrderNo == null) {
                errorMsg.setText("");
                errorMsg.setText("OrderID cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (ItemNo.trim().equals("") || ItemNo == null) {
                errorMsg.setText("");
                errorMsg.setText("Item No. cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (ProductID.trim().equals("") || ProductID == null) {
                errorMsg.setText("");
                errorMsg.setText("ProductID cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (Quantity.trim().equals("") || Quantity == null) {
                errorMsg.setText("");
                errorMsg.setText("Quantity cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (PPrice.trim().equals("") || PPrice == null) {
                errorMsg.setText("");
                errorMsg.setText("Product Price cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            try {
                if (odate.toString().trim().equals("") || odate.toString() == null) {
                    errorMsg.setText("");
                    errorMsg.setText("Order date cannot be empty");
                    errorMsg.setTextFill(Color.web("red"));
                    return;
                }
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Order date cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            cus.setCustomerName(CName);
            cus.setCustomerAddress(CAddress);
            try {
                cus.setCustomerContactNo(Long.parseLong(CCNo));
                cus.setAge(Integer.parseInt(CAge));
                order.setOrderID(Integer.parseInt(OrderNo));
                order.setItemID(Integer.parseInt(ItemNo));
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Check input data");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            order.setCustomerID(cus);
            AlcoholicBeverages alc = new AlcoholicBeverages();
            NonAlcoholicBeverages nonalc = new NonAlcoholicBeverages();
            try {
                p.setId(Integer.parseInt(ProductID));
                p.setProductPrice(Double.parseDouble(PPrice));
                order.setQuantity(Double.parseDouble(Quantity));
                int age = Integer.parseInt(CAge);
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Check input data");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            p.setProductType(Type);
            order.setOrderDate(odate);
            order.setProductID(p);
            if (Type.trim().equals("Alcoholic")) {
                if (alc.checkAge(Integer.parseInt(CAge))) {
                    errorMsg.setText("");
                    errorMsg.setText("Allowed");
                    errorMsg.setTextFill(Color.web("springgreen"));
                    ordLi.add(order);
                    saved = true;
                } else {
                    errorMsg.setText("");
                    errorMsg.setText("Check age. Not allowed!");
                    errorMsg.setTextFill(Color.web("red"));
                    return;
                }
            } else if (nonalc.allowBilling()) {
                errorMsg.setText("");
                errorMsg.setText("Purchase Allowed");
                errorMsg.setTextFill(Color.web("springgreen"));
                ordLi.add(order);
                saved = true;
            }
            saved = true;
            errorMsg.setText("");
            errorMsg.setText("Item Saved");
            errorMsg.setTextFill(Color.web("springgreen"));
            flag = true;
        }
        if (count != 0 && saved == false) {
            OrderNo = Order_txt.getText().trim();
            ItemNo = ItemNo_txt.getText().trim();
            ProductID = ProductID_txt.getText().trim();
            PPrice = ProductPrice_txt.getText().trim();
            Quantity = Quantity_txt.getText().trim();

            if (OrderNo.trim().equals("") || OrderNo == null) {
                errorMsg.setText("");
                errorMsg.setText("OrderID cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (ItemNo.trim().equals("") || ItemNo == null) {
                errorMsg.setText("");
                errorMsg.setText("Item No. cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (ProductID.trim().equals("") || ProductID == null) {
                errorMsg.setText("");
                errorMsg.setText("ProductID cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (Quantity.trim().equals("") || Quantity == null) {
                errorMsg.setText("");
                errorMsg.setText("Quantity cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (PPrice.trim().equals("") || PPrice == null) {
                errorMsg.setText("");
                errorMsg.setText("Product Price cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            if (odate.toString().trim().equals("") || odate.toString() == null) {
                errorMsg.setText("");
                errorMsg.setText("Product Price cannot be empty");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            order.setCustomerID(cus);
            try {
                order.setOrderID(Integer.parseInt(OrderNo));
                order.setItemID(Integer.parseInt(ItemNo));
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Check input data");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            AlcoholicBeverages alc = new AlcoholicBeverages();
            NonAlcoholicBeverages nonalc = new NonAlcoholicBeverages();
            p.setProductType(Type);
            try {
                p.setId(Integer.parseInt(ProductID));
                p.setProductPrice(Double.parseDouble(PPrice));
                order.setQuantity(Double.parseDouble(Quantity));
                int age = Integer.parseInt(CAge);
            } catch (Exception e) {
                errorMsg.setText("");
                errorMsg.setText("Error! Check input data");
                errorMsg.setTextFill(Color.web("red"));
                return;
            }
            order.setOrderDate(odate);
            order.setProductID(p);
            if (Type.trim().equals("Alcoholic")) {
                if (alc.checkAge(Integer.parseInt(CAge))) {
                    errorMsg.setText("");
                    errorMsg.setText("Allowed");
                    errorMsg.setTextFill(Color.web("springgreen"));
                    ordLi.add(order);
                    saved = true;
                } else {
                    errorMsg.setText("");
                    errorMsg.setText("Check age. Not allowed!");
                    errorMsg.setTextFill(Color.web("red"));
                    return;
                }
            } else if (nonalc.allowBilling()) {
                errorMsg.setText("");
                errorMsg.setText("Allowed");
                errorMsg.setTextFill(Color.web("springgreen"));
                ordLi.add(order);
                saved = true;
            }
            errorMsg.setText("");
            errorMsg.setText("Item saved");
            errorMsg.setTextFill(Color.web("springgreen"));
            flag = true;
        }
    }
    //This is required as stage.close() in the program will not trigger any events.
    //To have callback listeners on the close event, we trigger the external close event

    private void close() {
        dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
