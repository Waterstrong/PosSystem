package com.water.pos.model;

/**
 * Created by water on 14-11-27.
 */
public class Item {
    private Goods goods;
    private int amount;
    public Item(String barcode, double price, int amount) {
        goods = new Goods(barcode, price);
        this.amount = amount;
    }
    public Goods getGoods() {
        return goods;
    }
    public int getAmount() {
        return amount;
    }
    public double getSubtotal() {
        return amount * goods.getPrice();
    }
//    public String toString() {
//        return goods.toString() + "      " + amount;
//    }
}
