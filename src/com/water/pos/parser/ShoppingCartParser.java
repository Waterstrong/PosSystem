package com.water.pos.parser;

import com.water.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public class ShoppingCartParser implements IParser<Item> {
    @Override
    public Item parse(String line) {
        if (line == null) return null;
        String[] splitResult = line.split("-"); // parse the line as barcode : amount
        if (splitResult.length != 1 && splitResult.length != 2) return null;
        Integer amount = 1;
        if (splitResult.length == 2) {
            amount = Integer.parseInt(splitResult[1]);
            if (amount <= 0) return null;
        }
        return new Item(splitResult[0], 0, amount);
    }
}
