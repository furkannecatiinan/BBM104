import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class VoyageManager {
    private PrintWriter writer;
    private Map<Integer, Voyage> voyages = new HashMap<>();

    public VoyageManager(PrintWriter writer) {
        this.writer = writer;
    }

    public boolean initVoyage(Voyage voyage) {
        if (!voyages.containsKey(voyage.getVoyageID())) {
            voyages.put(voyage.getVoyageID(), voyage);

            String busTypeMessage;
            String refundRateMessage = String.format("%.0f", voyage.getBus().getRefundRate());
            if (voyage.getBus() instanceof Minibus) {
                busTypeMessage = "minibus (2) voyage from " + voyage.getFromLocation() + " to " + voyage.getToLocation() +
                        " with " + String.format("%.2f" ,voyage.getBus().getPrice()) + " TL priced " +
                        voyage.getBus().getTotalSeats() + " regular seats. Note that minibus tickets are not refundable.";
            } else if (voyage.getBus() instanceof StandardBus) {
                busTypeMessage = "standard (2+2) voyage from " + voyage.getFromLocation() + " to " + voyage.getToLocation() +
                        " with " + String.format("%.2f" ,voyage.getBus().getPrice()) + " TL priced " +
                        voyage.getBus().getTotalSeats() + " regular seats. Note that refunds will be " +
                        refundRateMessage + "% less than the paid amount.";
            } else if (voyage.getBus() instanceof PremiumBus) {
                int regularSeatsForPremium = voyage.getBus().getTotalSeats()-((PremiumBus) voyage.getBus()).rows;
                int premiumSeats = ((PremiumBus) voyage.getBus()).rows;
                double premiumPrice = voyage.getBus().getPrice() * (1 + ((PremiumBus) voyage.getBus()).getPremiumFee() / 100);
                busTypeMessage = "premium (1+2) voyage from " + voyage.getFromLocation() + " to " + voyage.getToLocation() +
                        " with " + String.format("%.2f" ,voyage.getBus().getPrice()) + " TL priced " +
                        regularSeatsForPremium + " regular seats and " +
                        String.format("%.2f", premiumPrice) + " TL priced "+ premiumSeats +" premium seats. Note that refunds will be " +
                        refundRateMessage + "% less than the paid amount.";
            } else {
                busTypeMessage = "Unknown bus type";
            }

             writer.println("Voyage " + voyage.getVoyageID() + " was initialized as a " + busTypeMessage);
            return true;
        } else {
             writer.println("ERROR: There is already a voyage with ID of " + voyage.getVoyageID() + "!");
            return false;
        }
    }


    public boolean sellTicket(int voyageID, int seatNumber) {
        Voyage voyage = voyages.get(voyageID);

        if (voyage == null) {
             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
            return false;
        }

        return voyage.sellTicket(seatNumber);
    }
    public boolean refundTicket(int voyageID, int seatNumber) {
        Voyage voyage = voyages.get(voyageID);
        if (voyage == null) {
             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
            return false;
        }
        return voyage.refundTicket(seatNumber);
    }

    public void refundCancellingPrice(int voyageID) {
        Voyage voyage = voyages.get(voyageID);
        if (voyage != null) {
            voyage.refundAllSoldTickets();
                 writer.println("Voyage " + voyageID + " was successfully cancelled!");
             writer.println("Voyage details can be found below:");
        }
    }
    public void cancelVoyage(int voyageID) {
        Voyage voyage = voyages.get(voyageID);
        if (voyage != null) {
            voyages.remove(voyageID);
        } else {
             writer.println("ERROR: There is no voyage with ID " + voyageID + "!");
        }
    }


    public void printVoyage(int voyageID){
        Voyage voyage = voyages.get(voyageID);
        if (voyage != null) {
            voyage.printVoyageDetails();
        }
        else {
             writer.println("ERROR: There is no voyage with ID of " + voyageID +"!");
        }
    }
    public void generateZReport() {
        if (voyages.isEmpty()) {
             writer.println("Z Report:");
             writer.println("----------------");
             writer.println("No Voyages Available!");
             writer.println("----------------");
        } else {
             writer.println("Z Report:");
             writer.println("----------------");
            for (Voyage voyage : voyages.values()) {
                voyage.printVoyageDetails();
                writer.println("----------------");
            }
        }
    }


    public Voyage getVoyage(int voyageID) {
        return voyages.get(voyageID);
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }
}


