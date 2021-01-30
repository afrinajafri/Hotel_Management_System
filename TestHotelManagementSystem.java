import java.io.*;
import java.util.*; 
import javax.swing.*;  
import java.awt.event.*;
import java.util.ArrayList; 
import java.util.Scanner; 
import java.text.DecimalFormat;
                
public class TestHotelManagementSystem  
{
    static DecimalFormat df2= new DecimalFormat("0.00"); // To round off to 2 points decimal format
    static JFrame f;
    static ArrayList<Admin> admin = new ArrayList<Admin>(); // Admin Array List  
    
    /******************************* MAIN *******************************/
    // This function is to read files from rooms and admins then run the whole system
    public static void main(String[] args)throws Exception
    {   
        ArrayList<CustomerBooking> bookingList = new ArrayList<CustomerBooking>(); //Customer's Booking Array
        ArrayList<Room> roomList = new ArrayList<Room>();  //Room Array List
        
        try 
        {
            BufferedReader roomData = new BufferedReader (new FileReader("RoomList.txt"));   //Read room list data from input file
            BufferedReader adminCredentials = new BufferedReader (new FileReader("adminCredentials.txt")); //Read admin data from input file
            String data=null; //Set the data to null
            
            int i = 0; //set the counter for room's data
            int j = 0; //set the counter for admin's data
            
            while ((data=roomData.readLine())!=null)//Read rooms data if not null
            {
               StringTokenizer input = new StringTokenizer(data,";"); 
                
               char code = input.nextToken().charAt(0);
               String type = input.nextToken();
               int roomNum = Integer.parseInt(input.nextToken()); 
               String beds = input.nextToken();
               String capacity = input.nextToken();
               double price = Double.parseDouble(input.nextToken()); 
                      
               Room roomArray = new Room(code, type, roomNum, beds, capacity, price);
               roomList.add(i,roomArray);
                i++;
            }
            
            while ((data=adminCredentials.readLine())!=null) //Read admin's data
            {
               StringTokenizer input = new StringTokenizer(data,";"); 
                
               int id = Integer.parseInt(input.nextToken());  
               String name = input.nextToken(); 
               String phone = input.nextToken();
               String address = input.nextToken(); 
               String username = input.nextToken(); 
               String pass = input.nextToken(); 
               
                      
               Admin adm = new Admin(id,  name,  phone,  address,  username,  pass);
               admin.add(j,adm);
               j++;
            }  
            
            roomData.close();  //close room file 
            adminCredentials.close();   //close admin file
        }//END OF TRY
        
        
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
         
        //Set an empty array list for bookingList array
        for(int i = 0 ; i < 50 ; i ++)
        {
            CustomerBooking emptyBooking  = new CustomerBooking(i, "", "" , "", "" , null , null, 0); 
            bookingList.add(i,emptyBooking);
        }    
        
        login(admin); //Display login form
        frontPage(bookingList, roomList);  
    
    }
    
    /******************************* LOGIN *******************************/
    // This function is to display login form when user firstly use the system
    public static void login(ArrayList<Admin> admin)  
    {
        JLabel jUserName = new JLabel("User Name");  
        JTextField userName = new JTextField(); //Get the username 
        JLabel jPassword = new JLabel("Password");  
        JTextField password = new JPasswordField();//Get the password
        Object[] ob = {jUserName, userName, jPassword, password};
        int result = JOptionPane.showConfirmDialog(null, ob, "LOGIN", JOptionPane.OK_CANCEL_OPTION);
    
        if (result == JOptionPane.OK_OPTION) //If user click on OK button
        {
            if(admin.get(0).getUsername().equals(userName.getText()) && admin.get(0).getPass().equals(password.getText())) //If user credentials is correct
            {
              f=new JFrame();   
              ImageIcon icon = new ImageIcon("rendahteccHotel.jpg");
              JLabel label = new JLabel(icon);
              f.add(label);
              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              f.pack();
              f.setVisible(true);
              f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
              JOptionPane.showMessageDialog(f,"Hello, " + admin.get(0).getName() + "!" +
              "\nWelcome to RendahTecc Hotel Management System.", "Welcome" , JOptionPane.INFORMATION_MESSAGE);   
            }
            else //Display error message if crendentials is wrong
            {
                f=new JFrame();  
                JOptionPane.showMessageDialog(null,"Wrong Username/Password!. Please try again." , "ERROR", JOptionPane.ERROR_MESSAGE);   
                login(admin);
            }
             
        }
        else{ System. exit(0); } //Close the program if the user click on cancel
         
    }
    
    /******************************* FRONT PAGE OPTIONS *******************************/
    // This function is to display Main Menu/ Front Page after the user had logged-in
    public static void frontPage(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList)throws Exception {
    
        Object[] options = {"Main Desk", "Record", "Exit" }; //Get the options for main menu
        Object option = JOptionPane.showInputDialog(null, "What would you like to do today?\n", 
        "RendahTecc System Options", JOptionPane.QUESTION_MESSAGE,  null, options, options[0]);
        
        if (option == null) { exitMessage(); }  //If the options chosen is null then perform exit message
        String selectionString = option.toString();  //convert the option to string
        
        switch (selectionString) 
        { 
            case "Main Desk":  //Add Customer / Update record / Back to Main Menu /Exit
                mainDesk(bookingList, roomList);
                break;
        
            case "Record":                         //Search Customer's Name", "Sort Customer's Name", "Search Room" , "Generate Customers Receipts"   
                record(bookingList, roomList); //"E-Report" ,"Back to Main Menu", "Exit"   
                break;
        
            case "Exit": 
            exitMessage();
        } 
    } 
    
    
    /******************************* MAIN DESK OPTIONS *******************************/
    // This function is to display MAIN DESK OPTIONS 
    public static void mainDesk(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {
        Object[] options = {"Add Customer", "Update Record", "Back to Main Menu", "Exit" }; //Get the options for main desk
        Object option = JOptionPane.showInputDialog(null, "Select an options below \n", 
        "Main Desk", JOptionPane.QUESTION_MESSAGE,  null, options, options[0]);
        
        if (option == null) { returnToMainMenu(bookingList, roomList); } //If choice is null then return to main menu/front page
        String selectionString = option.toString(); 
         
       switch (selectionString) 
       { 
           case "Add Customer": 
            addCustomer(bookingList, roomList);
            saveAllCustReceipts(bookingList,roomList);
            break;
           
           //extend check-out / check-out / back / exit
           case "Update Record":  
           Object[] updateOptions = {"Extend Check-Out", "Check-Out" , "Back to Main Desk" , "Exit"};
            Object updateOption = JOptionPane.showInputDialog(null, "Select an options below \n", 
            "Update Record", JOptionPane.QUESTION_MESSAGE,  null, updateOptions, updateOptions[0]);
            
           if (updateOption == null) { returnToMainDesk(bookingList, roomList); }
            
           String optionString = updateOption.toString(); 
           switch (optionString) 
           { 
                case "Extend Check-Out": // Call extend check out date function
                 extendCheckOut(bookingList, roomList); 
                    break; 
                 
                case "Check-Out":  // Check out and delete customer from bookingList
                    deleteCustomer(bookingList, roomList); 
                    break; 
                     
                case "Back to Main Desk": //Return to main desk options  
                mainDesk(bookingList, roomList);
                    break;
                        
                case "Exit": //Display exit message
                exitMessage();
           }
           break;
            
           case "Back to Main Menu": //Return to front page/main menu
           frontPage(bookingList, roomList);
           break;
                        
           case "Exit": //Display exit message
           exitMessage();
        } 
    }
     
    /******************************* RECORD OPTIONS *******************************/
    public static void record(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {
        Object[] options = {"Display Customers List", "Search Customer's Name", "Search Room", "E-Report" , "Sort Customer's Name" , "Generate Customers Receipts" 
            , "Back to Main Menu", "Exit"};
        Object option = JOptionPane.showInputDialog(f, "Select an options below \n", 
        "Record", JOptionPane.QUESTION_MESSAGE,  null, options, options[0]);
        
        if (option == null) { returnToMainMenu(bookingList, roomList); }
         
        String selectionString = option.toString();
         
        switch (selectionString) 
        { 
            case "Display Customers List":
            customerList(bookingList, roomList);
            record(bookingList, roomList);
            break; 
            
            //search customer
            case "Search Customer's Name": 
            try { 
                searchCustomer(bookingList, roomList); 
            } 
            catch (Exception e) {  e.printStackTrace(); } 
            break;
            
            //display sorted customer list based on name
            case "Sort Customer's Name":
            sortCustList(bookingList, roomList);
            break; 
        
            //search room
            case "Search Room":
            searchRoom(bookingList, roomList);
            break; 
            
            case "Generate Customers Receipts":  
            saveAllCustReceipts(bookingList, roomList);
            break;
               
            //display reports
            case "E-Report":
            report(bookingList, roomList);
            break;   
            //return to front page 
            case "Back to Main Menu":
            frontPage(bookingList, roomList);
            break;
                
            case "Exit": 
            exitMessage();
        } 
    }
    
    /******************************* DISPLAY EXIT MESSAGE *******************************/ //This function is to display exit message
    public static void exitMessage()
    { 
        StringBuilder sb = new StringBuilder();
        sb.append( "Thank You for using RendahTecc Hotel Management System! \n " +  "                             " + "Have a nice day :D" );
        JOptionPane.showMessageDialog (f, sb.toString(), "EXIT",  JOptionPane.INFORMATION_MESSAGE);
        System. exit(0); 
    }
    
    /******************************* DISPLAY RETURN TO MAIN DESK MESSAGE *******************************/ //This function is to return to main desk if the user clicked on cancel button
    public static void returnToMainDesk(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {  
        JOptionPane.showMessageDialog(f,"CANCELLED!\nReturning to Main Desk" 
        ,"CANCELLED",JOptionPane.WARNING_MESSAGE); 
        mainDesk(bookingList,roomList); 
    }
    
    /******************************* DISPLAY RETURN TO MAIN MENU MESSAGE *******************************/ //This function is to return to main menu if the user clicked on cancel button
    public static void returnToMainMenu(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {  
        JOptionPane.showMessageDialog(f,"CANCELLED!\nReturning to Main Menu" 
        ,"CANCELLED",JOptionPane.WARNING_MESSAGE); 
        frontPage(bookingList,roomList); 
    }
    
    /******************************* DISPLAY RETURN TO RECORD MESSAGE *******************************/ //This function is to return to record options if the user clicked on cancel button
    public static void returnToRecord(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {  
        JOptionPane.showMessageDialog(f,"CANCELLED!\nReturning to Record Menu" 
        ,"CANCELLED",JOptionPane.WARNING_MESSAGE); 
        record(bookingList,roomList); 
    }
     
    /******************************* ADD NEW CUSTOMER *******************************/
    //This function is to add new customer to the bookingList array
    public static void addCustomer(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    { 
        f=new JFrame();
          
        String displayRoom = JOptionPane.showInputDialog(f, roomList.get(0).displayRoomMenu(bookingList,roomList) 
                                                        ,"ROOMS MENU",JOptionPane.QUESTION_MESSAGE); 
         
        if (displayRoom == null)  //If the the room choice is null then return to main desk
        { returnToMainDesk(bookingList, roomList); }
        
        //Display error message if the room choice is not same with the input
        else if(!displayRoom.equals("1") && !displayRoom.equals("2") && !displayRoom.equals("3") && !displayRoom.equals("4") && !displayRoom.equals("5"))
        {   JOptionPane.showMessageDialog(f,"WRONG INPUT!. \nPlease try again."
            ,"Room Not Found",JOptionPane.ERROR_MESSAGE); addCustomer(bookingList,roomList);
        }
    
        int roomType_inp = Integer.parseInt(displayRoom);    //Convert the string dislayRoom into integer since we only need integer from the options
        
        String addCust = JOptionPane.showInputDialog(f,roomList.get(0).availableRoom(bookingList,roomList,roomType_inp) 
        + "\nChoose a Room Number [Example: D1]","AVAILABLE ROOM",JOptionPane.QUESTION_MESSAGE);  //Show available room and input options for add new customer
        
        if (addCust == null) { returnToMainDesk(bookingList, roomList); }      
        
        int idx = 0; 
        boolean is_room = false; //Check if the room is found
        for(int i = 0 ; i < roomList.size() ; i ++ )
        {     
            String rooms = roomList.get(i).getCode() + "" + roomList.get(i).getRoomNum(); 
            if(roomList.get(i).roomTypeIdx(roomType_inp).equals(roomList.get(i).getType()))
            { 
                if (rooms.equalsIgnoreCase(addCust) &&  bookingList.get(i).getRoomNum().equals("")) 
                { 
                    is_room = true; //Set room to true if the room is found
                    idx = i; 
                    
                    try{
                    JLabel jCheckInDate = new JLabel("Check In Date [Example : 5/3/2021] "); //Insert check in date
                    JTextField checkInDate = new JTextField();
                    
                    JLabel jCheckOutDate = new JLabel("Check Out Date [Example : 8/3/2021] ");//Insert check out date
                    JTextField checkOutDate = new JTextField();
                     
                    JLabel jName = new JLabel("Full Name"); //Insert full name
                    JTextField name = new JTextField();
                    
                    JLabel jPhone = new JLabel("Phone Number"); //Insert phone number
                    JTextField phone = new JTextField();
                    
                    JLabel jAddress = new JLabel("Address"); //Insert address
                    JTextField address = new JTextField();
                    
                    Object[] ob = {jCheckInDate, checkInDate, jCheckOutDate, checkOutDate, jName, name, jPhone,phone, jAddress, address, };
                    
                    
                    JFrame f = new JFrame();
                    f.setAlwaysOnTop(true); 
                     
                    int result = JOptionPane.showConfirmDialog(f, ob, "Booking Form", JOptionPane.OK_CANCEL_OPTION);  //Display the booking form for the customer to key in data
                    
                    if (result == JOptionPane.OK_OPTION ){ //If the customer clicked on OK
                        for (int i1 = 0; i1 <  roomList.size() ; i1++) {
                            
                            int editRoomIndex1 = bookingList.get(i1).getID(); //get the room index
                            
                            if (editRoomIndex1 == idx) //Check if the room index is the same with the founded room index 
                            {    //then, set the customer informations from the JTextField by using getText()
                                bookingList.get(i1).setCheckIn(checkInDate.getText()); 
                                bookingList.get(i1).setCheckOut(checkOutDate.getText());
                                bookingList.get(i1).setName(name.getText());
                                bookingList.get(i1).setPhone(phone.getText());
                                bookingList.get(i1).setAddress(address.getText()); 
                                bookingList.get(i1).setRoomNum(addCust.toUpperCase()); 
                                
                                printReceipt(bookingList, roomList, i1);   //Print the receipts once everything is done
                                //Display success message
                                JOptionPane.showMessageDialog(f,"Data have been successfully accepted.","Saved"  ,JOptionPane.INFORMATION_MESSAGE); 
                                customerList(bookingList, roomList);
                                break;
                            
                            }
                        }  
                    }
                    else // If the customer clicked on Cancel
                    {
                        returnToMainDesk(bookingList, roomList);
                    }
                }
                catch (Exception e) { e.printStackTrace(); } 
                }
               
            }   
        }
        
        if(is_room == false) //Display error message if the room is not found
        {
            JOptionPane.showMessageDialog(f,"Room number not found! \nEither the room is vacant or you've entered wrong room number. \nPlease try again."
            ,"Room Not Found",JOptionPane.ERROR_MESSAGE);
            addCustomer(bookingList , roomList);
        }   
                                
         frontPage(bookingList, roomList); //Return to main menu/front page once the function has ended
    
    }
    
     
    /******************************* PRINT CUSTOMER'S RECEIPT *******************************/ //This function is to print customer's receipt 
    public static void printReceipt(ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList, int cust_idx ) throws Exception
    {
        //******************************* CALCULATION *******************************
        String checkIn = bookingList.get(cust_idx).getCheckIn();
        String checkOut = bookingList.get(cust_idx).getCheckOut();
        double oriPrice = roomList.get(cust_idx).getPrice();
        double totalDays = (bookingList.get(cust_idx).getDifference(checkIn,checkOut) + 1) ;
        double totalBeforeTax = oriPrice * totalDays;
        double totalAfterTax = roomList.get(cust_idx).totalAfterTax(oriPrice, totalDays);
        bookingList.get(cust_idx).setTotalPrice(totalAfterTax); 
        //**************************************************************
        
        StringBuilder sb = new StringBuilder(); //Represents a mutable sequence of characters
        sb.append( 
        "***************************************************\n"
        +"                   BOOKING RECEIPT              \n"
        +"***************************************************\n"
        + "\nTYPE OF ROOM : " + roomList.get(cust_idx).getType() 
        + bookingList.get(cust_idx).toString()   
        + "\nNUMBER OF DAY(S) : " + (bookingList.get(cust_idx).getDifference(checkIn,checkOut) + 1) + " days(s)"
        + "\nROOM PRICE: RM " + df2.format(roomList.get(cust_idx).getPrice()) 
        + "\nTAX (6%) : RM " + df2.format(roomList.get(cust_idx).taxPrice(totalBeforeTax)) 
        
        +"\n***************************************************\n"
        + "TOTAL BEFORE TAX : RM " + df2.format(totalBeforeTax) 
        + "\nTOTAL AFTER TAX : RM " + df2.format(totalAfterTax)
        +"\n***************************************************\n" 
        + "\nGRAND TOTAL: RM " + df2.format(totalAfterTax) + "\n\n");
        
        
        JOptionPane.showMessageDialog(f,sb.toString(), "RENDAHTECC HOTEL RECEIPT",  JOptionPane.PLAIN_MESSAGE); //Display the receipt
    } 


    /******************************* DELETE CUSTOMER IF THEY WANT TO CHECK OUT *******************************/ //This function is to delete customer data from the bookingList array
    public static void deleteCustomer( ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception
    {  
        String roomNumber = JOptionPane.showInputDialog(f,"What is the room number? \n[Example : D1]" ,"Check-Out",JOptionPane.QUESTION_MESSAGE); //Display the room number input
    
        if (roomNumber == null) { returnToMainDesk(bookingList,roomList); } //If the room number input is null then return to main desk
        
        boolean searchRoomNum=false;  //Do searching for room
        int idx = 0;
        
        for (int i = 0; i < bookingList.size()  ; i++) { 
            if (bookingList.get(i).getRoomNum().equals(roomNumber.toUpperCase())) { 
                searchRoomNum = true;  //Set the search room to true if the room is found
                ///// Delete the customer's data by setting the array to null
                printReceipt(bookingList, roomList, i); 
                bookingList.get(i).setName("");
                bookingList.get(i).setPhone("");
                bookingList.get(i).setAddress(""); 
                bookingList.get(i).setRoomNum("");
                bookingList.get(i).setTotalPrice(0); 
                //display success message
                JOptionPane.showMessageDialog(f,"Succesfully checked out!.","Successfully Check Out" ,JOptionPane.INFORMATION_MESSAGE); 
                customerList(bookingList, roomList);
            }
        }  
        
        if(searchRoomNum == false) //If searching for room is false then display error message
        {
            JOptionPane.showMessageDialog(f,"ERROR! \nEither the room is still available or you've entered wrong room number\nPlease try again."
            ,"ERROR",JOptionPane.ERROR_MESSAGE);
            deleteCustomer(bookingList , roomList);
        }
        
        frontPage(bookingList, roomList); //Return to frontpage/ main menu once the function is done
        
    } 
    
    /******************************* EXTEND CHECK OUT *******************************/ // This function is to extend the check out days  
    public static void extendCheckOut( ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList ) throws Exception
    {
        String roomNumber = JOptionPane.showInputDialog(f,"What is the room number? \n[Example : D1]" ,"Extend Check-Out",JOptionPane.QUESTION_MESSAGE); //Display the room number
    
        if (roomNumber == null) { returnToMainDesk(bookingList,roomList); } //Return to main desk if the input is null
        
        boolean searchRoomNum=false;  //Do searching for room
        int idx = 0; //Get the index
        
        for (int i = 0; i < bookingList.size()  ; i++) { 
            
            CustomerBooking value = bookingList.get(i); //encapsulation / wrap the booking list 
            Room room = roomList.get(i); //encapsulation / wrap the room list 
            
            if (value.getRoomNum().equals(roomNumber.toUpperCase())) //Check if the room number in booking list is same with input
            { 
                searchRoomNum = true; 
                idx = i; 
                
                String extendCheckOut = JOptionPane.showInputDialog(f,"How many days do you want to extend? [Example : 1 ]","Extend Days",JOptionPane.QUESTION_MESSAGE); 
                if (extendCheckOut == null) {  returnToMainDesk(bookingList,roomList); }  //User input
                
                double roomPrice = room.getPrice(); //original room price
                double oldTotal = value.getTotalPrice(); //get the old price
                int extendDays = Integer.parseInt(extendCheckOut);  //total days to be extend  
                double totalAfterExtend = value.calculateTotal(roomPrice,oldTotal,extendDays);  //total 
                /////send parameters ////
                String checkIn = value.getCheckIn();
                String checkOut = value.getCheckOut();
                double oriPrice = room.getPrice();
                int totalDays = (value.getDifference(checkIn,checkOut) + 1) ;
                double totalBeforeTax = oriPrice * totalDays;
                double tax = room.taxPrice(totalBeforeTax);
                double totalAfterTax = room.totalAfterTax(oriPrice, totalDays); 
                String displayData = value.toString();
                String roomType = room.getType() ; 
                ///////////////////////////////
                 
                value.printNewReceipt(roomPrice,extendDays,displayData,totalDays ,tax,roomType,totalBeforeTax,totalAfterTax , totalAfterExtend); //Call the print new receipt function
                ///set new check out days to bookingList array 
                int oldCheckOutDay = value.bookingDay(value.getCheckOut());
                int oldCheckOutMonth = value.bookingMonth(value.getCheckOut()); 
                int oldCheckOutYear = value.bookingYear(value.getCheckOut());  
                int newCheckOutDay = extendDays + oldCheckOutDay;  //Calculate how many days to be added into the existing array
                String setNewDate =  String.valueOf(newCheckOutDay +"/" + oldCheckOutMonth + "/" + oldCheckOutYear) ; //Get the new data
                
                value.setCheckOut(setNewDate); // Set the new date into booking list array
                value.setTotalPrice(totalAfterExtend);  // Set the new total pruce into booking list array
                
                customerList(bookingList, roomList);
            }
        }  
        
        if(searchRoomNum == false)
        {
            JOptionPane.showMessageDialog(f,"ERROR! \nEither the room is still available or you've entered wrong room number\nPlease try again."
            ,"ERROR",JOptionPane.ERROR_MESSAGE);
            extendCheckOut(bookingList , roomList);
        }
        
        frontPage(bookingList, roomList);//Return to frontpage/ main menu once the function is done
        
    }
    
    /******************************* SEARCH CUSTOMER *******************************/ //This function is to search customer's name
    public static void searchCustomer( ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList) throws Exception
    {
        String searchCustName = JOptionPane.showInputDialog(f,"Enter customer's name\n" ,"Search for Customer",JOptionPane.QUESTION_MESSAGE); //Ask user to input a name
    
        if (searchCustName == null) { returnToRecord(bookingList,roomList); } //If the input is null then return to record menu
         
        boolean search = false; //Do searching
        
        for (int i = 0; i < bookingList.size()  ; i++) { 
            
            if (bookingList.get(i).getName().equalsIgnoreCase(searchCustName.toUpperCase())) 
            { 
                search = true;  //Set the search to true if the name is found 
                printReceipt(bookingList, roomList, i); //Print the customer's receipt if the name is found 
            } 
        } 
        
        if(search == false) //If searching for room is false then display error message
        {
            JOptionPane.showMessageDialog(f, "Customer's name not found!.\nPlease try again." ,"ERROR",JOptionPane.ERROR_MESSAGE);
            searchCustomer(bookingList,roomList); //Repeat the function
        }
        
        record(bookingList, roomList); ///Return to record menu once the function is done
        
    }
    
    
    /******************************* SEARCH ROOM *******************************/  //This function is to search room's type
    public static void searchRoom(ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList) throws Exception{
       
        String roomNumber = JOptionPane.showInputDialog(f,
        "[1] " + roomList.get(0).getType()  +
         "\n\n[2] " + roomList.get(11).getType()  +
         "\n\n[3] " + roomList.get(21).getType() +
         "\n\n[4] " + roomList.get(31).getType() +
         "\n\n[5] " + roomList.get(41).getType() +
         "\n\nChoose a Room Type [1-5] : ", "ROOM TYPE", JOptionPane.QUESTION_MESSAGE); //Display room menu and get user input
         
         if (roomNumber == null) { returnToRecord(bookingList,roomList); }  //Return to record menu if the input is null
         
         int roomType_inp = Integer.parseInt(roomNumber); 
         //Display available room by calling the availableRoom function from roomList 
         JOptionPane.showMessageDialog (f, roomList.get(0).availableRoom(bookingList,roomList,roomType_inp), "Rooms",  JOptionPane.PLAIN_MESSAGE);  
          
       record(bookingList, roomList);
    }
    
    /******************************* PRINT REPORT *******************************/  //This function is print the report and save it into a text file
    public static void report (ArrayList<CustomerBooking> bookingList ,ArrayList<Room> roomList)throws Exception
    {
        int countD  = 0 , countK  = 0 , countS  = 0 ,countP  = 0  , countT  = 0 ; //Get the count for total customers
        double totalD  = 0 , totalK  = 0 , totalS  = 0 ,totalP  = 0  , totalT  = 0 ; //Get the total sales for each customers
        for (int i = 0; i < bookingList.size()  ; i++) 
        {  
            if (roomList.get(i).getCode() == 'D' && !bookingList.get(i).getRoomNum().equals("") ) 
            {  
                totalD = totalD + bookingList.get(i).getTotalPrice();
                countD ++; 
            } 
            else if (roomList.get(i).getCode() == 'K' && !bookingList.get(i).getRoomNum().equals("") ) 
            {  
                totalK = totalK + bookingList.get(i).getTotalPrice();
                countK ++;
            } 
            else if (roomList.get(i).getCode() == 'S' && !bookingList.get(i).getRoomNum().equals("") ) 
            {  
                totalS = totalS + bookingList.get(i).getTotalPrice();
                countS ++;
            } 
            else if (roomList.get(i).getCode() == 'P' && !bookingList.get(i).getRoomNum().equals("") ) 
            {  
                totalP = totalP + bookingList.get(i).getTotalPrice();
                countP ++;
            } 
            else if (roomList.get(i).getCode() == 'T' && !bookingList.get(i).getRoomNum().equals("") ) 
            {  
                totalT = totalT + bookingList.get(i).getTotalPrice();
                countT ++;
            } 
        }
        
            double grandTotal = totalD + totalK + totalS + totalP + totalT; //Calculate the total sales for each customers
            int totalCustomer = countD + countK + countS + countP + countT; //Calculate the total count for total customers
            
            JOptionPane.showMessageDialog  //Display the reports
            (f, 
            "TOTAL CUSTOMER\n\n" + 
            roomList.get(0).getType() + "\nTotal Customer(s) : " + countD + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalD) + "\n\n" +
            roomList.get(11).getType() + "\nTotal Customer(s) : " + countK + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalK) + "\n\n" +
            roomList.get(21).getType() + "\nTotal Customer(s) : " + countS + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalS) + "\n\n" +
            roomList.get(31).getType() + "\nTotal Customer(s) : " + countP + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalP) + "\n\n" +
            roomList.get(41).getType() + "\nTotal Customer(s) : " + countT + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalT) + "\n\n" +
            "Total Sales = RM " + df2.format(grandTotal)+ "\n\n" + 
            "Company Tax (10%) = RM " + df2.format(grandTotal * 0.10) + "\n\n" +
            "Grand Total Sales = RM " + df2.format(admin.get(0).calculateTotal(grandTotal,0.10,totalCustomer)) + "\n\n" //Call the calculateTotal from admin to calculate total price
            , "Report",  JOptionPane.PLAIN_MESSAGE);
            
            JOptionPane.showMessageDialog(f, "ALL REPORTS HAS BEEN SAVED INTO TEXT FILE [dailyReport.txt]" , "SAVED",JOptionPane.INFORMATION_MESSAGE);
            
            try{
            PrintWriter report = new PrintWriter (new FileWriter("dailyReport.txt")); //Create a daily report file
            report.println("=========================== E-REPORT =========================== ") ;
            report.println("\n\nTOTAL CUSTOMER\n\n");
            report.println(roomList.get(0).getType() + "\nTotal Customer(s) : " + countD + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalD) 
            + "\n\n");
            report.println(roomList.get(11).getType() + "\nTotal Customer(s) : " + countK + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalK) 
            + "\n\n");
            report.println(roomList.get(21).getType() + "\nTotal Customer(s) : " + countS + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalS) 
            + "\n\n");
            report.println(roomList.get(31).getType() + "\nTotal Customer(s) : " + countP + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalP) 
            + "\n\n");
            report.println(roomList.get(41).getType() + "\nTotal Customer(s) : " + countT + " customer(s)" + "\nTotal Sales = RM " + df2.format(totalT) 
            + "\n\n");
            report.println("Total Sales = RM " + df2.format(grandTotal)+ "\n\n");
            report.println("Company Tax (10%) = RM " + df2.format(grandTotal * 0.10) + "\n\n");
            report.println("Grand Total Sales = RM " + df2.format(admin.get(0).calculateTotal(grandTotal,0.10,totalCustomer)));
            report.println("================================================================= ") ;
            
            report.close(); //Close report file
            }  
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            } 
         record(bookingList, roomList); //Return to record options
    }
    
    /******************************* SORT CUSTOMER LIST *******************************/ //This function is to sort all customers' list and display their name, phone and room num
    public static void sortCustList (ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList)throws Exception
    { 
        CustomerBooking temp; //Create a temporary list
        int n = bookingList.size(); //Get the array size
       
        //Sort the customers' lists using name  
        for( int j=0; j<n-1; j++)
        {
          for(int i=0;i<n-j-1;i++)
          {
            CustomerBooking c1 = bookingList.get(i);
            CustomerBooking c2 = bookingList.get(i+1);
                            
            if(c1.getName().compareToIgnoreCase(c2.getName())>0 )
            {
              temp=bookingList.get(i);
              bookingList.set(i,bookingList.get(i+1));
              bookingList.set(i+1,temp);
            }
                        
          }
        }
            
        StringBuilder sb = new StringBuilder();
        int count = 1 ; 
        for (int i = 0; i < bookingList.size()  ; i++) {  
            
            if(!bookingList.get(i).getRoomNum().equals(""))
            {
                sb.append( "[" + count++ + "] " + "   " +  bookingList.get(i).getName() 
                + "          "   + "["  + bookingList.get(i).getPhone() + "] " +  "          Room " + bookingList.get(i).getRoomNum()  
                + "\n-----------------------------------------------------------------------------------------------------------------------------\n") ;  
            }
        } 
        JOptionPane.showMessageDialog(f,"Sorted by Name :\n\n " + sb.toString() , "CUSTOMER LIST",  JOptionPane.PLAIN_MESSAGE); //Display the sorted list
         
        record(bookingList, roomList); //Return to record menu
         
    }
    
    /******************************* TO SAVE ALL CUSTOMER RECEIPTS INTO A TEXT FILE *******************************/  
    public static void saveAllCustReceipts (ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList)throws Exception
    {    
        PrintWriter receipt = new PrintWriter (new FileWriter("customersReceipts.txt")); //Create a new text file
        for (int i = 0; i < bookingList.size()  ; i++) {  
            
            if(!bookingList.get(i).getRoomNum().equals("")) //As long as the room number is not empty in the booking list
            {   //Get calculations
                String checkIn = bookingList.get(i).getCheckIn();
                String checkOut = bookingList.get(i).getCheckOut();
                double oriPrice = roomList.get(i).getPrice();
                double totalDays = (bookingList.get(i).getDifference(checkIn,checkOut) + 1) ;
                double totalBeforeTax = oriPrice * totalDays;
                double totalAfterTax = roomList.get(i).totalAfterTax(oriPrice, totalDays); 
                ///Save the receipts into text files
                receipt.print("***************************************************\n");
                receipt.print("                 BOOKING RECEIPT              \n");
                receipt.print("***************************************************\n");
                receipt.print( "\nTYPE OF ROOM : " + roomList.get(i).getType() );
                receipt.print( bookingList.get(i).toString()   );
                receipt.print( "\nNUMBER OF DAY(S) : " + (bookingList.get(i).getDifference(checkIn,checkOut) + 1) + " days(s)");
                receipt.print( "\nROOM PRICE: RM " + df2.format(roomList.get(i).getPrice()) );
                receipt.print( "\nTAX (6%) : RM " + df2.format(roomList.get(i).taxPrice(totalBeforeTax)) ); 
                receipt.print("\n***************************************************\n");
                receipt.print( "TOTAL BEFORE TAX : RM " + df2.format(totalBeforeTax) );
                receipt.print( "\nTOTAL AFTER TAX : RM " + df2.format(totalAfterTax));
                receipt.print("\n===================================================" );
                receipt.print( "\nGRAND TOTAL: RM " + df2.format(bookingList.get(i).getTotalPrice()));
                receipt.print("\n===================================================\n\n\n\n\n" );
            }
        }
        receipt.close(); //Close text file
        JOptionPane.showMessageDialog(f,"ALL CUSTOMER HAS BEEN SAVED INTO [customersReceipts.txt]" , "SAVED",  JOptionPane.INFORMATION_MESSAGE); //Display the success message
         
        record(bookingList, roomList); //Return to record menu once done
         
    }
    
     /******************************* TO SAVE ALL CUSTOMER RECEIPTS INTO A TEXT FILE *******************************/  
    public static void customerList (ArrayList<CustomerBooking> bookingList,ArrayList<Room> roomList)throws Exception
    {    
        
        StringBuilder sb = new StringBuilder();
        int count = 1 ; 
        for (int i = 0; i < bookingList.size()  ; i++) {  
            
            if(!bookingList.get(i).getRoomNum().equals(""))
            {
                sb.append( "[" + count++ + "] " + "   " +  bookingList.get(i).getName() 
                + "          "   + "["  + bookingList.get(i).getPhone() + "] " +  "          Room " + bookingList.get(i).getRoomNum()  
                + "\n-----------------------------------------------------------------------------------------------------------------------------\n") ;  
            }
        } 
        JOptionPane.showMessageDialog(f,"Customers List:\n\n " + sb.toString() , "CUSTOMER LIST",  JOptionPane.PLAIN_MESSAGE); //Display the sorted list 
    }
        
        
 
} //End of test class
 