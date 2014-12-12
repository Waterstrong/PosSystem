package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;
import com.water.pos.promotion.IPromotion;

/**
 * Created by water on 14-11-27.
 */
public class DiscountParser implements IParser<Pair<String, IPromotion>> {
    @Override
    public Pair<String, IPromotion> parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as barcode : discount
        return new Pair<String, IPromotion>(splitResult[0], new DiscountPromotion(Double.parseDouble(splitResult[1])));
    }
}
