import java.util.*;
import java.io.*;
public class WestminsterShoppingManager implements ShoppingManager{
    static Scanner input = new Scanner(System.in);
    private ArrayList<Product> productList;

    //static Scanner input = new Scanner(System.in);

    /*public WestminsterShoppingManager(){
        this.productList = new ArrayList<>();
    }*/

    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.readFromFile("savedProductList.txt");

        while(true){
            westminsterShoppingManager.displayMenu();
            int option = input.nextInt();
            switch(option){
                case 1:
                    westminsterShoppingManager.addProduct();
                    break;

                case 2:
                    westminsterShoppingManager.deleteProduct();
                    break;

                case 3:
                    westminsterShoppingManager.printProductList();
                    break;

                case 4:
                    westminsterShoppingManager.saveToFile("savedProductList.txt");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

    }
    public void displayMenu(){
        System.out.println("Westminster Shopping Center management system");
        System.out.println("Please select an option from the menu below");
        System.out.println("1. Add a new item");
        System.out.println("2. Delete an existing item");
        System.out.println("3. Print the list of items");
        System.out.println("4. Save in a file");
        System.out.print("Option number: ");
    }

    public void addProduct(/*Product product*/){
        /*if(productList.size() < 50){
            productList.add(product);
            System.out.println("Product added successfully");
        }else{
            System.out.println("Product list is full");
        }*/
        if(productList.size() >= 50){
            System.out.println("Product list is full");
            return;
        }

        while(true) {
            try {
                System.out.println("Please select a product type");
                System.out.println("1. Electronics");
                System.out.println("2. Clothes");
                System.out.print("Product number: ");
                int productType = input.nextInt();

                System.out.print("Product ID: ");
                String productId = input.nextLine();
                System.out.print("Product Name: ");
                String productName = input.nextLine();
                System.out.print("Number of available items: ");
                int numberOfAvailableItems = input.nextInt();
                System.out.print("Price:");
                double price = input.nextDouble();

                switch (productType) {
                    case 1:
                        System.out.print("Brand Name: ");
                        String brandName = input.nextLine();
                        System.out.print("Warranty Period: ");
                        int warrantyPeriod = input.nextInt();
                        productList.add( new Electronics(productId, productName, numberOfAvailableItems, price, brandName, warrantyPeriod));
                        break;

                    case 2:
                        System.out.print("Size: ");
                        String size = input.nextLine();
                        System.out.print("Color: ");
                        String color = input.nextLine();
                        productList.add(new Clothing(productId, productName, numberOfAvailableItems, price, size, color));
                        break;

                    default:
                        System.out.println("Invalid product type");
                        break;
                }
                break;
            } catch(InputMismatchException e){
                System.out.println("Invalid input");
                input.next();
            }
        }
    }

    public void deleteProduct(/*String productId*/) {
        System.out.print("Insert product ID: ");
        String deleteProductId = input.nextLine();
        for (Product product : productList) {
            if (product.getProductId().equals(deleteProductId)) {
                productList.remove(product);
                System.out.println("Product Details");
                System.out.println("Product type : " + product.getProductType());
                System.out.println("Product ID   : " + product.getProductType());
                System.out.println("Product Name : " + product.getProductName());
                System.out.println("Available amount : " + product.getNumberOfAvailableItems());
                System.out.println("Price : " + product.getPrice());
                if (product.getProductType().equals("Electronics")) {
                    Electronics electronics = (Electronics) product;
                    System.out.println("Brand Name : " + electronics.getBrandName());
                    System.out.println("Warranty Period : " + electronics.getWarrantyPeriod());
                } else{
                    Clothing clothing = (Clothing) product;
                    System.out.println("Size : " + clothing.getSize());
                    System.out.println("Color : " + clothing.getColor());
                }
                    break;
            }System.out.println("Product deleted successfully");
        }
    }

    public void printProductList(){
        /*bubble_sort();*/
        productList.sort(Comparator.comparing(Product::getProductId));
        for(Product product : productList){
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Number of available items: " + product.getNumberOfAvailableItems());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Product Type: " + product.getProductType());
            System.out.println();
        }
    }

    /*public void bubble_sort() {
        int end = productList.size() - 2;       //take the index of ArrayList ( last index - 1)
        Product swap_index_data;             //Keep the data temporary
        boolean continue_loop = true;       //for looping
        int total_price= 0;                 //take the total price of the tickets

        while (continue_loop) {
            continue_loop = false;          // if there is no swapping end the loop

            for (int i = 0; i <= end; i++) {        //loop until goes to the last index-1
                if (productList.get(i).getProductId() > productList.get(i + 1).price) {  //check whether index i price > index (i+1) price
                    swap_index_data = tickets.get(i);                   //if it larger temporary add data in to swap_index_data variable
                    tickets.set(i, tickets.get(i + 1));                 // if it larger swap index i+1 details to index i
                    tickets.set((i + 1), swap_index_data);        //  add the information inside the temporary variable in to the index i
                    continue_loop = true;       // loop again because there is a swap happened
                }
            }
            end--;    //after one time loop works and going to the next tern decrease the last index otherwise it gives an index out of bound error
        }
        for(int i= 0; i<tickets.size(); i++){
            tickets.get(i).print();         // call the print method inside the Ticket class
            total_price += tickets.get(i).price;
        }
        System.out.println("\nTotal price of the tickets: "+ total_price);
    }*/

    public void saveToFile(String fileName){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(productList);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Product list saved to " + fileName + " successfully");
        }catch(IOException e){
            System.out.println("Error saving product list");
        }
    }

    public void readFromFile(String fileName){
        try{
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            productList = (ArrayList<Product>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Product list read from " + fileName + " successfully");
        }catch(IOException e){
            System.out.println("Error reading product list");
        }catch(ClassNotFoundException e){
            System.out.println("Error reading product list");
        }
    }

}
