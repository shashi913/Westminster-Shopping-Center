package Assignment;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ShoppingCenterTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Product ID","Name","Category","Price(£)","Info"};
    private final ArrayList<Product> productArrayList;

    public ShoppingCenterTableModel(ArrayList<Product> productList){
        this.productArrayList = productList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return productArrayList.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object temp;
        if (col == 0) {
            temp = productArrayList.get(row).getProductId();
        }
        else if (col == 1) {
            temp = productArrayList.get(row).getProductName();
        }
        else if (col == 2) {
            if(productArrayList.get(row).getProductType() == "Clothing"){
                temp = "Clothing";
            }
            else temp = "Electronics";
        } else if (col == 3) {
            temp = productArrayList.get(row).getPrice();
        } else if (col == 4) {
            if(productArrayList.get(row).getProductType() == "Clothing"){
                Clothing selectedClothingProduct = (Clothing)productArrayList.get(row);
                temp = selectedClothingProduct.getSize()+", "+selectedClothingProduct.getColor();
            }
            else{
                Electronics selectedElectronicsProduct = (Electronics) productArrayList.get(row);
                temp = selectedElectronicsProduct.getBrandName()+ ", " +
                        selectedElectronicsProduct.getWarrantyPeriod();
            }
        } else {
            temp = null;
        }
        return temp;
    }

    @Override
    public String getColumnName(int col) {return columnNames[col];}       // to show column names in JTable

    public Product getRowObject(int Row){               //get the product id of the selected row
        for(Product product: productArrayList){
            if(this.getValueAt(Row,0).equals(product.getProductId())){
                return product;
            }
        }
        return null;
    }

}
