package com.water.pos.parser;

import com.water.pos.model.Goods;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoodsParserTest {

    private GoodsParser goodsParser;

    @Before
    public void setUp() throws Exception {
        goodsParser = new GoodsParser();
    }

    @Test
    public void should_parse_correctly_when_give_the_goods_data() throws Exception {
        Goods goods = goodsParser.parse("ITEM000001:40");

        assertThat(goods.getBarcode(), is("ITEM000001"));
        assertEquals(goods.getPrice(), 40d, 0.00001);
    }

    @Test
    public void should_get_null_goods_when_give_null_line_string() throws Exception {
        Goods goods = goodsParser.parse(null);

        assertNull(goods);
    }

    @Test
    public void should_get_null_goods_when_give_illegal_format_line_string() throws Exception {
        Goods goods = goodsParser.parse("ITEM000001-40");

        assertNull(goods);
    }

    @Test
    public void should_get_null_goods_when_give_negative_price() throws Exception {
        Goods goods = goodsParser.parse("ITEM000001:-1");

        assertNull(goods);
    }

    @Test(expected = NumberFormatException.class)
    public void should_parse_number_format_exception_when_give_string_without_right_number() throws NumberFormatException {
        goodsParser.parse("ITEM000001:4b0");
    }
}