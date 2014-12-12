package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullAmountDiscountPromotion;
import com.water.pos.promotion.IPromotion;

/**
 * Created by water on 14-11-27.
 */
public class SecondHalfPriceParser implements IParser<Pair<String, IPromotion>> {
    @Override
    public Pair<String, IPromotion> parse(String line) {
        return new Pair<String, IPromotion>(line, new FullAmountDiscountPromotion(2, 0.5));
    }
}
