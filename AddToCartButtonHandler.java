import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class AddToCartButtonHandler implements ActionListener {
    private final JTable productsTable;
    private final ArrayList<Product> productList;
    private final CartTableModel cartTableModel;
    private final ShoppingCartGUI shoppingCartGUI;
    private final boolean firstPurchase;

    public AddToCartButtonHandler(JTable productsTable,ArrayList<Product> productList,
                               CartTableModel cartTableModel,ShoppingCartGUI shoppingCartGUI,boolean firstPurchase){
        this.productsTable = productsTable;
        this.productList = productList;
        this.cartTableModel = cartTableModel;
        this.shoppingCartGUI = shoppingCartGUI;
        this.firstPurchase = firstPurchase;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int selectedRow = productsTable.getSelectedRow();
        if(selectedRow != -1) {
            String selectedProductId = productsTable.getValueAt(selectedRow, 0).toString();
            Map<Product, Integer> quantityOnCart = cartTableModel.getShoppingCart().getQuantityOnCart();

            // get the selected product and check its availability
            for (Product checker : productList) {
                if (checker.getProductId().equals(selectedProductId) && checker.getNumberOfAvailableItems()>0) {
                    int availableItems;
                    if(quantityOnCart.containsKey(checker)){
                        availableItems = checker.getNumberOfAvailableItems() - quantityOnCart.get(checker);
                    }
                    else availableItems = checker.getNumberOfAvailableItems();

                    if (availableItems > 0) {
                        cartTableModel.getShoppingCart().addProduct(checker);
                        shoppingCartGUI.updatePrices(cartTableModel.getShoppingCart().calculateTotalPrice(),cartTableModel.getShoppingCart(),firstPurchase);
                        cartTableModel.fireTableDataChanged();
                    }

                    break;
                }
            }

        }
    }
}
