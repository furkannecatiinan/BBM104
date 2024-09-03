import java.util.*;

public class Process {
    public void processCommand(String command, Inventory<Book> bookInventory, Inventory<Toy> toyInventory, Inventory<Stationery> stationeryInventory, List<String> output) {
        String[] parts = command.split("\t");
        String action = parts[0];

        switch (action) {
            case "ADD":
                String itemType = parts[1];
                switch (itemType) {
                    case "Book":
                        Book book = new Book(parts[2], parts[3], parts[4], Double.parseDouble(parts[5]));
                        bookInventory.add(book);
                        break;
                    case "Toy":
                        Toy toy = new Toy(parts[2], parts[3], parts[4], Double.parseDouble(parts[5]));
                        toyInventory.add(toy);
                        break;
                    case "Stationery":
                        Stationery stationery = new Stationery(parts[2], parts[3], parts[4], Double.parseDouble(parts[5]));
                        stationeryInventory.add(stationery);
                        break;
                }
                break;

            case "REMOVE":
                String barcodeToRemove = parts[1];
                Item removedItem = bookInventory.removeByBarcode(barcodeToRemove);
                if (removedItem != null) {
                    output.add("REMOVE RESULTS:");
                    output.add("Item is removed.");
                } else {
                    removedItem = toyInventory.removeByBarcode(barcodeToRemove);
                    if (removedItem != null) {
                        output.add("REMOVE RESULTS:");
                        output.add("Item is removed.");
                    } else {
                        removedItem = stationeryInventory.removeByBarcode(barcodeToRemove);
                        if (removedItem != null) {
                            output.add("REMOVE RESULTS:");
                            output.add("Item is removed.");
                        } else {
                            output.add("REMOVE RESULTS:");
                            output.add("Item is not found.");
                        }
                    }
                }
                output.add("------------------------------");
                break;
            case "SEARCHBYBARCODE":
                String barcodeToSearch = parts[1];
                Item foundItem = bookInventory.searchByBarcode(barcodeToSearch);
                if (foundItem == null) {
                    foundItem = toyInventory.searchByBarcode(barcodeToSearch);
                }
                if (foundItem == null) {
                    foundItem = stationeryInventory.searchByBarcode(barcodeToSearch);
                }
                output.add("SEARCH RESULTS:");
                if (foundItem != null) {
                    output.add(foundItem.toString());
                    output.add("------------------------------");
                } else {
                    output.add("Item is not found.");
                    output.add("------------------------------");
                }
                break;
            case "SEARCHBYNAME":
                String nameToSearch = parts[1];
                List<Item> foundItems = new ArrayList<>();
                foundItems.addAll(bookInventory.searchByName(nameToSearch));
                foundItems.addAll(toyInventory.searchByName(nameToSearch));
                foundItems.addAll(stationeryInventory.searchByName(nameToSearch));
                if (!foundItems.isEmpty()) {
                    //output.add("------------------------------");
                    output.add("SEARCH RESULTS:");
                    for (Item item : foundItems) {
                        output.add(item.toString());
                    }
                    output.add("------------------------------");
                }
                else {
                    output.add("SEARCH RESULTS:");
                    output.add("Item is not found.");
                    output.add("------------------------------");
                }
                break;
            case "DISPLAY":
                //output.add("------------------------------");
                output.add("INVENTORY:");
                output.addAll(bookInventory.getAllItems());
                output.addAll(toyInventory.getAllItems());
                output.addAll(stationeryInventory.getAllItems());
                output.add("------------------------------");
                break;
        }
    }
}
