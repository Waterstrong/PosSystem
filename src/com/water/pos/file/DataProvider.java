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
    private static final String FILE_COMMON_PATH = "/home/water/Projects/PosSystem/data/";
    public static List<String> importData(IFileStream fileStream) throws Exception {
        File file = new File(FILE_COMMON_PATH + fileStream.getFileName());
        InputStreamReader inputStreamreader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(inputStreamreader);
        List<String> dataList = new ArrayList<String>();
        while (bufferedReader.ready()) {
            dataList.add(bufferedReader.readLine());
        }
        return dataList;
    }
}
