

public class Minibus extends Bus {
    private boolean refundable = false;

    public Minibus(int id, String from, String to, int rows, double price) {
        super(id, from, to, rows, price, 2*rows);
    }

    public boolean isRefundable() {
        return false;
    }

}
