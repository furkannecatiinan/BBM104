import java.util.List;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
 * The Main class is the entry point of the program.
 * It reads the command line arguments, processes the purchases, and prints the status of the vending machine.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Main <ProductsFilePath> <PurchasesFilePath> <OutputFilePath>");
            return;
        }

        String productsFilePath = args[0];
        String purchasesFilePath = args[1];
        String outputFilePath = args[2];
    
        try (PrintWriter printWriter = new PrintWriter(outputFilePath)) {

            List<Product> productList = Reader.readProducts(productsFilePath);


            VendingMachine vendingMachine = new VendingMachine();

            
            vendingMachine.fillSequentially(productList, printWriter); 
            vendingMachine.printStatus(printWriter);

           
            List<Purchase> purchases = PurchaseReader.readPurchases(purchasesFilePath);

            
            for (Purchase purchase : purchases) {
                vendingMachine.processPurchase(purchase, printWriter); 
            }

            vendingMachine.printStatus(printWriter);

        } catch (FileNotFoundException e) {
            System.err.println("Error opening the output file: " + e.getMessage());
        }
    }

}
