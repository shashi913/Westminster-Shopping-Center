import java.util.*;
public class ShoppingCart {
    //private static String[] Produts = new String[50];
    private ArrayList<Product> selectedProducts;
    private final Map<Product, Integer> quentityOnCart;

    public ArrayList<Product> getCartList() {
        return selectedProducts;
    }
    public Map<Product, Integer> getQuantityOnCart() {
        return quentityOnCart;
    }


    public ShoppingCart(ArrayList<Product> productsOnCart,Map<Product, Integer> quantityOnCart) {

        //this.selectedProducts = new ArrayList<>();
        this.selectedProducts = productsOnCart;
        this.quentityOnCart = quantityOnCart;
    }

    public void addProduct(Product product){

        if(selectedProducts.contains(product)) {
            quentityOnCart.put(product, quentityOnCart.get(product) + 1);
        }
        else{
            selectedProducts.add(product);
            quentityOnCart.put(product,1);
        }
    }

    public void removeProduct(Product product){

        this.selectedProducts.remove(product);
        this.quentityOnCart.remove(product);
    }

    public double calculateTotalPrice(){
        double totalPrice = 0;
        for(Product product : this.selectedProducts){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public double getThreeSameItemsDiscount(){
        double discount = 0;
        for(Product product: selectedProducts){
            if(getQuantityOnCart().containsKey(product) && getQuantityOnCart().get(product) >= 3)
                discount += (product.getPrice() * getQuantityOnCart().get(product)) * 0.2;
        }
        return Math.round(discount * 100.0) / 100.0;
    }

}
