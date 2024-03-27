package Assignment;

public abstract class Product {
    private String productId;
    private String productName;
    private int numberOfAvailableItems;
    private double price;

    public Product(String productId, String productName, int numberOfAvailableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getProductType();


}
