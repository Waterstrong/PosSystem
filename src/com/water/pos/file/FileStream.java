package com.water.pos.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by water on 14-12-2.
 */
public abstract class FileStream {
    private static final String FILE_COMMON_PATH = "/home/water/Projects/PosSystem/data/";
    protected abstract String getFileName();
    public BufferedReader getBufferedReader() throws Exception {
        File file = new File(FILE_COMMON_PATH + getFileName());
        InputStreamReader inputStreamreader = new InputStreamReader(new FileInputStream(file));
        return new BufferedReader(inputStreamreader);
    }
}
