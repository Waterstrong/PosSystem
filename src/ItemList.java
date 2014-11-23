import java.util.HashMap;
import java.lang.String;

/**
 * Created by water on 14-11-21.
 */
public class ItemList {
    private HashMap<String, Double> Items;
    protected void HandleItem(String item) {
        // ...
    }
    public ItemList() {
        //LoadItemFromFile("itemlist.txt");
    }
    public void clearItem() {
        Items.clear();
    }
    public void loadItemFromFile(String fileName){

    }
    public double getItemPrice(String barcode) {
        return Items.get(barcode);
    }
}
