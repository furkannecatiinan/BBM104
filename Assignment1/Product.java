/**
 * Represents a product with its name, price, nutritional information, and quantity.
 */
public class Product {
    private String name;
    private float price; 
    private float protein;
    private float carbohydrate;
    private float fat;
    private float calories;
    private int quantity;

    /**
     * Constructs a new Product object with the specified name, price, protein, carbohydrate, and fat.
     * The calories are calculated based on the nutritional information.
     * The initial quantity is set to 1.
     *
     * @param name          the name of the product
     * @param price         the price of the product
     * @param protein       the protein content of the product
     * @param carbohydrate  the carbohydrate content of the product
     * @param fat           the fat content of the product
     */
    public Product(String name, float price, float protein, float carbohydrate, float fat) {
        this.name = name;
        this.price = price; 
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calories = calculateCalories(); 
        this.quantity = 1; 
    }

    /**
     * Decreases the quantity of the product by 1.
     * If the quantity is already 0, it remains unchanged.
     */
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     *
     * @return the price of the product
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returns the protein content of the product.
     *
     * @return the protein content of the product
     */
    public float getProtein() {
        return protein;
    }

    /**
     * Returns the carbohydrate content of the product.
     *
     * @return the carbohydrate content of the product
     */
    public float getCarbohydrate() {
        return carbohydrate;
    }

    /**
     * Returns the fat content of the product.
     *
     * @return the fat content of the product
     */
    public float getFat() {
        return fat;
    }

    /**
     * Returns the calories of the product.
     *
     * @return the calories of the product
     */
    public int getCalories() {
        return (int) calories;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Increases the quantity of the product by 1.
     * If the quantity is already 10, it remains unchanged.
     */
    public void increaseQuantity() {
        if (quantity < 10) {
            quantity++;
        }
    }

    /**
     * Calculates the calories of the product based on the protein, carbohydrate, and fat content.
     *
     * @return the calculated calories of the product
     */
    private int calculateCalories() {
        return (int) Math.round((protein * 4) + (carbohydrate * 4) + (fat * 9));
    }
}
