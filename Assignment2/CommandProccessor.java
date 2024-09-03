import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CommandReader {

    private BufferedReader reader;
    private PrintWriter writer;
    private VoyageManager voyageManager;

    private boolean lastCommandWasZReport = false;

    public CommandReader(String inputFilePath, String outputFilePath, VoyageManager voyageManager) throws IOException {
        this.reader = new BufferedReader(new FileReader(inputFilePath));
        this.writer = new PrintWriter(new FileWriter(outputFilePath), true);
        this.voyageManager = voyageManager;
        this.voyageManager.setWriter(this.writer);
    }

    public void processCommands() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!processCommand(line)) {

                }
            }
            if (!lastCommandWasZReport) {
                voyageManager.generateZReport();
            }

        } catch (IOException e) {
             writer.println("ERROR: An error occurred while reading the input file -> " + e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                 writer.println("ERROR: Failed to close the input file -> " + e.getMessage());

            }
        writer.close();
        }

    }

    private boolean processCommand(String command) {
        String[] parts = command.split("\t");
         writer.println("COMMAND: " + command);
        lastCommandWasZReport = false;


        try {
            switch (parts[0]) {
                case "Z_REPORT":
                    if (parts.length != 1) {
                         writer.println("ERROR: Erroneous usage of \"Z_REPORT\" command!");
                        return false;
                    }
                    voyageManager.generateZReport();
                    lastCommandWasZReport = true;
                    return true;

                case "INIT_VOYAGE":
                    lastCommandWasZReport = false;
                    return initVoyage(parts);

                case "SELL_TICKET":
                    if (parts.length == 3) {

                        int voyageID = Integer.parseInt(parts[1]);
                        String[] seatNumbers = parts[2].split("_");
                        List<String> soldSeats = new ArrayList<>();
                        Voyage voyage = voyageManager.getVoyage(voyageID);

                        if (Integer.parseInt(parts[1]) <= 0) {
                             writer.println("ERROR:"+ voyageID +" is not a positive integer, ID of a voyage must be a positive integer!");
                            return false;
                        }
                        if (voyage == null) {
                             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
                            return false;
                        }
                        double totalTicketPrice = 0.0;
                        for (String seatStr : seatNumbers) {
                            int seatNumber = Integer.parseInt(seatStr);
                            if (seatNumber <= 0) {
                                 writer.println("ERROR: "+ seatNumber +" is not a positive integer, seat number must be a positive integer!");
                                return false;
                            }
                            if (seatNumber > voyage.getBus().getTotalSeats()) {
                                 writer.println("ERROR: There is no such a seat!");
                                return false;
                            }

                            if (!voyage.isSeatAvailable(seatNumber)) {
                                 writer.println("ERROR: One or more seats already sold!");
                                return false;
                            }
                        }

                        for (String seatStr : seatNumbers) {
                            int seatNumber = Integer.parseInt(seatStr);
                            double ticketPrice = voyage.calculateTicketPrice(seatNumber);
                            if (voyageManager.sellTicket(voyageID, seatNumber)) {
                                if (ticketPrice > 0) {
                                    totalTicketPrice += ticketPrice;
                                soldSeats.add(seatStr);
                            }

                        }}

                        if (!soldSeats.isEmpty()) {

                            String soldSeatsStr = String.join("-", soldSeats);
                             writer.println("Seat " + soldSeatsStr + " of the Voyage " + voyageID + " from " + voyage.getFromLocation() + " to " + voyage.getToLocation() + " was successfully sold for " + String.format("%.2f", totalTicketPrice) + " TL.");

                        }
                        lastCommandWasZReport = false;
                        return true;
                    } else {
                         writer.println("ERROR: Erroneous usage of \"SELL_TICKET\" command!");
                    }
                    break;


                case "REFUND_TICKET":
                    if (parts.length == 3) {
                        int voyageID = Integer.parseInt(parts[1]);
                        String[] seatNumbers = parts[2].split("_");
                        List<String> refundedSeats = new ArrayList<>();
                        Voyage voyage = voyageManager.getVoyage(voyageID);
                        double totalRefundAmount = 0.0;

                        if (voyageID <= 0) {
                             writer.println("ERROR: " + voyageID + " is not a positive integer, ID of a voyage must be a positive integer!");
                            return false;
                        }
                        if (voyage == null) {
                             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
                            return false;
                        }
                        if (voyage.getBus() instanceof Minibus) {
                             writer.println("ERROR: Minibus tickets are not refundable!");
                            return false;
                        }

                        for (String seatStr : seatNumbers) {
                            int seatNumber = Integer.parseInt(seatStr);
                            if (seatNumber <= 0 ) {
                                 writer.println("ERROR: "+seatNumber+" is not a positive integer, seat number must be a positive integer!");
                                return false;
                            }
                            if ( seatNumber > voyage.getBus().getTotalSeats()) {
                                 writer.println("ERROR: There is no such a seat!");
                                return false;
                            }

                            if (voyage.isSeatAvailable(seatNumber)) {
                                 writer.println("ERROR: One or more seats are already empty!");
                                return false;
                            }
                        }

                        for (String seatStr : seatNumbers) {
                            int seatNumber = Integer.parseInt(seatStr);
                            if (seatNumber <= 0 ) {
                                 writer.println("ERROR: "+ seatNumber +" is not a positive integer, seat number must be a positive integer!");
                                 writer.println();
                                return false;
                            }
                            if ( seatNumber > voyage.getBus().getTotalSeats()) {
                                 writer.println("ERROR: There is no such a seat!");
                                return false;
                            }

                            double refundAmount = voyage.calculateRefundAmount(seatNumber);
                            if (voyage.refundTicket(seatNumber)) {
                                totalRefundAmount += refundAmount;
                                refundedSeats.add(seatStr);
                            }
                        }

                        if (!refundedSeats.isEmpty()) {
                            String refundedSeatsStr = String.join("-", refundedSeats);
                             writer.println("Seat " + refundedSeatsStr + " of the Voyage " + voyageID + " from " + voyage.getFromLocation() + " to " + voyage.getToLocation() + " was successfully refunded for " + String.format("%.2f", totalRefundAmount) + " TL.");
                        }
                        lastCommandWasZReport = false;
                        return true;
                    } else {
                         writer.println("ERROR: Erroneous usage of \"REFUND_TICKET\" command!");
                    }
                    break;



                case "CANCEL_VOYAGE":
                    if (parts.length == 2) {
                        int voyageID = Integer.parseInt(parts[1]);
                        Voyage voyage = voyageManager.getVoyage(voyageID);


                        if (Integer.parseInt(parts[1]) <= 0) {
                             writer.println("ERROR: "+ voyageID +" is not a positive integer, ID of a voyage must be a positive integer!");
                            return false;
                        }
                        if (voyage == null) {
                             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
                            return false;
                        }

                        voyageManager.refundCancellingPrice(voyageID);
                        voyageManager.printVoyage(voyageID);
                        voyageManager.cancelVoyage(voyageID);

                        lastCommandWasZReport = false;
                        return true;
                    }
                    else {
                         writer.println("ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!");
                    }
                    break;
                case "PRINT_VOYAGE":
                    if (parts.length == 2) {
                        int voyageID = Integer.parseInt(parts[1]);
                        Voyage voyage = voyageManager.getVoyage(voyageID);
                        if (Integer.parseInt(parts[1]) <= 0) {
                             writer.println("ERROR: " + voyageID + " is not a positive integer, ID of a voyage must be a positive integer!");
                            return false;
                        }
                        if (voyage == null) {
                             writer.println("ERROR: There is no voyage with ID of " + voyageID + "!");
                            return false;
                        }

                        voyageManager.printVoyage(voyageID);
                        lastCommandWasZReport = false;
                        return true;
                    }
                    else {
                         writer.println("ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!");
                    }
                    break;
                default:
                    String commandType = parts[0];

                     writer.println("ERROR: There is no command namely " + commandType + "!");
                    lastCommandWasZReport = false; // Diğer komutlar için bu değeri false yap
                    return false; // Eğer hiçbir koşula girmezse
            }
        } catch (NumberFormatException e) {
             writer.println("ERROR: Invalid number format -> " + command);
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
             writer.println("ERROR: Missing arguments -> " + command);
            return false;
        }
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!
        // GENEL EXCEPTİONLAR EKLE HERHANGİ Bİ DURUMDA PATLAMASIN!!!!!!!!!

        return false;
    }

    private boolean initVoyage(String[] parts) {
        if ((parts[1].equals("Minibus") && parts.length == 7) ||
                (parts[1].equals("Standard") && parts.length == 8) ||
                (parts[1].equals("Premium") && parts.length == 9)) {

            int id = Integer.parseInt(parts[2]);
            if (id <= 0) {
                 writer.println("ERROR: " + id + " is not a positive integer, ID of a voyage must be a positive integer!");
                return false;
            }

            String from = parts[3];
            String to = parts[4];
            int rows = Integer.parseInt(parts[5]);
            if (rows <= 0) {
                 writer.println("ERROR: "+ rows + " is not a positive integer, number of seat rows of a voyage must be a positive integer!");
                return false;
            }
            double price = Double.parseDouble(parts[6]);
            if (price < 0) {
                 writer.println("ERROR: "+ (int) price + " is not a positive number, price must be a positive number!");
                return false;
            }
            switch (parts[1]) {
                case "Minibus":
                    Minibus minibus = new Minibus(id, from, to, rows, price);
                    voyageManager.initVoyage(new Voyage(id, minibus, writer));
                    return true;
                case "Standard":
                    double refundCut = Double.parseDouble(parts[7]);
                    if (refundCut < 0 || refundCut > 100) {
                         writer.println("ERROR: " + (int) refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
                        return false;
                    }
                    StandardBus standardBus = new StandardBus(id, from, to, rows, price, refundCut);
                    voyageManager.initVoyage(new Voyage(id, standardBus, writer));
                    return true;
                case "Premium":
                    refundCut = Double.parseDouble(parts[7]);
                    if (refundCut < 0 || refundCut > 100) {
                         writer.println("ERROR: " + (int) refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
                        return false;
                    }
                    double premiumFee = Double.parseDouble(parts[8]);
                    if (premiumFee < 0) {
                         writer.println("ERROR: " + (int) premiumFee + " is not a non-negative integer, premium fee must be a non-negative integer!");
                        return false;
                    }
                    PremiumBus premiumBus = new PremiumBus(id, from, to, rows, price, refundCut, premiumFee);
                    voyageManager.initVoyage(new Voyage(id, premiumBus, writer));
                    return true;
                default:
                     writer.println("ERROR: Unknown bus type: " + parts[1]);
                    return false;
            }
        } else {
             writer.println("ERROR: Erroneous usage of "+"\"INIT_VOYAGE\""+" command!");
            return false;
        }

    }
    public static int getPartsTwo(String[] parts) {
        return Integer.parseInt(parts[2]);
    }

}
