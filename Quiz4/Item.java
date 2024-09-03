public abstract class Item {
    private String name;
    private String barcode;
    private double price;

    public Item(String name, String barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + "\t" + barcode + "\t" + price;
    }
}

class Book extends Item {
    private String author;

    public Book(String name, String author, String barcode, double price) {
        super(name, barcode, price);
        this.author = author;
    }

    @Override
    public String toString() {
        return "Author of the " + super.getName() + " is " + author+". Its barcode is " + super.getBarcode() + " and its price is " + super.getPrice();
    }
}

class Toy extends Item {
    private String color;

    public Toy(String name, String color, String barcode, double price) {
        super(name, barcode, price);
        this.color = color;
    }

    @Override
    public String toString() {
        //return "Toy\t" + super.toString() + "\t" + color;
        return "Color of the " + super.getName() + " is " + color + ". Its barcode is " + super.getBarcode() + " and its price is " + super.getPrice();
    }
}

class Stationery extends Item {
    private String kind;

    public Stationery(String name, String kind, String barcode, double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Kind of the " + super.getName() + " is " + kind + ". Its barcode is " + super.getBarcode() + " and its price is " + super.getPrice();
    }
}
















