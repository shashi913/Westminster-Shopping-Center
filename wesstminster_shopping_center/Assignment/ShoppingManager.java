package Assignment;

import java.util.ArrayList;

interface ShoppingManager {
    void displayMenu();
    void addProduct();
    void deleteProduct();
    void printProductList();
    void saveToFile(String fileName);
    ArrayList<Product> readFromFile();

}