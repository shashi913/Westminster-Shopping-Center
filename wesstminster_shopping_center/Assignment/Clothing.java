package Assignment;
public class Clothing extends Product {
    private int Size;
    private String Color;

    public Clothing(String productId, String productName, int numberOfAvailableItems, double price, int Size,
                    String Color){
        super(productId, productName, numberOfAvailableItems, price);
        this.Size = Size;
        this.Color = Color;
    }

    public int getSize(){
        return Size;
    }

    public String getColor(){
        return Color;
    }

    @Override
    public String getProductType(){
        return "Clothing";
    }

}