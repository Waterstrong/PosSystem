package com.water.pos.promotion;


import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public interface IPromotion {
    public Item calculate(final Item item);
    public String toString();
}
