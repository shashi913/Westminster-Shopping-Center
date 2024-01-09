import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginPageGUI extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private  final JLabel message;
    private final ArrayList<User> users;                            //to get the user list from main class
    private final ArrayList<Product> productsList;                   //to get the product list from main class
    private final JPanel topPanel,bottomPanel;
    private final JButton registerButton;
    private final JButton loginButton;

    public LoginPageGUI(ArrayList<User> users,ArrayList<Product> productsList) {
        this.users = users;                                                         //get the user list from main class
        this.productsList = productsList;                                           //get the product list from main class

        Font headerFont = new Font("Arial", Font.BOLD, 20);              //set font size in every tex on login page

        this.setLayout(new BorderLayout());                                         //set layout of login page

        topPanel = new JPanel(new GridLayout(2,2,25,20));        //set layout of top panel
        topPanel.setBorder(new EmptyBorder(110,0,40,95));

        bottomPanel = new JPanel(new BorderLayout());                               //set layout of bottom panel
        bottomPanel.setBorder(new EmptyBorder(0,0,150,0));

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,20,20));      //set layout of button panel
        buttonPanel.setBorder(new EmptyBorder(0,150,0,150));


        JLabel username = new JLabel("Username :");
        username.setFont(headerFont);

        JLabel password = new JLabel("Password :");
        password.setFont(headerFont);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        usernameField = new JTextField();
        usernameField.setFont(headerFont);
        passwordField = new JPasswordField();
        passwordField.setFont(headerFont);

        topPanel.add(username);
        topPanel.add(usernameField);
        topPanel.add(password);
        topPanel.add(passwordField);

        message = new JLabel("    ");
        message.setBorder(new EmptyBorder(20,0,0,0));
        message.setFont(headerFont);

        //bottomPanel.add(bottomPanel,BorderLayout.CENTER);
        bottomPanel.add(buttonPanel,BorderLayout.CENTER);
        bottomPanel.add(message,BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Add components to the JFrame
        add(topPanel,BorderLayout.NORTH);
        add(bottomPanel,BorderLayout.CENTER);

    }


    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); // Convert char[] to String for password
        boolean firstPurchase;

        if (e.getSource() == loginButton){
            for(User user: users){
                if(/*username== user.getUserName()*/ user.getUserName().equals(username) && /*password == user.getPassword()*/user.getPassword().equals(password)){

                    firstPurchase = user.firstPurchase();

                    this.dispose();                 //close login page
                    final ArrayList<Product> productsOnCart = new ArrayList<>(50);                                 //to use as the ShoppingCart object and get the product list from the cart from main class
                    final Map<Product, Integer> quantityOnCart = new HashMap<>();                               //not clear   //to get the product list from main class
                    ShoppingCart shoppingCart = new ShoppingCart(productsOnCart,quantityOnCart);                //not clear
                    ShoppingCentreGUI frame = new ShoppingCentreGUI(productsList,shoppingCart,firstPurchase);   //not clear
                    frame.setTitle("Westminster Shopping Center");
                    frame.setSize(700,650);
                    frame.setMinimumSize(new Dimension(600, 400));
                    frame.setVisible(true);
                    return;
                }
                else if(user.getUserName().equals(username)){
                    message.setText("Password is incorrect");
                    passwordField.setText("");
                    passwordField.setBackground(new Color(255,100,100));
                    this.repaint();
                    return;
                }
            }
            usernameField.setText("");
            passwordField.setText("");
            usernameField.setBackground(new Color(255, 100, 100));
            passwordField.setBackground(new Color(255,100,100));
            message.setText("Incorrect username and password");

            this.repaint();

        } else if (e.getActionCommand().equals("Register")) {
            boolean newUser = true;
            for(User user: users){
                if(user.getUserName().equals(username)){
                    newUser = false;
                    break;
                }
            }

            if(!username.isEmpty() && !password.isEmpty() && newUser){
                users.add(new User(username,password));
                usernameField.setBackground(new Color(100, 200, 200));
                passwordField.setBackground(new Color(100,200,200 ));
                message.setText("Registered");
            } else if (!newUser) {
                usernameField.setBackground(new Color(255, 100, 100));
                message.setText("This username is already given");
            }
            else if(username.isEmpty() && password.isEmpty()){
                message.setText("Please enter the username and password");
                usernameField.setBackground(new Color(255, 100, 100));
                passwordField.setBackground(new Color(255,100,100));

            } else if (password.isEmpty()) {
                message.setText("Please enter the password");
                passwordField.setBackground(new Color(255, 100, 100));

            }
            else {
                message.setText("Please enter the username");
                usernameField.setBackground(new Color(255, 100, 100));
            }
        }
    }
}

