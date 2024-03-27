package Assignment;

import java.util.ArrayList;

public class User {
    private String UserName;
    private String Password;
    /*private final ArrayList<Purchase> purchaseHistory;*/

    public User(String UserName, String Password){
        this.UserName = UserName;
        this.Password = Password;
        /*this.purchaseHistory = new ArrayList<>();*/
    }

    public String getUserName(){
        return UserName;
    }

    public String getPassword(){
        return Password;
    }

    /*public boolean firstPurchase(){         // Check if the user has made a purchase before by checking the purchaseHistory array
        return this.purchaseHistory.isEmpty();
    }*/
}
