
public class StandardBus extends Bus {
    protected double refundCut;
    public StandardBus(int id, String from, String to, int rows, double price, double refundCut) {
        super(id, from, to, rows, price, 4*rows);
        this.refundCut = refundCut;
    }
    @Override
    public double getRefundRate() {
        return refundCut;
    }
}
