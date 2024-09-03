import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Reader class provides methods to read products from a file.
 */
public class Reader {
    /**
     * Reads products from the specified file path and returns a list of products.
     *
     * @param filePath the path of the file to read
     * @return a list of products read from the file
     */
    public static List<Product> readProducts(String filePath) {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String name = parts[0];
                float price = Float.parseFloat(parts[1]);
                String[] nutritiveValues = parts[2].split(" ");
                float protein = Float.parseFloat(nutritiveValues[0]);
                float carbohydrate = Float.parseFloat(nutritiveValues[1]);
                float fat = Float.parseFloat(nutritiveValues[2]);

                Product product = new Product(name, price, protein, carbohydrate, fat);
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }
}


