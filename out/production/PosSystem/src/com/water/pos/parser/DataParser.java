package com.water.pos.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 14-11-27.
 */
public abstract class DataParser<T> {
    public List<T> loadData(BufferedReader bufferedReader) throws Exception {
        List<T> dataList = new ArrayList<T>();
        while (bufferedReader.ready()) {
            dataList.add(parse(bufferedReader.readLine())); // template method to get the List<V>.
        }
        return dataList;
    }
    protected abstract T parse(String line);
}
