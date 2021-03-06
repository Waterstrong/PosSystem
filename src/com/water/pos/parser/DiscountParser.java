package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;
import com.water.pos.promotion.IPromotion;
import sun.util.locale.provider.SPILocaleProviderAdapter;

/**
 * Created by water on 14-11-27.
 */
public class DiscountParser implements IParser<Pair<String, IPromotion>> {
    @Override
    public Pair<String, IPromotion> parse(String line) {
        if (line == null) return null;
        String[] splitResult = line.split(":"); // parse the line as barcode : discount
        if (splitResult.length != 2) return null;
        double discountRate = Double.parseDouble(splitResult[1]);
        if (discountRate < 0 || discountRate > 100) return null;
        return new Pair<String, IPromotion>(splitResult[0], new DiscountPromotion(discountRate));
    }
}
