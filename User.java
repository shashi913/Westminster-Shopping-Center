import java.util.ArrayList;

public class User {
    private String UserName;
    private String Password;
    private final ArrayList<Purchase> purchaseHistory;  //dave

    public User(String UserName, String Password){
        this.UserName = UserName;
        this.Password = Password;
        this.purchaseHistory = new ArrayList<>();    //dave
    }

    public void setUSerPassword(String Password){
        this.Password = Password;
    }

    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    public String getUserName(){
        return UserName;
    }

    public String getPassword(){
        return Password;
    }

    public boolean firstPurchase(){     //dave
        return this.purchaseHistory.isEmpty();
    }
}
