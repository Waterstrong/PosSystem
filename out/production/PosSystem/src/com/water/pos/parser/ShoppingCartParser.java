package com.water.pos.parser;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class ShoppingCartParser extends DataParser<Item> {
    @Override
    protected Item parse(String line) {
        String[] splitResult = line.split("-"); // parse the line as barcode : amount
        Integer amount = 1;
        if (splitResult.length > 1) {
            amount = Integer.parseInt(splitResult[1]);
        }
        return new Item(splitResult[0], 0, amount);
    }
}
