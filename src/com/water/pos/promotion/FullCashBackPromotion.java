package com.water.pos.promotion;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class FullCashBackPromotion implements Promotion {
    private double fullCash;
    private double cashBack;
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
    public String getDescription() {
        return "満" + fullCash + "返" + cashBack;
    }
}
