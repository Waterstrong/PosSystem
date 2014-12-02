package com.water.pos.file;

/**
 * Created by water on 14-12-2.
 */
public class ShoppingCartFile extends FileStream {
    @Override
    protected String getFileName() {
        return "cart.txt";
    }
}
