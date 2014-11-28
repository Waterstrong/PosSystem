package ws.pos.parser;

import ws.pos.common.Pair;
import ws.pos.promotion.DiscountPromotion;

/**
 * Created by water on 14-11-27.
 */
public class DiscountParser extends DataParser<Pair<String, DiscountPromotion>> {
    @Override
    public Pair<String, DiscountPromotion> parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as barcode : discount
        return new Pair<String, DiscountPromotion>(splitResult[0], new DiscountPromotion(Double.parseDouble(splitResult[1])/100));
    }
}
