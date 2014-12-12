package com.water.pos.parser;

/**
 * Created by water on 14-12-10.
 */
public interface IParser<O> {
    O parse(String line);
}
