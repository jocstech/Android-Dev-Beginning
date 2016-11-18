package lab.csci4100u.jocstech.lab_06_storage;

import java.io.Serializable;

/**
 * Created by jocs on 2016-11-01.
 */

// Data type of Contact:

public class Contact implements Serializable {

    private int contactID;      // Contact ID
    private String firstName;   // First Name
    private String lastName;    // Last Name
    private String phone;       // Phone Number
    //.............

    public Contact(){
        contactID = 0;
        firstName = null;
        lastName = null;
        phone = null;
    }

    public Contact(int id, String firstName , String lastName , String phone){
        this.contactID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public void setId(int id){
        this.contactID = id;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public int getID(){
        return contactID;
    }

    public String getName(){
        return firstName+" "+lastName;
    }

    public String toString(){
        return "ID: "+String.valueOf(this.contactID)+"\nName: "+firstName+" "+lastName+"\nPhone#: "+phone;
    }

    public String stringType(){
        return String.valueOf(this.contactID)+","+firstName+","+lastName+","+phone+"\n";
    }
}
