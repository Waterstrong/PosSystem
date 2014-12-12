package com.water.pos.promotion;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class FullAmountDiscountPromotion implements IPromotion {
    private int fullAmount;

    private double discountRate;
    public int getFullAmount() {
        return fullAmount;
    }

    public double getDiscountRate() {
        return discountRate;
    }
    public FullAmountDiscountPromotion(int fullAmount, double discountRate) {
        this.fullAmount = fullAmount;
        this.discountRate = discountRate;
    }
    @Override
    public Item calculate(final Item item) {
        if (fullAmount <= 0 || discountRate < 0 || item == null) return null;

        int discountAmount = item.getAmount() / fullAmount;
        double subtotal = item.getGoods().getPrice() * discountRate/100 * discountAmount + (item.getAmount()-discountAmount)*item.getGoods().getPrice();
        return new Item(item.getGoods().getBarcode(), subtotal/item.getAmount(), item.getAmount());
    }
    @Override
    public String toString() {
        return "第" + fullAmount + "件" + discountRate/10 + "折";
    }
}
