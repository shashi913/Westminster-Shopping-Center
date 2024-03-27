package Assignment;
public class Electronics extends Product {
    private String BrandName;
    private int WarrantyPeriod;

    public Electronics(String productId, String productName, int numberOfAvailableItems, double price, String BrandName,
                       int WarrantyPeriod){
        super(productId, productName, numberOfAvailableItems, price);
        this.BrandName = BrandName;
        this.WarrantyPeriod = WarrantyPeriod;
    }

    public String getBrandName(){
        return BrandName;
    }

    public int getWarrantyPeriod(){
        return WarrantyPeriod;
    }

    @Override
    public String getProductType(){
        return "Electronics";
    }

}
