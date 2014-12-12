package com.water.pos.file;

/**
 * Created by water on 14-12-2.
 */
public class ShoppingCartFile implements IFileStream {
    @Override
    public String getFileName() {
        return "cart.txt";
    }
}
