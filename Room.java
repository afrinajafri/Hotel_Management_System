import java.io.*;
import java.util.*; 
import javax.swing.*;  
import java.awt.event.*;
import java.util.ArrayList; 
import java.util.Scanner; 
import java.text.DecimalFormat;
public class Room
{  
    private char code;  
    private String type; 
    private String beds;
    private int roomNum;
    private String capacity;
    private double price; 
    static DecimalFormat df2= new DecimalFormat("0.00");
    
    //DEFAULT CONSTRUCTOR
    public Room(){  
        code = '0';
        type = null;
        beds = null;
        capacity = null;
        price = 0.0; 
    }
    
    
    //NORMAL CONSTRUCTOR
    public Room(char code, String type , int roomNum, String beds, String capacity, double price ) {  
        this.code = code;
        this.type = type; 
        this.roomNum = roomNum;
        this.beds = beds;
        this.roomNum = roomNum;
        this.capacity = capacity;
        this.price = price; 
    }
    
    //ACCESSOR METHOD  
    public char getCode() { return code; }
    
    public String getType() { return type; }
    
    public int getRoomNum() { return roomNum; } 
    
    public String getBeds() { return beds; } 
    
    public String getCapacity() { return capacity; } 
    
    public double getPrice() { return price; }  
    
    
    //MUTATOR METHOD
    public void setCode(char code) { this.code = code; }
    
    public void setType(String type) { this.type = type; }
    
    public void setBeds(String beds) { this.beds = beds; }
    
    public void setCapacity(String capacity) { this.capacity = capacity; }
    
    public void setPrice(double price) { this.price = price; }
    
    //PROCESSOR METHOD - To calculate total tax price
    public double taxPrice(double totalBeforeTax)
    {
        double tax = totalBeforeTax * 0.06; //Calculate the total tax 6%
        
        return tax;
    } 
    
    //PROCESSOR METHOD - To calculate total after tax
    public double totalAfterTax(double oriPrice, double totalDays)
    {
        double totalBeforeTax = oriPrice * totalDays; //Get the normal room price and multiply with total days
        double totalAfterTax = totalBeforeTax + taxPrice(totalBeforeTax); //Get the total price with tax 6%
        
        return totalAfterTax;
    }
    
    
    //PROCESSOR METHOD - To get the room type based on user input
    public String roomTypeIdx ( int roomType_inp )
    { 
        String type_idx = "";
         if(roomType_inp == 1)
        { 
            type_idx = "Deluxed Single Room";
        }
        else if(roomType_inp == 2)
        {
            type_idx = "Kids Suite Room";
        } 
        else if(roomType_inp == 3)
        {
            type_idx = "Studio Suite Room";
        }
        else if(roomType_inp == 4)
        {
            type_idx = "Premiere Suite Room";
        }
        else if(roomType_inp == 5)
        {
            type_idx = "Three-Bedroom Apartment";
        }
        
        return type_idx ; // return the room type/name
    }
     
    //PROCESSOR METHOD - To display available rooms
    public String availableRoom(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList, int roomType_inp) 
    { 
        boolean is_room = false; 
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roomList.size()  ; i++) {
            
            if(roomType_inp == 1)
            { 
                is_room = true;
                if (bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'D') 
                {
                     sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " - AVAILABLE \n"); 
                }
                else if(!bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'D') 
                {
                     sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " -  " 
                    + bookingList.get(i).getName() + " [ " + bookingList.get(i).getPhone() + " ] \n"); 
                }
            }
            
            else if(roomType_inp == 2)
            { is_room = true;
                if (bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'K') 
                {
                    sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " - AVAILABLE \n"); 
                }
                else if(!bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'K') 
                {
                     sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " -  " 
                    + bookingList.get(i).getName() + " [ " + bookingList.get(i).getPhone() + " ] \n"); 
                }
            }
            
            else if(roomType_inp == 3)
            { is_room = true;
                if (bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'S') 
                {
                    sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " - AVAILABLE \n"); 
                }
                else if(!bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'S') 
                {
                    sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " -  " 
                    + bookingList.get(i).getName() + " [ " + bookingList.get(i).getPhone() + " ] \n"); 
                 
                }
            }
            
            else if(roomType_inp == 4)
            { is_room = true;
                if (bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'P') 
                {
                    sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " - AVAILABLE \n"); 
                }
                else if(!bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'P') 
                {
                     sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " -  " 
                    + bookingList.get(i).getName() + " [ " + bookingList.get(i).getPhone() + " ] \n"); 
                }
            }
            
            else if(roomType_inp == 5)
            { is_room = true;
                if (bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'T') 
                {
                   sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " - AVAILABLE \n"); 
                }
                else if(!bookingList.get(i).getRoomNum().equals("") && roomList.get(i).getCode() == 'T') 
                {
                    sb.append(roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum() + " -  " 
                    + bookingList.get(i).getName() + " [ " + bookingList.get(i).getPhone() + " ] \n"); 
                }
            }
        } 
        
        return sb.toString();
    } 
    
    //PROCESSOR METHOD - To display rooms menu
    public String displayRoomMenu(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) 
    {
        return "[1] " + roomList.get(0).getType() +  "\n" + roomList.get(0).getBeds() + "\n" + roomList.get(0).getCapacity() +  "\nRM " + df2.format(roomList.get(0).getPrice()) +
         "\n\n[2] " + roomList.get(11).getType() +  "\n" + roomList.get(11).getBeds() + "\n" + roomList.get(11).getCapacity() + "\nRM " + df2.format(roomList.get(11).getPrice()) +
         "\n\n[3] " + roomList.get(21).getType() +  "\n" + roomList.get(21).getBeds() + "\n" + roomList.get(21).getCapacity() + "\nRM " + df2.format(roomList.get(21).getPrice()) +
         "\n\n[4] " + roomList.get(31).getType() +  "\n" + roomList.get(31).getBeds() + "\n" + roomList.get(31).getCapacity() + "\nRM " + df2.format(roomList.get(31).getPrice()) +
         "\n\n[5] " + roomList.get(41).getType() +  "\n" + roomList.get(41).getBeds() + "\n" + roomList.get(41).getCapacity() + "\nRM " + df2.format(roomList.get(41).getPrice()) +
         "\n\nChoose Room Type [1-5] : \n";
    }
        
      
    //DISPLAY METHOD  
    public String toString(){
        return "Code : "  +  code+ "\nType : " +  type + "\nRoom Num :" + roomNum + "\nBeds : " +  beds + "\nCapacity: " +  capacity + "\nPrice: " + price;
    }
    
}
