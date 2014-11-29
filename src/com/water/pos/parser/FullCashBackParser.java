package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullCashBackPromotion;

/**
 * Created by water on 14-11-27.
 */
public class FullCashBackParser extends DataParser<Pair<String, FullCashBackPromotion>> {
    @Override
    public Pair<String, FullCashBackPromotion> parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as barcode : fullCash : backCash
        return new Pair<String, FullCashBackPromotion>(splitResult[0],
                new FullCashBackPromotion(Double.parseDouble(splitResult[1]), Double.parseDouble(splitResult[2])));
    }
}
