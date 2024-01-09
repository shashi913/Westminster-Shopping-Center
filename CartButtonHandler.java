import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartButtonHandler implements ActionListener {
    ShoppingCartGUI shoppingCartGUI;

    public CartButtonHandler(ShoppingCartGUI shoppingCartGUI){
        this.shoppingCartGUI = shoppingCartGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!shoppingCartGUI.isActive()){
            shoppingCartGUI.setVisible(true);
        }
    }
}