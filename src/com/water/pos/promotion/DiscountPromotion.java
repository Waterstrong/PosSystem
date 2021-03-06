package com.water.pos.promotion;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class DiscountPromotion implements IPromotion {
    private double discountRate;

    public double getDiscountRate() {
        return discountRate;
    }
    public DiscountPromotion(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public Item calculate(final Item item) {
        if (discountRate < 0 || item == null) return null;
        return new Item(item.getGoods().getBarcode(), item.getGoods().getPrice()*discountRate/100, item.getAmount());
    }
    @Override
    public String toString() {
        return discountRate/10 + "折";
    }
}
