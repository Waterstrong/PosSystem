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
        if (line == null) return null;
        String[] splitResult = line.split(":"); // parse the line as barcode : fullCash : backCash
        if (splitResult.length != 3) return null;
        double fullCash = Double.parseDouble(splitResult[1]);
        double cashBack = Double.parseDouble(splitResult[2]);
        if (fullCash <= 0 || cashBack < 0) return null;
        return new Pair<String, IPromotion>(splitResult[0],
                new FullCashBackPromotion(fullCash, cashBack));
    }
}
