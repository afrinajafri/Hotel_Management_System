import java.util.Date; 
import java.time.Month; 
import java.time.LocalDate; 
import java.text.*;
import java.util.Calendar; 
import java.io.*;
import java.util.*; 
import javax.swing.*;  
import java.awt.event.*;
import java.util.ArrayList; 
import java.util.Scanner; 
import java.text.DecimalFormat;  

public class CustomerBooking extends Profile
{   
    private String roomNum;
    private String checkIn;
    private String checkOut;
    private double totalPrice;
    static DecimalFormat df2= new DecimalFormat("0.00");
    
    
    //DEFAULT CONSTRUCTOR
    public CustomerBooking()
    {  
        super();
        roomNum = null;
        checkIn = null;
        checkOut = null; 
        totalPrice = 0.0;
    }
     
    //NORMAL CONSTRUCTOR
    public CustomerBooking(int id, String name, String phone, String address, String roomNum, String checkIn, String checkOut, double totalPrice ) { 
        super( id,  name,  phone,  address); 
        this.roomNum = roomNum;
        this.checkIn = checkIn;
        this.checkOut = checkOut; 
        this.totalPrice = totalPrice;
    } 
    
    //ACCESSOR CONSTRUCTOR
    public String getRoomNum() { return roomNum; }
    
    
    public String getCheckIn() { return checkIn; }
    
    public String getCheckOut() { return checkOut; }
    
    public double getTotalPrice() { return totalPrice; }
     
    //MUTATOR CONSTRUCTOR
    public void setRoomNum(String roomNum) { this.roomNum = roomNum; }
    
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
    
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    
    //PROCESSOR METHOD - Get the booking day
    public int bookingDay(String bookingDate) throws Exception
    {  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = sdf.parse(bookingDate); 
 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
         
        calendar.add(Calendar.MONTH, 1);   
        int day = calendar.get(Calendar.DAY_OF_MONTH);  
    
        return day;
    } 
    
    //PROCESSOR METHOD - Get the booking month
    public int bookingMonth(String bookingDate) throws Exception
    {  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = sdf.parse(checkIn); 
 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
         
        calendar.add(Calendar.MONTH, 1);  
        int month = calendar.get(Calendar.MONTH);  
        
        return month;
    } 
    
    //PROCESSOR METHOD - Get the booking year
    public int bookingYear(String bookingDate) throws Exception
    {  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = sdf.parse(checkIn); 
 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
         
        calendar.add(Calendar.MONTH, 1);  
        int year = calendar.get(Calendar.YEAR);   
        
        return year;
    }  
     
    //PROCESSOR METHOD - To count the leap years  
    public int countLeapYears(String bookingDate)throws Exception
    {
        //date 0 = day
        //date 1 = month
        //date 2 = year
        
        int years = bookingYear(bookingDate);
     
        // Check if the current year needs to be considered for the count of leap years or not
        if (bookingMonth(bookingDate) <= 2)
            years--;
     
        // An year is a leap year if it is a multiple of 4, multiple of 400 and not a multiple of 100.
        return years / 4 - years / 100 + years / 400;
    }
     
    //PROCESSOR METHOD - To get the total days between check in date and check out date
    public int getDifference(String checkIn, String checkOut)throws Exception
    {
            // COUNT TOTAL NUMBER OF DAYS BEFORE FIRST DATE 'checkIn'
         int monthDays[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
            // initialize count using years and day
             int n1 = bookingYear(checkIn) * 365 + bookingDay(checkIn);
         
            // Add days for months in given date
            for (int i = 0; i < bookingMonth(checkIn) - 1; i++)
                n1 += monthDays[i];
         
            // Since every leap year is of 366 days, Add a day for every leap year
            n1 += countLeapYears(checkIn);
         
            // SIMILARLY, COUNT TOTAL NUMBER OF DAYS BEFORE 'checkOut'
         
              int n2 = bookingYear(checkOut) * 365 + bookingDay(checkOut);
            for (int i = 0; i < bookingMonth(checkOut) - 1; i++)
                n2 += monthDays[i];
            n2 += countLeapYears(checkOut);
         
            // return difference between two counts
            return (n2 - n1);
    }
    
    //PROCESSOR METHOD - To calculate total charge 10% of room per days
    public static double totalCharge(double roomPrice , int extendDays)
    { 
        double chargePrice = 0; 
        double newTot = 0;
        double totalChargePrice = 0 ;   
        
        chargePrice = roomPrice + (0.1 * roomPrice) ;  // Get the new room price after 10% charge
        totalChargePrice = chargePrice * extendDays; //Total charge price 10% percent including days 
        
        return totalChargePrice;
    } 
    
    //PROCESSOR METHOD - To calculate new total with including tax 6%
    public static double newTotal(double roomPrice, int extendDays)
    {   
        double newTotal = 0; 
        double totalChargePrice = totalCharge(roomPrice, extendDays);
        
        newTotal = (totalChargePrice * 0.06) + totalChargePrice;  //Total charge with tax 6%
         
        return newTotal;
    }
    
    //PROCESSOR METHOD - To calculate grand total if the customer wants to extend the checkout date
    public double calculateTotal(double roomPrice, double oldTotal, int extendDays)
    {  
        double newTotal =  newTotal(roomPrice,extendDays);  //Retrieve new total 
        double totalAfterExtend = 0; 
         
        totalAfterExtend = newTotal + oldTotal;  //Get the new grand total after extend the check out days
        
        return totalAfterExtend;
    }
    
    //PROCESSOR METHOD - To print new receipt after extended day
    public static void printNewReceipt(double roomPrice, int extendDays, String displayData, int totalDays , double tax
    , String roomType, double totalBeforeTax, double totalAfterTax , double totalAfterExtend)
    {
        StringBuilder sb = new StringBuilder();
         
        double newTotal =  newTotal(roomPrice,extendDays);
        double charge = totalCharge(roomPrice,extendDays);
        
        JFrame f = new JFrame();
        sb.append( 
        "***************************************************\n"
        +"                   BOOKING RECEIPT              \n"
        +"***************************************************\n"
        
        + displayData 
        + "\nTYPE OF ROOM : " + roomType
        + "\nNUMBER OF DAY(S) : " + totalDays + " days(s)"
        + "\nROOM PRICE: RM " + df2.format(roomPrice) 
        + "\nTAX (6%) : RM " + df2.format(tax) 
        
        +"\n***************************************************\n"
        +"Old Total\n"  
        + "\nTOTAL BEFORE TAX : RM " + df2.format(totalBeforeTax) 
        + "\nTOTAL AFTER TAX : RM " + df2.format(totalAfterTax)
        +"\n\n***************************************************\n"
        
        +"Total After Extended Days\n"
        + "*Charge 10% [Room Price] per days\n"
        
        + "\nNUMBER OF DAY(S) EXTENDED : " + extendDays + " days(s)"  
        + "\nCHARGE (10%) : RM " + df2.format( charge) 
        + "\nTOTAL CHARGE INCLUDING TAX(6%) : RM " + df2.format(newTotal)
        + "\n\nNEW GRAND TOTAL: RM " + df2.format(totalAfterExtend) + "\n\n");
        
        
        JOptionPane.showMessageDialog(f,sb.toString(), "RENDAHTECC HOTEL RECEIPT",  JOptionPane.PLAIN_MESSAGE);
        
    } 
     
    //DISPLAY METHOD 
    public String toString(){
        
        return "\nROOM NUMBER\t: "  + roomNum + super.toString() + "\nCHECK IN DATE\t: " + checkIn + "\nCHECK OUT DATE\t: " + checkOut;
    } 
     
}