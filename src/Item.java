/**
 * Created by water on 14-11-21.
 */
public class Item {
    protected String Barcode;
    protected double Price;
    protected int Amount;
    public Item(String barcode, double price, int amount)
    {
        Barcode = barcode;
        Price = price;
        Amount = amount;
    }
    String getBarcode() {
        return Barcode;
    }
    double getPrice(){
        return Price;
    }
    void setPrice(double price) {
        Price = price;
    }
    int getAmount(){
        return Amount;
    }
    void setAmount(int amount){
        Amount = amount;
    }
    double getSubtotal() {
        return Price * Amount;
    }

}
