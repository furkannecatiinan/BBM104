public class PremiumBus extends Bus {
    private double premiumFee;
    private double refundCut;

    public PremiumBus(int id, String from, String to, int rows, double price, double refundCut, double premiumFee) {
        super(id, from, to, rows, price, 3 * rows);
        this.refundCut = refundCut;
        this.premiumFee = premiumFee;
    }

    public double getPremiumFee() {
        return premiumFee;
    }
    public boolean isPremiumSeat(int seatNumber) {
        return seatNumber % 3 == 1;

    }
    public double getPriceForSeat(int seatNumber) {
        double basePrice = super.getPriceForSeat(seatNumber);
        return isPremiumSeat(seatNumber) ? (basePrice * (1 + premiumFee / 100)) : basePrice;
    }

    public double getRefundCut() {
        return refundCut;
    }

    @Override
    public boolean isRefundable() {
        return true;
    }

    @Override
    public double getRefundRate() {
        return refundCut;
    }

}
