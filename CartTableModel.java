import javax.swing.table.AbstractTableModel;

public class CartTableModel extends AbstractTableModel {
    private final ShoppingCart shoppingCart;
    private final String[] columnNames = {"Product","Quantity","Price"};

    public CartTableModel(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int getRowCount() {
        return shoppingCart.getCartList().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product selectedProduct = shoppingCart.getCartList().get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                String details = selectedProduct.getProductId()+", "+selectedProduct.getProductName()+", ";
                if(selectedProduct.getProductType() == "Clothing"){
                    Clothing selectedClothingProduct = (Clothing)selectedProduct;
                    details += selectedClothingProduct.getSize()+", "+selectedClothingProduct.getColor();
                }
                else {
                    Electronics selectedElectronicsProduct = (Electronics) selectedProduct;
                    details += selectedElectronicsProduct.getBrandName()+", "+
                            selectedElectronicsProduct.getWarrantyPeriod();//.replace("m"," months");
                }
                return details;
            }
            case 1 -> {return shoppingCart.getQuantityOnCart().get(selectedProduct);}
            case 2 -> {
                double price = selectedProduct.getPrice()*shoppingCart.getQuantityOnCart().get(selectedProduct);
                price = Math.round(price * 100.0) / 100.0;
                return price + "£";
            }

            default -> {return null;}
        }
    }

    // needed to show column names in JTable
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
