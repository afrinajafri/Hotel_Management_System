import java.io.*;
import java.util.*; 
import javax.swing.*;  
import java.awt.event.*;
public class Admin extends Profile
{
    private String username;
    private String pass; 
    private double totalCompanyTax;
    
    //DEFAULT CONSTRUCTOR
    public Admin()
    {
        super();
        username = null;
        pass = null;
    }
     
    //NORMAL CONSTRUCTOR
    public Admin(int id, String name, String phone, String address, String username, String pass ) { 
        super( id,  name,  phone,  address); 
        this.username = username;
        this.pass = pass;
    }
     
    //ACCESSOR METHOD
    public String getUsername(){ return username; }
     
    public String getPass() { return pass; }   
    
    //DEFAULT CONSTRUCTOR
    public double calculateTotal(double totalBeforeTax,double tax,int totalCustomer)
    { 
      double totalAfterTax = 0 ;
     
      totalAfterTax = totalBeforeTax - (totalBeforeTax * tax);
            
        return totalAfterTax;
    }
    
    //DISPLAY METHOD
    public String toString()
    {
        return username + " " + pass;
    }
} 