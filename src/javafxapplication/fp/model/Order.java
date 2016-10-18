/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.model;
import javafxapplication.fp.model.Product;
import javafxapplication.fp.model.Customer;
import java.util.*;

/**
 *
 * @author karthik
 */
public class Order {

    private int OrderID;
    private int ItemID;
    private Product ProductID;
    private Customer CustomerID;
    private double Quantity;
    private Date orderDate;

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public Product getProductID() {
        return ProductID;
    }

    public void setProductID(Product ProductID) {
        this.ProductID = ProductID;
    }

    public Customer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Customer CustomerID) {
        this.CustomerID = CustomerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


}
