package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;

/**
 * Created by water on 14-11-27.
 */
public class DiscountParser extends DataParser<Pair<String, DiscountPromotion>> {
    @Override
    protected Pair<String, DiscountPromotion> parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as barcode : discount
        return new Pair<String, DiscountPromotion>(splitResult[0], new DiscountPromotion(Double.parseDouble(splitResult[1])/100));
    }
}
