package Assignment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ShoppingCartTableModel extends AbstractTableModel {
    private final ShoppingCart shoppingCart;
    private final String[] columnNames = {"Assignment.Product","Quantity","Price"};

    public ShoppingCartTableModel(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int getRowCount() {
        return shoppingCart.getCartList().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Product userSelectedProduct = shoppingCart.getCartList().get(row);
        Object temp;
        if(col == 0){
            String details = userSelectedProduct.getProductId() + ", " + userSelectedProduct.getProductName() + ", ";
            if (userSelectedProduct.getProductType() == "Clothing") {
                Clothing selectedClothingProduct = (Clothing) userSelectedProduct;
                details += selectedClothingProduct.getSize() + ", " + selectedClothingProduct.getColor();
            } else {
                Electronics selectedElectronicsProduct = (Electronics) userSelectedProduct;
                details += selectedElectronicsProduct.getBrandName() + ", " +
                        selectedElectronicsProduct.getWarrantyPeriod();//.replace("m"," months");
            }
            temp= details;
        } else if(col == 1) {
            return shoppingCart.getQuantityOnCart().get(userSelectedProduct);
        } else if(col == 2) {
            double price = userSelectedProduct.getPrice() * shoppingCart.getQuantityOnCart().
                    get(userSelectedProduct);
            price = Math.round(price * 100.0) / 100.0;
            return price + "£";
        }else{
            temp= null;
        }
        return temp;
    }

    // needed to show column names in JTable
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public static class ShoppingCentrePageGUI extends JFrame {

        JPanel selectProductPanel,selectProductAndCartBtn,topPanel,bottomPanel, cartButtonPanel, tablePanel, productDetails;
        JLabel selectProductCategory,productDetailsHeader;
        JButton cartBtn,addToCartBtn;
        JLabel id,category,name,uniq1,uniq2,availability;
        final private Map<Product, Integer> quantityOnCart;
        ArrayList<Product> productList;
        JTable productsTable;
        final private String[] categories = {"All","Electronics","Clothing"};
        final private JComboBox<String> selectProductCategoryComboBox = new JComboBox<>(categories);

        public ShoppingCentrePageGUI(ArrayList<Product> productList, ShoppingCart shoppingCart,
                                     Map<Product, Integer> quantityOnCart){
            this.quantityOnCart = quantityOnCart;
            this.productList = productList;

            selectProductPanel = new JPanel();
            selectProductPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,30));

            selectProductCategory = new JLabel("  Select Product Category");
            selectProductCategoryComboBox.setPreferredSize(new Dimension(120,20));

            selectProductPanel.add(selectProductCategory);
            selectProductPanel.add(selectProductCategoryComboBox);

            selectProductAndCartBtn = new JPanel();

            cartButtonPanel = new JPanel();
            cartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            cartBtn = new JButton("Shopping Cart");
            cartBtn.setPreferredSize(new Dimension(120,30));
            cartButtonPanel.add(cartBtn);

            selectProductAndCartBtn.add(selectProductPanel,BorderLayout.CENTER);
            selectProductAndCartBtn.add(cartButtonPanel,BorderLayout.EAST);

            topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());

            ShoppingCenterTableModel tableModel = new ShoppingCenterTableModel(this.productList);       //shoppingCenterPage Table model object (defining structure and data of the table
            productsTable = new JTable(tableModel);                                                     //shoppingCenterPage Table model JTable


            productsTable.setRowHeight(30);

            tablePanel = new JPanel();
            tablePanel.setBorder(new EmptyBorder(40,0,10,0));
            tablePanel.setLayout(new BorderLayout());
            tablePanel.add(new JScrollPane(productsTable),BorderLayout.CENTER);

            topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            topPanel.add(selectProductAndCartBtn,BorderLayout.NORTH);
            topPanel.add(tablePanel,BorderLayout.CENTER);

            bottomPanel = new JPanel();
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            productDetails = new JPanel();
            productDetails.setLayout(new GridLayout(7,1,10,10));
            productDetails.setBorder(new EmptyBorder(0, 30, 30, 10));

            productDetailsHeader = new JLabel("Selected Product - Details");
            id = new JLabel("");
            category = new JLabel("");
            name = new JLabel("");
            uniq1 = new JLabel("");
            uniq2 = new JLabel("");
            availability = new JLabel("");



            JPanel addToCartPanel = new JPanel();
            addToCartPanel.setLayout(new FlowLayout());
            addToCartBtn = new JButton("Add to Shopping Cart");
            addToCartPanel.add(addToCartBtn);

            bottomPanel.add(productDetails,BorderLayout.CENTER);
            bottomPanel.add(addToCartPanel,BorderLayout.SOUTH);


            productDetails.add(productDetailsHeader);
            productDetails.add(id);
            productDetails.add(category);
            productDetails.add(name);
            productDetails.add(uniq1);
            productDetails.add(uniq2);
            productDetails.add(availability);

            this.add(topPanel,BorderLayout.CENTER);
            this.add(bottomPanel,BorderLayout.SOUTH);

            productsTable.getSelectionModel().addListSelectionListener(new ProductDetailsWritter(productsTable,
                    productList,id,category,name,uniq1,uniq2,availability));                                    //add listener to the table to write product details to the labels

            //sorter**
            TableRowSorter<ShoppingCenterTableModel> tableSorter = new TableRowSorter<>(tableModel);            //add sorter function to the table using TableSorter class
            productsTable.setRowSorter(tableSorter);

            selectProductCategoryComboBox.addActionListener(new ProductCategoryComboBox                         //combo box action listener (Filter data accoding to user need
                    (selectProductCategoryComboBox,productsTable,tableSorter));
            //**//


            //shopping table GUI
            ShoppingCartTableModel shoppingCartTableModel = new ShoppingCartTableModel(shoppingCart);                           //create cart table model object
            shoppingCart = new ShoppingCart(productList, quantityOnCart);
            shoppingCart.ShoppingCartGUI(shoppingCartTableModel);
            shoppingCart.setTitle("Shopping Cart");
            shoppingCart.setSize(700, 450);

            addToCartBtn.addActionListener(new AddToCartButtonFunction(productsTable,productList,
                    shoppingCartTableModel, shoppingCart/*,firstPurchase*/));                        //add to cart button action listener

            cartBtn.addActionListener(new CartButtonFunction(shoppingCart));              //shopping cart button action listener
        }

        //****************************************************************************************************************
        //cart button action listener class
        public class CartButtonFunction implements ActionListener {
            ShoppingCart shoppingCart;

            public CartButtonFunction(ShoppingCart shoppingCart){
                this.shoppingCart = shoppingCart;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shoppingCart.isActive()){
                    shoppingCart.setVisible(true);
                } else {
                    shoppingCart.setVisible(false);
                }
            }
        }

        //*********************************************************************************************************

        //add listener to the table to write product details to the labels
        public class ProductDetailsWritter implements ListSelectionListener {
            private final JTable productsTable;
            private final ArrayList<Product> productList;
            private final JLabel id,category,name,additional1,additional2,availability;


            public ProductDetailsWritter(JTable productsTable,ArrayList<Product> productList,JLabel id,JLabel category,
                                         JLabel name,JLabel additional1,JLabel additional2,JLabel availability){
                this.productsTable = productsTable;
                this.productList = productList;
                this.id = id;
                this.category = category;
                this.name = name;
                this.additional1 = additional1;
                this.additional2 = additional2;
                this.availability = availability;

                for (int i = 0; i < productsTable.getColumnCount(); i++) {
                    productsTable.getColumnModel().getColumn(i).setCellRenderer(new AvailibilityCellRender());                  //make row color red if availability is less than 3
                }
            }

            @Override
            public void valueChanged(ListSelectionEvent event) {                    //check if the selection process has finished
                if (!event.getValueIsAdjusting()) { // Check if the selection process has finished
                    int selectedRow = productsTable.getSelectedRow();
                    if (selectedRow != -1) { // Check if any row is selected
                        String selectedProductId = productsTable.getValueAt(selectedRow,0).toString();
                        Product selectedProduct = null;
                        boolean selectedItemIsNull = true;
                        for(Product checker : productList){
                            if (checker.getProductId().equals(selectedProductId)) {
                                selectedProduct = checker;
                                selectedItemIsNull = false;
                                break;
                            }
                        }
                        if(!selectedItemIsNull) {
                            id.setText("Product Id: "+ selectedProduct.getProductId());
                            name.setText("Name: "+ selectedProduct.getProductName());
                            availability.setText("Items Available: "+ selectedProduct.getNumberOfAvailableItems());
                            if (selectedProduct.getProductType() == "Clothing") {
                                Clothing selectedClothe = (Clothing)selectedProduct;
                                category.setText("Category: Clothing");
                                additional1.setText("Size: "+ selectedClothe.getSize());
                                additional2.setText("Colour: "+ selectedClothe.getColor());
                            } else {
                                Electronics selectedElectronic = (Electronics)selectedProduct;
                                category.setText("Category: Electronics");
                                additional1.setText("Brand: "+selectedElectronic.getBrandName());
                                additional2.setText("Warranty Period: "+selectedElectronic.getWarrantyPeriod());

                            }
                        }
                    }

                }
            }
        }

        //*****************************************************************************************************************

        //make row color red if availability is less than 3
        private class AvailibilityCellRender extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);     //get the component

                // Retrieve the product from the table model based on the row index
                ShoppingCenterTableModel model = (ShoppingCenterTableModel) table.getModel();    //get the table model
                Product product = model.getRowObject(row);      //get the product object from the table model based on the row index
                ProductCategoryComboBox productCategoryComboBox = new ProductCategoryComboBox(selectProductCategoryComboBox,
                        productsTable, (TableRowSorter<ShoppingCenterTableModel>) productsTable.getRowSorter());

                int comboBoxReq = productCategoryComboBox.getSelectProductCategoryComboBox();

                if(comboBoxReq == 1){
                    if(product.getProductType() == "Electronics"){
                        if (product.getNumberOfAvailableItems() <= 3) {
                            component.setBackground(Color.RED); // Set background color to red
                            component.setForeground(Color.WHITE); // Set text color to white
                        } else{
                            component.setBackground(Color.WHITE); // Set background color to default
                            component.setForeground(Color.BLACK); // Set text color to default
                        }
                    }
                } else if(comboBoxReq == 2){
                    if(product.getProductType() == "Clothing"){
                        if (product.getNumberOfAvailableItems() <= 3) {
                            component.setBackground(Color.RED); // Set background color to red
                            component.setForeground(Color.WHITE); // Set text color to white
                        } else{
                            component.setBackground(Color.WHITE); // Set background color to default
                            component.setForeground(Color.BLACK); // Set text color to default
                        }
                    }
                } else{
                    if (product.getNumberOfAvailableItems() <= 3) {
                        component.setBackground(Color.RED); // Set background color to red
                        component.setForeground(Color.WHITE); // Set text color to white
                    } else {
                        component.setBackground(table.getBackground()); // Set background color to default
                        component.setForeground(table.getForeground()); // Set text color to default
                    }
                }
                return component;
            }
        }
        ////////////////////////////////////////////////////////////////

        // Check if the product's availability
                /*if (product.getNumberOfAvailableItems() <= 3) {
                    component.setBackground(Color.RED); // Set background color to red
                    component.setForeground(Color.WHITE); // Set text color to white
                } else {
                    component.setBackground(table.getBackground()); // Set background color to default
                    component.setForeground(table.getForeground()); // Set text color to default
                }*/



        //*****************************************************************************************************************

        //add to cart button action listener class
        public class AddToCartButtonFunction implements ActionListener {
            private final JTable productsTable;
            private final ArrayList<Product> productList;
            private final ShoppingCartTableModel shoppingCartTableModel;
            private final ShoppingCart shoppingCart;
            /*private final boolean firstPurchase;*/

            public AddToCartButtonFunction(JTable productsTable, ArrayList<Product> productList,
                                           ShoppingCartTableModel shoppingCartTableModel, ShoppingCart
                                                   shoppingCartPageGUIGUI/*, boolean firstPurchase*/){
                this.productsTable = productsTable;
                this.productList = productList;
                this.shoppingCartTableModel = shoppingCartTableModel;
                this.shoppingCart = shoppingCartPageGUIGUI;
                /*this.firstPurchase = firstPurchase;*/
            }

            @Override
            public void actionPerformed(ActionEvent event) {
                int selectedRow = productsTable.getSelectedRow();       //get the selected row
                if(selectedRow != -1) {
                    String selectedProductId = productsTable.getValueAt(selectedRow, 0).toString();     //get the selected product id
                    Map<Product, Integer> quantityOnCart = shoppingCartTableModel.getShoppingCart().getQuantityOnCart();        //get the quantity on cart map from shopping cart table model

                    // get the selected product and check its availability
                    for (Product checker : productList) {
                        if (checker.getProductId().equals(selectedProductId) && checker.getNumberOfAvailableItems()>0) {
                            int availableItems;
                            if(quantityOnCart.containsKey(checker)){
                                availableItems = checker.getNumberOfAvailableItems() - quantityOnCart.get(checker);

                            }
                            else {
                                availableItems = checker.getNumberOfAvailableItems();
                            }
                            if (availableItems > 0) {
                                shoppingCartTableModel.getShoppingCart().addProduct(checker);
                                shoppingCart.updatePrices(shoppingCartTableModel.getShoppingCart().
                                                calculateTotalPrice(),
                                        shoppingCartTableModel.getShoppingCart());
                                shoppingCartTableModel.fireTableDataChanged();
                            }

                            break;
                        }
                    }

                }
            }
        }


        //*****************************************************************************************************************

        //combo box action listener class
        public class ProductCategoryComboBox implements ActionListener {
            JComboBox<String> selectProductCategoryComboBox;
            JTable productsTable;
            TableRowSorter<ShoppingCenterTableModel> sorter;

            public ProductCategoryComboBox(JComboBox<String> selectProductCategoryComboBox,JTable productsTable,
                                           TableRowSorter<ShoppingCenterTableModel> sorter){
                this.selectProductCategoryComboBox = selectProductCategoryComboBox;
                this.productsTable = productsTable;
                this.sorter = sorter;

    //            for (int i = 0; i < productsTable.getColumnCount(); i++) {
    //                productsTable.getColumnModel().getColumn(i).setCellRenderer(new AvailibilityCellRender());                  //make row color red if availability is less than 3
    //            }

            }
            public int getSelectProductCategoryComboBox() {
                return selectProductCategoryComboBox.getSelectedIndex();
            }

            @Override                                 //combo box action listener
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = selectProductCategoryComboBox.getSelectedIndex();
                if(selectedIndex == 1){
                    sorter.setRowFilter(RowFilter.regexFilter("^[E]", 2)); // Hide clothing items
                } else if (selectedIndex == 2) {
                    sorter.setRowFilter(RowFilter.regexFilter("^[C]",2));
                } else {
                    sorter.setRowFilter(null);
                }

            }

        }


        //*****************************************************************************************************************



    }
}
