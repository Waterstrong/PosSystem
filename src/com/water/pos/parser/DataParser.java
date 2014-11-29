package com.water.pos.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 14-11-27.
 */
public abstract class DataParser<T> {
    private static final String FILE_COMMON_PATH = "/home/water/Projects/PosSystem/data/";
    public List<T> loadFromFile(String fileName) throws Exception {
        File file = new File(FILE_COMMON_PATH + fileName);
        InputStreamReader inputStreamreader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(inputStreamreader);
        List<T> dataList = new ArrayList<T>();
        while (bufferedReader.ready()) {
            dataList.add(parse(bufferedReader.readLine())); // template method to get the List<V>.
        }
        return dataList;
    }
    protected abstract T parse(String line);
}
