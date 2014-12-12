package com.water.pos.parser;

import com.water.pos.model.Goods;

/**
 * Created by water on 14-11-27.
 */


public class GoodsParser implements IParser<Goods> {
    @Override
    public Goods parse(String line) {
        String[] splitResult = line.split(":"); // parse the line as product ( barcode : price )
        return new Goods(splitResult[0], Double.parseDouble(splitResult[1]));
    }
}
