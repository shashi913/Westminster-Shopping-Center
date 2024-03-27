package Assignment;
import java.util.*;
import java.io.*;
public class WestminsterShoppingManager implements ShoppingManager{
    static Scanner input = new Scanner(System.in);
    private ArrayList<Product> productList = new ArrayList<>(50);

    final ArrayList<User> users = new ArrayList<>();           //not clear
/** jjj **/
    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.readFromFile();

        while(true) {
            try {
                westminsterShoppingManager.displayMenu();
                int option = input.nextInt();
                input.nextLine();
                switch (option) {
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

                    case 5:
                        westminsterShoppingManager.openGUI();
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.next();
            }
        }
    }

    @Override
    public void displayMenu(){
        System.out.println("Westminster Shopping Center management system");
        System.out.println("Please select an option from the menu below");
        System.out.println("1. Add a new item");
        System.out.println("2. Delete an existing item");
        System.out.println("3. Print the list of items");
        System.out.println("4. Save in a file");
        System.out.println("5. Open GUI");
        System.out.println("6. Exit");
        System.out.print("Option number: ");
    }

    public static String productIdValidation() {
        while (true) {
            System.out.print("Product ID: ");
            String productId = input.nextLine();

            if (productId.length() == 5 && productId.charAt(0) >= 'A' && productId.charAt(0) <= 'Z' && Character.isUpperCase(productId.charAt(0))) {
                for (int i = 1; i < 6; i++) {
                    if (Character.isDigit(productId.charAt(i))) {
                        break;
                    }
                }

            }else {
                System.out.println("\nId format should be \"A001\"\n");

            }
            return productId;
        }
    }

    @Override
    public void addProduct(){

        int productType;
        while(true) {
            if (productList.size() >= 50) {
                System.out.println("Assignment.Product list is full");
                return;
            }

            while (true) {
                try {
                    System.out.println("Please select a product type");
                    System.out.println("1. Electronics");
                    System.out.println("2. Clothes");
                    System.out.println("3. Exit");
                    System.out.print("Product number: ");
                    productType = input.nextInt();
                    input.nextLine();

                    if(productType== 3) {
                        System.out.println("\nExiting add product...\n");
                        return;
                    }

                    String productId= productIdValidation();

                    System.out.print("Product Name: ");
                    String productName = input.nextLine();
                    int numberOfAvailableItems;
                    while(true) {
                        System.out.print("Number of available items: ");
                        numberOfAvailableItems = input.nextInt();
                        if(numberOfAvailableItems <= 0){
                            System.out.println("\nNumber of available items can't be less than zero\n");
                        } else{
                            break;
                        }
                    }
                    double price;
                    while(true) {
                        System.out.print("Price:");
                        price = input.nextInt();
                        if(price <= 0){
                            System.out.println("\nPrice can't be less than zero\n");
                        } else{
                            input.nextLine();
                            break;
                        }
                    }


                    switch (productType) {
                        case 1:
                            System.out.print("Brand Name : ");
                            String brandName = input.nextLine();

                            int warrantyPeriod;
                            while(true) {
                                System.out.print("Warranty Period (months): ");
                                warrantyPeriod = input.nextInt();
                                if(warrantyPeriod <= 0 || warrantyPeriod >= 12){
                                    System.out.println("\nWarranty period should be between 1 to 12 months!\n");
                                } else {
                                    break;
                                }
                            }
                            productList.add(new Electronics(productId, productName, numberOfAvailableItems, price, brandName, warrantyPeriod));
                            break;

                        case 2:
                            int size;
                            while(true) {
                                System.out.print("Size (cm): ");
                                size = input.nextInt();
                                if(size < 10 || size > 40){
                                    System.out.println("\nSize should be between 10cm to 40cm\n");
                                } else{
                                    input.nextLine();
                                    break;
                                }
                            }
                            String color;
                            while(true) {
                                String[] colors = {"red", "black", "white", "yellow", "green", "orange"};
                                System.out.print("Color : ");
                                color = input.nextLine();
                                /*if(color.toUpperCase().equals(colors))*/
                                    if(Arrays.asList(colors).contains(color)){
                                    break;
                                } else{
                                    System.out.println("\nColors should be "+ Arrays.toString(colors)+ ".\n");
                                }
                            }
                            productList.add(new Clothing(productId, productName, numberOfAvailableItems, price, size, color));
                            System.out.println("\nProduct added successfully\n");
                            break;

                        default:
                            System.out.println("\nInvalid product type\n");
                            break;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input\n");
                    input.next();
                }
            }
        }
    }

    @Override
    public void deleteProduct() {
        String deleteProductId= productIdValidation();
        boolean productDeleted = false;
        for (Product product : productList) {
            if (product.getProductId().equals(deleteProductId)) {

                System.out.println("Assignment.Product Details");
                System.out.println("Assignment.Product ID   : " + product.getProductId());
                System.out.println("Assignment.Product type : " + product.getProductType());
                System.out.println("Assignment.Product Name : " + product.getProductName());
                System.out.println("Available amount : " + product.getNumberOfAvailableItems());
                System.out.println("Price : " + product.getPrice()+ " £");
                if (product.getProductType().equals("Electronics")) {
                    Electronics electronics = (Electronics) product;
                    System.out.println("Brand Name : " + electronics.getBrandName());
                    System.out.println("Warranty Period : " + electronics.getWarrantyPeriod() + " months");
                } else {
                    Clothing clothing = (Clothing) product;
                    System.out.println("Size : " + clothing.getSize());
                    System.out.println("Color : " + clothing.getColor());
                } while (true) {
                    System.out.print("Are you sure you want to delete this product? (Y/N)");
                    String option = input.nextLine();
                    if (option.equals("Y") || option.equals("y")) {
                        productList.remove(product);
                        productDeleted = true;
                        System.out.println("\nProduct deleted successfully\n");
                        break;
                    } else if (option.equals("N") || option.equals("n")) {
                        System.out.println("\nProduct not deleted!\n");
                        break;
                    } else {
                        System.out.println("\nInvalid option\n");
                    }
                }
            }
        }if(!productDeleted){
            System.out.println("\nProduct not found\n");
        }
    }

    @Override
    public void printProductList() {
        productList.sort(Comparator.comparing(Product::getProductId));

        for (Product product : productList) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Number of available items: " + product.getNumberOfAvailableItems());
            System.out.println("Price: " + product.getPrice()+ " £");
            System.out.println("Product Type: " + product.getProductType());
            if (product.getProductType().equals("Electronics")) {
                Electronics electronics = (Electronics) product;
                System.out.println("Brand Name : " + electronics.getBrandName());
                System.out.println("Warranty Period : " + electronics.getWarrantyPeriod() + " months");
            } else {
                Clothing clothing = (Clothing) product;
                System.out.println("Size : " + clothing.getSize());
                System.out.println("Color : " + clothing.getColor());
            }
            System.out.println();
        }
    }

    @Override
    public void saveToFile(String fileName){
        try{

            FileWriter fileWriter = new FileWriter("savedProductList.txt");
            for(Product product : productList){
                fileWriter.write(product.getProductType() + "," + product.getProductId() + "," + product.getProductName() + "," + product.getNumberOfAvailableItems() + "," + product.getPrice() + ",");

                if(product.getProductType().equals("Electronics")){
                    Electronics electronics = (Electronics) product;
                    fileWriter.write(electronics.getBrandName() + "," + electronics.getWarrantyPeriod() + "\n");
                }else{
                    Clothing clothing = (Clothing) product;
                    fileWriter.write(clothing.getSize() + "," + clothing.getColor() + "\n");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();

            System.out.println("Product list saved to " + fileName + " successfully");
        }catch(IOException e){
            System.out.println("Error saving product list");
        }
    }

    @Override
    public ArrayList<Product> readFromFile(){
        File file = new File("savedProductList.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;

            while((str = reader.readLine()) != null) {
                String[] words = str.split(",");

                if (words[0].equals("Electronics")) {
                    productList.add(new Electronics(words[1], words[2], Integer.parseInt(words[3]),
                            Double.parseDouble(words[4]), words[5], Integer.parseInt(words[6])));
                } else if(words[0].equals("Clothing")){
                    productList.add(new Clothing(words[1], words[2], Integer.parseInt(words[3]),
                            Double.parseDouble(words[4]), Integer.parseInt(words[5]), words[6]));
                }
            }
            System.out.println("Load Successfully");
        }catch(IOException e){
            System.out.println("Error reading product list");
        }
        return productList;
    }

    public void openGUI(){
        LoginPageGUI loginGUI = new LoginPageGUI(users,productList);
        loginGUI.setTitle("Login or Register");
        loginGUI.setSize(700, 650);
        loginGUI.setResizable(false);
        loginGUI.setVisible(true);

    }

}
