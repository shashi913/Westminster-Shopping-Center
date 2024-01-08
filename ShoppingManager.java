interface ShoppingManager {
    public void displayMenu();
    public void addProduct(/*Product product*/);
    public void deleteProduct(/*String productId*/);
    public void printProductList();
    public void saveToFile(String fileName);
    public void readFromFile(String fileName);

}
