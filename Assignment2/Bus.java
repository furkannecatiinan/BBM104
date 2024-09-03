

public class Bus {
    public boolean isRefundable;
    protected int id;
    protected String from;
    protected String to;
    protected int rows;
    protected double price;
    protected int totalSeats;

    public Bus(int id, String from, String to, int rows, double price, int totalSeats) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.rows = rows;
        this.price = price;
        this.totalSeats = totalSeats;
    }

    // Getter metodları
    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getRows() {
        return rows;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    // Setter metodları
    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getPriceForSeat(int seatNumber) {
        return price;
    }
    public boolean isRefundable(){
        return true;
    }
    public double getRefundRate(){
        return 0;
    }
}
