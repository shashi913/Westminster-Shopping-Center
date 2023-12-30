public class Clothing extends Product {
    private String Size;
    private String Color;

    public Clothing(String productId, String productName, int numberOfAvailableItems, int price, String Size,
                   String Color){
        super(productId, productName, numberOfAvailableItems, price);
        this.Size = Size;
        this.Color = Color;
    }

    public String getSize(){
        return Size;
    }

    public String getColor(){
        return Color;
    }

    public void setSize(){
        this.Size = Size;
    }

    public void setColor(){
        this.Color = Color;
    }
}
