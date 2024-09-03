

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class  Voyage {

    private int voyageID;
    private String fromLocation;
    private String toLocation;
    private Bus bus;
    private double revenue = 0;
    private Map<Integer, Boolean> seatAvailability = new HashMap<>();
    private PrintWriter writer;
    double refundAmount ;


    public Voyage(int voyageID, String fromLocation, String toLocation, Bus bus, PrintWriter writer) {
        this.voyageID = voyageID;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.bus = bus;
        this.writer = writer;
        initializeSeats();
    }
    public Voyage(int voyageID, Bus bus, PrintWriter writer) {
        this.voyageID = voyageID;
        this.fromLocation = bus.getFrom();
        this.toLocation = bus.getTo();
        this.bus = bus;
        this.writer = writer;
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= bus.getTotalSeats(); i++) {
            seatAvailability.put(i, true);
        }
    }


    public int getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(int voyageID) {
        this.voyageID = voyageID;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public Map<Integer, Boolean> getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(Map<Integer, Boolean> seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

    public boolean sellTicket(int seatNumber) {
        if (!seatAvailability.getOrDefault(seatNumber, false)) {
             writer.println("ERROR: Seat " + seatNumber + " is not available or does not exist.");
            return false;
        }

        double ticketPrice = bus.getPriceForSeat(seatNumber);

        seatAvailability.put(seatNumber, false);
        revenue += ticketPrice;

        return true;
    }

    public boolean refundTicket(int seatNumber) {
        if (!seatAvailability.getOrDefault(seatNumber, true) && bus.isRefundable()) {
            double refundAmount = bus.getPriceForSeat(seatNumber); // Temel iade miktarı
            double refundRate = bus.getRefundRate();

                refundAmount = ((100.0 - refundRate) / 100.0) * refundAmount;


            seatAvailability.put(seatNumber, true);
            revenue -= refundAmount;

            return true;
        }
        else if (!bus.isRefundable()){
            return false;
        }
        else {
            return false;
        }
    }


   public void printVoyageDetails() {

       if (writer == null) {
           throw new IllegalStateException("PrintWriter has not been initialized in printdetail");
       }
        writer.println("Voyage " + voyageID);
        writer.println(fromLocation + "-" + toLocation);

       int seatsPerRow = bus instanceof Minibus ? 2 : (bus instanceof PremiumBus ? 3 : 4);
       boolean isPremiumBus = bus instanceof PremiumBus;
       boolean isStandardBus = bus instanceof StandardBus;

       int totalSeats = bus.getTotalSeats();
       for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
           writer.print(seatAvailability.getOrDefault(seatNumber, true) ? "*" : "X");

           if (isPremiumBus && seatsPerRow == 3 && seatNumber % seatsPerRow == 1) {
               writer.print(" | ");
           } else if (seatNumber % seatsPerRow != 0) {
               writer.print(" ");
           }

           if (isStandardBus && seatNumber % seatsPerRow == 2) {
               writer.print("| ");
           } else if (seatNumber % seatsPerRow != 0) {
               writer.print("");
           }
           if (seatNumber % seatsPerRow == 0 || seatNumber == totalSeats) {
                writer.println();
           }
       }

        writer.println("Revenue: " + String.format("%.2f", revenue));
   }


    public boolean isSeatAvailable(int seatNumber) {
        return seatAvailability.getOrDefault(seatNumber, false);
    }

    public void refundAllSoldTickets() {
        for (Map.Entry<Integer, Boolean> entry : seatAvailability.entrySet()) {
            if (!entry.getValue()) {
                double ticketPrice = bus.getPriceForSeat(entry.getKey());
                revenue -= ticketPrice;
            }
        }
    }

    public double getRefundedAmount() {
       return refundAmount;
    }

    public double calculateTicketPrice(int seatNumber) {return bus.getPriceForSeat(seatNumber);
    }

    public double calculateRefundAmount(int seatNumber) {
        if (!isSeatAvailable(seatNumber) && bus.isRefundable()) {
            double ticketPrice = bus.getPriceForSeat(seatNumber);
            double refundRate = bus.getRefundRate();
            return ((100.0 - refundRate) / 100.0) * ticketPrice;
        }
        return 0.0;
    }
    public void setWriter(PrintWriter writer2) {
        throw new UnsupportedOperationException("Unimplemented method 'setWriter'");
    }

}

