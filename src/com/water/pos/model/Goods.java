package com.water.pos.model;

/**
 * Created by water on 14-11-27.
 */
public class Goods {
    private String barcode;
    private double price;
    public Goods(String barcode, double price) {
        this.barcode = barcode;
        this.price = price;
    }
    public String getBarcode() {
        return barcode;
    }
    public double getPrice() {
        return price;
    }
    public static void printGoodsTitle() {
        System.out.print("商品条码    商品单价");
    }
    public void printGoodsDetail() {
        System.out.print(barcode+"    "+price);
    }
}
