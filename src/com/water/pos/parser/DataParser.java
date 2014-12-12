package com.water.pos.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 14-11-27.
 */
public class DataParser {
    public static <O> List<O> map(List<String> lines, IParser<O> parser) throws Exception {
        List<O> dataList = new ArrayList<O>();
        for (String line : lines) {
            O data = parser.parse(line);
            if (data == null) return null;
            dataList.add(data);
        }
        return dataList;
    }
}
