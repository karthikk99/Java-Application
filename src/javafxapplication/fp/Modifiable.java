/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp;
/**
 *
 * @author karthik
 * @param <T>
 */
public interface Modifiable <T>{
    /**
     *
     * @param t class object 
     * @return class object
     */
    public T create(T t);
    public T modify(T t);
    public void delete(int t);
    public boolean search(int t);
}
