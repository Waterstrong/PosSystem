package com.water.pos.promotion;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class DiscountPromotion implements Promotion {

    private double discountRate;
    public DiscountPromotion(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public Item calculate(final Item item) {
        return new Item(item.getGoods().getBarcode(), item.getGoods().getPrice()*discountRate, item.getAmount());
    }
    @Override
    public String getDescription() {
        return discountRate*10 + "折";
    }
}