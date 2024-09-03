import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * The PurchaseReader class is responsible for reading purchases from a file and creating Purchase objects.
 * It provides a static method to read purchases from a given file path and return a list of Purchase objects.
 */
public class PurchaseReader {

    /**
     * Reads purchases from a file and returns a list of Purchase objects.
     *
     * @param filePath the path of the file to read purchases from
     * @return a list of Purchase objects read from the file
     */
    public static List<Purchase> readPurchases(String filePath) {
        VendingMachine machine = new VendingMachine();
        List<Purchase> purchaseList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String[] moneyStr = parts[1].split(" ");
                int[] money = new int[moneyStr.length];
                for (int i = 0; i < moneyStr.length; i++) {
                    money[i] = Integer.parseInt(moneyStr[i]);
                }
                SelectionType choice = SelectionType.valueOf(parts[2].toUpperCase());

                int value = Integer.parseInt(parts[3]);

                Purchase purchase = new Purchase(machine, money, choice, value); 
                purchaseList.add(purchase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchaseList;
    }
}


