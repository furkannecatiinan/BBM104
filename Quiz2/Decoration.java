public class Decoration {
    public Decoration(String name, String type, double price, double area) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.area = area;
    }
    private String type;
    private String name;
    private double price;
    private double area;

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getArea() {
        return area;
    }

    public String getName() {
        return name;

    }
}
