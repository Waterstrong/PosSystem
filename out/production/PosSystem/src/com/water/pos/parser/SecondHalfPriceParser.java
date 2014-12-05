package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullAmountDiscountPromotion;

/**
 * Created by water on 14-11-27.
 */
public class SecondHalfPriceParser extends DataParser<Pair<String, FullAmountDiscountPromotion>> {
    @Override
    protected Pair<String, FullAmountDiscountPromotion> parse(String line) {
        return new Pair<String, FullAmountDiscountPromotion>(line, new FullAmountDiscountPromotion(2, 0.5));
    }
}
