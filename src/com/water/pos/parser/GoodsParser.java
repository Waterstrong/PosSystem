package com.water.pos.parser;

import com.water.pos.model.Goods;

/**
 * Created by water on 14-11-27.
 */


public class GoodsParser implements IParser<Goods> {
    @Override
    public Goods parse(String line) {
        if (line == null) return null;
        String[] splitResult = line.split(":"); // parse the line as product ( barcode : price )
        if (splitResult.length != 2) return null;
        return new Goods(splitResult[0], Double.parseDouble(splitResult[1]));
    }
}
