package com.water.pos.file;

/**
 * Created by water on 14-12-2.
 */
public class DiscountFile extends FileStream {
    @Override
    protected String getFileName() {
        return "discount_promotion.txt";
    }
}