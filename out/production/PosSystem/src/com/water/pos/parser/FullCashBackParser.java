package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullCashBackPromotion;
import com.water.pos.promotion.IPromotion;

/**
 * Created by water on 14-11-27.
 */
public class FullCashBackParser implements IParser<Pair<String, IPromotion>> {
    @Override
    public Pair<String, IPromotion> parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as barcode : fullCash : backCash
        return new Pair<String, IPromotion>(splitResult[0],
                new FullCashBackPromotion(Double.parseDouble(splitResult[1]), Double.parseDouble(splitResult[2])));
    }
}
