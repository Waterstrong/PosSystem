package com.water.pos.file;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 14-12-2.
 */
public class DataProvider {
    public static List<String> read(BufferedReader bufferedReader) throws Exception {
        List<String> dataList = new ArrayList<String>();
        while (bufferedReader.ready()) {
            dataList.add(bufferedReader.readLine());
        }
        return dataList;
    }
}
