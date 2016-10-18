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
public class AlcoholicBeverages extends Product {
    private int extraTax = 3;
    public boolean checkAge(int age)
    {
        if (age >= 18)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public double productStateTax()
    {
        return (tax+extraTax);
    }
}
