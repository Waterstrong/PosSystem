package com.water.pos.parser;

import com.water.pos.model.Goods;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoodsParserTest {
    @Test
    public void should_parse_correctly_when_give_the_goods_data() throws Exception {
        GoodsParser goodsParser = new GoodsParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001:40");
        when(reader.ready()).thenReturn(true, false);

        List<Goods> goodsList = goodsParser.loadData(reader);
        Goods goods = goodsList.get(0);

        assertThat(goods.getBarcode(), is("ITEM000001"));
        assertEquals(goods.getPrice(), 40d, 0.00001);
    }
}