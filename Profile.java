import java.util.Date;         

public abstract class Profile
{
    private int id; 
    private String name;
    private String address;
    private String phone; 
    
    //DEFAULT CONSTRUCTOR
    public Profile()
    {
        id = 0;
        name = null;
        address = null;
        phone = null; 
    }
    
    
    //NORMAL CONSTRUCTOR
    public Profile(int id, String name, String phone, String address) { 
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone; 
    }
    
    //ACCESSOR METHOD
    public int getID() {
        return id;
    } 
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhone() {
        return phone;
    } 
     
    //MUTATOR METHOD
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
     
    
    //DISPLAY METHOD
   public String toString()
   {
       return "\nNAME\t: " + name + "\nPHONE NUMBER\t: " + phone + "\nADDRESS\t: " + address;
   }
    
   //ABSTRACT METHOD TO CALCULATE TOTAL OF EACH SUBCLASS
   public abstract double calculateTotal(double a ,double b,int c);
}
    