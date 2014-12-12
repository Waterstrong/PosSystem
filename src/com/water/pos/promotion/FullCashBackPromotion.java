package com.water.pos.promotion;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class FullCashBackPromotion implements IPromotion {
    private double fullCash;

    private double cashBack;

    public double getFullCash() {
        return fullCash;
    }

    public double getCashBack() {
        return cashBack;
    }
    public FullCashBackPromotion(double fullCash, double cashBack) {
        this.fullCash = fullCash;
        this.cashBack = cashBack;
    }
    @Override
    public Item calculate(Item item) {
        double subtotal = item.getSubtotal();
        if (subtotal >= fullCash) {
            subtotal -= cashBack;
        }
        return new Item(item.getGoods().getBarcode(), subtotal / item.getAmount(), item.getAmount());
    }
    @Override
    public String toString() {
        return "満" + fullCash + "返" + cashBack;
    }
}
