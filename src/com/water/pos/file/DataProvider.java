package com.water.pos.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 14-12-2.
 */
public class DataProvider {
    public static List<String> read(String filePath) throws Exception {
        File file = new File(filePath);
        InputStreamReader inputStreamreader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(inputStreamreader);
        List<String> dataList = new ArrayList<String>();
        while (bufferedReader.ready()) {
            dataList.add(bufferedReader.readLine());
        }
        return dataList;
    }
}
