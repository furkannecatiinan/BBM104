import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a vending machine capable of selecting products based on various criteria and handling transactions.
 */

public class VendingMachine {

    private List<Integer> validDenominations = Arrays.asList(1, 5, 10, 20, 50, 100, 200);
    private Product[][] products;
    // Current selection type made on the vending machine.
    private SelectionType choice;


    /**
     * Constructs a VendingMachine object with an initial empty products array.
     */
    public VendingMachine() {
        this.products = new Product[6][4];
    }


    /**
     * Gets the current selection type.
     *
     * @return The current selection type.
     */
    public SelectionType getChoice() {
        return this.choice;
    }

    /**
     * Selects a product based on the purchase criteria.
     *
     * @param purchase The purchase containing the selection criteria.
     * @return The selected product, or null if no product matches the criteria.
     */
    public Product selectProductByCriteria(Purchase purchase) {
        switch (purchase.getChoice()) {
            case NUMBER:
                return selectProductBySlot(purchase.getValue());
            case PROTEIN:
                return selectProductByNutrient("protein", purchase.getValue());
            case CARB:
                return selectProductByNutrient("carb", purchase.getValue());
            case FAT:
                return selectProductByNutrient("fat", purchase.getValue());
            case CALORIE:
                return selectProductByNutrient("calorie", purchase.getValue());
            default:
                return null;
        }
    }

    /**
     * Selects a product based on the specified nutrient type and target value.
     *
     * @param nutrientType The type of nutrient to filter by.
     * @param targetValue The target value for the nutrient.
     * @return The selected product, or null if no product matches the criteria.
     */
    private Product selectProductByNutrient(String nutrientType, int targetValue) {
        for (Product[] productRow : products) {
            for (Product product : productRow) {
                if (product != null && product.getQuantity() > 0) {
                    switch (nutrientType) {
                        case "protein":
                            if (product.getProtein() >= targetValue - 5 && product.getProtein() <= targetValue + 5) return product;
                            break;
                        case "carb":

                            if (product.getCarbohydrate() >= targetValue - 5 && product.getCarbohydrate() <= targetValue + 5) return product;
                            break;
                        case "fat":
                            if (product.getFat() >= targetValue - 5 && product.getFat() <= targetValue + 5) return product;
                            break;
                        case "calorie":
                            if (product.getCalories() >= targetValue - 5 && product.getCalories() <= targetValue + 5) return product;
                            break;
                    }
                }
            }
        }
        return null;
    }

     /**
     * Selects a product based on its slot number.
     *
     * @param slot The slot number of the product.
     * @return The selected product, or null if the slot is empty or the product is out of stock.
     */
    private Product selectProductBySlot(int slot) {
        int rows = products.length;
        int cols = products[0].length;

        int row = slot / cols;
        int col = slot % cols;

        if (row < rows && col < cols && products[row][col] != null && products[row][col].getQuantity() > 0) {
            return products[row][col];
        }
        return null;
    }

     /**
     * Finds a product by its name.
     *
     * @param name The name of the product.
     * @return The product with the specified name, or null if not found.
     */
    public Product findProductByName(String name) {
        for (Product[] row : products) {
            for (Product product : row) {
                if (product != null && product.getName().equals(name)) {
                    return product;
                }
            }
        }
        return null;
    }

    public Product[][] getProducts() {
        return products;
    }

    public void setProducts(Product[][] products) {
        this.products = products;
    }


    /**
     * Fills the vending machine with products sequentially.
     *
     * @param productList  the list of products to be placed in the vending machine
     * @param printWriter  the PrintWriter object used to print information messages
     * @return             -1 if any product could not be placed, 0 otherwise
     */
    public int fillSequentially(List<Product> productList, PrintWriter printWriter) {
        boolean machineFullyLoaded = false;

        for (Product incomingProduct : productList) {
            if (machineFullyLoaded) {
                break;
            }

            boolean productPlaced = false;
            for (int row = 0; row < products.length && !productPlaced; row++) {
                for (int col = 0; col < products[row].length && !productPlaced; col++) {
                    if (products[row][col] == null) {
                        products[row][col] = incomingProduct;
                        productPlaced = true;
                    } else if (products[row][col].getName().equals(incomingProduct.getName()) &&
                            products[row][col].getQuantity() < 10) {
                        products[row][col].increaseQuantity();
                        productPlaced = true;
                    }
                }
            }

            if (!productPlaced) {
                printWriter.println("INFO: There is no available place to put " + incomingProduct.getName());
                if (!isAnySlotAvailable()) {
                    printWriter.println("INFO: The machine is full!");
                    machineFullyLoaded = true;
                }
            }
        }

        return machineFullyLoaded ? -1 : 0;
    }

    private boolean isAnySlotAvailable() {
        for (int row = 0; row < products.length; row++) {
            for (int col = 0; col < products[row].length; col++) {
                if (products[row][col] == null || products[row][col].getQuantity() < 10) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Calculates the total money inserted by the customer.
     * @param money
     * @return total money
     */
    private float calculateTotalMoney(int[] money) {
        int total = 0;
        for (int m : money) {
            total += m;
        }
        return total;
    }

    /**
     * Processes a purchase made by a customer.
     *
     * @param purchase The purchase made by the customer.
     * @param printWriter The PrintWriter object used to write output.
     */
    public void processPurchase(Purchase purchase, PrintWriter printWriter) {
        int totalValidMoney = 0;
        StringBuilder invalidMoneyStr = new StringBuilder();
        StringBuilder validMoneyStr = new StringBuilder();

        for (int money : purchase.getMoney()) {
            if (validDenominations.contains(money)) {
                totalValidMoney += money;
                if (validMoneyStr.length() > 0) validMoneyStr.append(" ");
                validMoneyStr.append(money);
            } else {
                if (invalidMoneyStr.length() > 0) invalidMoneyStr.append(" ");
                invalidMoneyStr.append(money);
            }
        }

        if (invalidMoneyStr.length() > 0) {
            printWriter.println("INFO: Invalid money: " + invalidMoneyStr + ". Returning all inserted invalid money.");
        }
        Product selectedProduct = null;
        boolean invalidNumber = false;

        if (purchase.getChoice().equals(SelectionType.NUMBER)) {
            int slot = purchase.getValue();
            if (slot < 0 || slot >= 24) {
                printWriter.println("INPUT: CASH\t" + validMoneyStr.toString() + "\t" + purchase.getChoice().name() + "\t" + purchase.getValue());
                printWriter.println("INFO: Number cannot be accepted. Please try again with another number.");
                printWriter.println("RETURN: Returning your change: " + totalValidMoney + " TL");
                return;
            }

            selectedProduct = selectProductBySlot(slot);
            if (selectedProduct == null || selectedProduct.getQuantity() <= 0) {
                printWriter.println("INPUT: CASH\t" + validMoneyStr.toString() + "\t" + purchase.getChoice().name() + "\t" + purchase.getValue());
                printWriter.println("INFO: This slot is empty, your money will be returned.");
                printWriter.println("RETURN: Returning your change: " + totalValidMoney + " TL");
                return;
            }
        } else {
            selectedProduct = selectProductByCriteria(purchase);
            if (selectedProduct == null) {
                printWriter.println("INPUT: CASH\t" + validMoneyStr.toString() + "\t" + purchase.getChoice().name() + "\t" + purchase.getValue());
                printWriter.println("INFO: Product not found, your money will be returned.");
                printWriter.println("RETURN: Returning your change: " + totalValidMoney + " TL");
                return;
            }
        }

        if (totalValidMoney >= selectedProduct.getPrice()) {
            selectedProduct.decreaseQuantity();
            int change = totalValidMoney - (int)selectedProduct.getPrice();
            printWriter.println("INPUT: CASH\t" + validMoneyStr.toString() + "\t" + purchase.getChoice().name() + "\t" + purchase.getValue());
            printWriter.println("PURCHASE: You have bought one " + selectedProduct.getName());
            printWriter.println("RETURN: Returning your change: " + change + " TL");
        } else {
            printWriter.println("INPUT: CASH\t" + validMoneyStr.toString() + "\t" + purchase.getChoice().name() + "\t" + purchase.getValue());
            printWriter.println("INFO: Insufficient money, try again with more money.");
            printWriter.println("RETURN: Returning your change: " + totalValidMoney + " TL");
        }
    }


    /**
     * Prints the status of the vending machine to the specified PrintWriter.
     * The status includes the name, calories, and quantity of each product in the machine.
     *
     * @param printWriter the PrintWriter to write the status to
     */
    public void printStatus(PrintWriter printWriter) {
        printWriter.println("-----Gym Meal Machine-----");
        for (int row = 0; row < products.length; row++) {
            for (int col = 0; col < products[row].length; col++) {
                Product product = products[row][col];
                if (product != null && product.getQuantity() > 0) {
                    printWriter.print(product.getName() + "(" + product.getCalories() + ", " + product.getQuantity() + ")" + "___");
                } else {
                    printWriter.print("___" + "(0, 0)" + "___");
                }
            }

            printWriter.println();
        }
        printWriter.println("----------");
    }
}




