public class User {
    private String UserName;
    private String Password;

    public User(String UserName, String Password){
        this.UserName = UserName;
        this.Password = Password;
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
}
