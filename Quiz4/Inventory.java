import java.util.*;

public class Inventory<T extends Item>{
    public List<T> items = new ArrayList<>();

    //Add item to the inventory
    public void add(T item){
        items.add(item);
    }


    public T removeByBarcode(String barcode) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item.getBarcode().equals(barcode)) {
                iterator.remove();
                return item; // Return the removed item
            }
        }
        return null; // Item not found
    }
    //Search item by barcode
    public T searchByBarcode(String barcode){
        for (T item : items) {
            if (item.getBarcode().equals(barcode)) {
                return item;
            }
        }
        return null;
    }
    //Search item by name
    public List<T> searchByName(String name){
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (item.getName().equals(name)) {
                result.add(item);
            }
        }
        return result;
    }
    public List<String> getAllItems(){
        List<String > tempItemList = new ArrayList<>();
        for (T item : items) {
            tempItemList.add(item.toString());
        }
        return tempItemList;
    }

}