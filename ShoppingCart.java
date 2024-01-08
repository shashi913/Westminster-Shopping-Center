import java.util.*;
public class ShoppingCart {
    //private static String[] Produts = new String[50];
    private ArrayList<Product> selectedProducts;

    public ShoppingCart(){
        this.selectedProducts = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.selectedProducts.add(product);
    }

    public void removeProduct(Product product){
        this.selectedProducts.remove(product);
    }

    public double calculateTotalPrice(){
        double totalPrice = 0;
        for(Product product : this.selectedProducts){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

}
