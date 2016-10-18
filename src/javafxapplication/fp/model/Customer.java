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
public class Customer {
    private int id;
    private String CustomerName;
    private String CustomerAddress;
    private long CustomerContactNo;
    private int Age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String CustomerAddress) {
        this.CustomerAddress = CustomerAddress;
    }

    public long getCustomerContactNo() {
        return CustomerContactNo;
    }

    public void setCustomerContactNo(Long CustomerContactNo) {
        this.CustomerContactNo = CustomerContactNo;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }
    
}
