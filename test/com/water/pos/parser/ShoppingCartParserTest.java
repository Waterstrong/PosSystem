package com.water.pos.parser;

import com.water.pos.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartParserTest {
    ShoppingCartParser shoppingCartParser;

    @Before
    public void setUp() throws Exception {
        shoppingCartParser = new ShoppingCartParser();
    }

    @Test
    public void should_parse_correctly_when_give_the_shopping_cart_data_with_amount() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001-3");
        when(reader.ready()).thenReturn(true, false);

        List<Item> itemList = shoppingCartParser.loadData(reader);

        assertThat(itemList.get(0).getGoods().getBarcode(), is("ITEM000001"));
        assertThat(itemList.get(0).getAmount(), is(3));
    }

    @Test
    public void should_parse_correctly_when_give_the_shopping_cart_data_without_amount() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000005");
        when(reader.ready()).thenReturn(true, false);

        List<Item> itemList = shoppingCartParser.loadData(reader);

        assertThat(itemList.get(0).getGoods().getBarcode(), is("ITEM000005"));
        assertThat(itemList.get(0).getAmount(), is(1));
    }
}