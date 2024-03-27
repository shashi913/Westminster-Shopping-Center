package Assignment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginPageGUI extends JFrame implements ActionListener {
    final private JTextField usernameField;
    final private JPasswordField passwordField;
    final private JLabel messageLabel, userNameLabel, passwordLabel;
    final private JPanel fullPanel, labelPanel, buttonPanel, messagePanel;
    final private ArrayList<User> users;                            //to get the user list
    final private ArrayList<Product> savedList;             //to get saved product list
    private JButton registerButton;                         //register button
    private JButton loginButton;                            //login button

    public LoginPageGUI(ArrayList<User> users,ArrayList<Product> productsList) {
        this.users = users;                                                         //get the user list
        this.savedList = productsList;                                           //get the product list

        Container container = getContentPane();

        fullPanel = new JPanel(new GridLayout(3,1,25,0));        //set layout of full panel
        container.add(fullPanel);

        /*topPanel.setBorder(new EmptyBorder(110,0,40,95));*/
        labelPanel = new JPanel(new GridLayout(2,2,25,20));        //set layout of top panel (container of the labels)
        labelPanel.setBorder(new EmptyBorder(50,30,30,50));

        buttonPanel = new JPanel(new GridLayout(1,2,20,20));      //set layout of button panel  (container buttons)
        buttonPanel.setBorder(new EmptyBorder(70,60,70,60));

        messagePanel = new JPanel(new GridLayout(1,1,20,20));      //set layout of message panel    (container of the message label)
        messagePanel.setBorder(new EmptyBorder(30,30,30,30));

        usernameField = new JTextField();                                                //set text field for username
        passwordField = new JPasswordField();                                           //set text field for password

        loginButton = new JButton("Login");                                         //set button for login
        registerButton = new JButton("Register");                                   //set button for register

        messageLabel = new JLabel("", SwingConstants.CENTER);                   //set label value for messageLabel text field
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));

        userNameLabel = new JLabel("Username :", SwingConstants.CENTER);        //set label value for usernameLabel text field
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel = new JLabel("Password :", SwingConstants.CENTER);        //set label value for passwordLabel text field
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));

        buttonPanel.add(loginButton);                                       //add login button to button panel
        buttonPanel.add(registerButton);                                    //add register button to button panel

        labelPanel.add(userNameLabel);                                           //add usernameLabel to top panel
        labelPanel.add(usernameField);                                           //add usernameField to top panel
        labelPanel.add(passwordLabel);                                          //add passwordLabel to top panel
        labelPanel.add(passwordField);                                          //add passwordField to top panel

        messagePanel.add(messageLabel);                             //add messageLabel to message panel

        fullPanel.add(labelPanel);                                        //add label panel to full panel
        fullPanel.add(buttonPanel);                                         //add button panel to full panel
        fullPanel.add(messagePanel);                                    //add message panel to full panel

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e){
        String username = usernameField.getText();                          //get the username from username text field
        String password = String.valueOf(passwordField.getPassword());      //get the password from password text field and convert it from char to string

        if(e.getSource() == loginButton){
            if(username.isEmpty() || password.isEmpty()){
                usernameField.setText("");
                passwordField.setText("");
                messageLabel.setText("please enter username and password");
                this.repaint();                                             //repaint the frame


            }else if(!username.isEmpty() && !password.isEmpty()){
                for(User user : users){
                    if(user.getUserName().equals(username) && user.getPassword().equals(password)){

                        this.dispose();
                        final ArrayList<Product> productsOnCart = new ArrayList<>(50); //to use as the Assignment.ShoppingCart object and get the product list from the cart from main class
                        final Map<Product, Integer> quantityOnCart = new HashMap<>(50); //to use as the Assignment.ShoppingCart object and get the quantity list from the cart from main class

                        final ShoppingCart shoppingCart = new ShoppingCart(productsOnCart,
                                quantityOnCart); //to use as the Assignment.ShoppingCart object and get the product list and quantity list from the cart from main class

                        final ShoppingCartTableModel.ShoppingCentrePageGUI shoppingPageGUI = new
                                ShoppingCartTableModel.ShoppingCentrePageGUI(savedList,
                                shoppingCart, quantityOnCart);

                        shoppingPageGUI.setTitle("Westminster Shopping Center");
                        shoppingPageGUI.setSize(1000, 800);
                        shoppingPageGUI.setVisible(true);
                        return;
                    }
                }
            } else{
                messageLabel.setText("Username or password is incorrect");
                usernameField.setText("");
                passwordField.setText("");
                this.repaint();
            }

        } else if(e.getSource() == registerButton) {
            boolean newUser = true;
            for (User user : users) {
                if (user.getUserName().equals(username)) {
                    newUser = false;
                    return;
                }
            }
            if (!username.isEmpty() && !password.isEmpty() && newUser) {
                users.add(new User(username, password));
                this.repaint();
                messageLabel.setText("Registered");
                users.add(new User(username, password));

            } else{
                this.repaint();
                messageLabel.setText("This username is already given");
                usernameField.setText("");
                passwordField.setText("");

            }
        }
    }
}
