/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.model;

/**
 *
 * @author karthik
 */
public class Product {
    private int id;
    private String ProductName;
    private String ProductType;
    private double ProductPrice;
    protected double tax = 9.55;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double ProductPrice) {
        this.ProductPrice = ProductPrice;
    }
    
    public double productStateTax()
    {
        return tax;
    }
}
   
