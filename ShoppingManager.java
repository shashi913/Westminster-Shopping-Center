import java.util.ArrayList;

interface ShoppingManager {
    void displayMenu();
    void addProduct(/*Product product*/);
    void deleteProduct(/*String productId*/);
    void printProductList();
    void saveToFile(String fileName);
    ArrayList<Product> readFromFile(/*String fileName*/);

}
