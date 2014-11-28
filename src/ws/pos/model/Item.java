package ws.pos.model;

/**
 * Created by water on 14-11-27.
 */
public class Item extends Goods {
    private int amount;
    public Item(String barcode, double price, int amount) {
        super(barcode, price);
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
    public double getSubtotal() {
        return amount * getPrice();
    }
    public static void printItemTitle() {
        printGoodsTitle();
        System.out.print("    购买数量");
    }
    public void printItemDetail() {
        printGoodsDetail();
        System.out.print("      " + amount);
    }
}
