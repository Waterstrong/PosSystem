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
        Item item = shoppingCartParser.parse("ITEM000001-3");

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertThat(item.getAmount(), is(3));
    }

    @Test
    public void should_parse_correctly_when_give_the_shopping_cart_data_without_amount() throws Exception {
        Item item = shoppingCartParser.parse("ITEM000005");

        assertThat(item.getGoods().getBarcode(), is("ITEM000005"));
        assertThat(item.getAmount(), is(1));
    }

    @Test
    public void should_get_null_item_when_give_null_line_string() throws Exception {
        Item item = shoppingCartParser.parse(null);

        assertNull(item);
    }

    @Test(expected = NumberFormatException.class)
    public void should_parse_number_format_exception_when_give_string_without_right_number() throws NumberFormatException {
        shoppingCartParser.parse("ITEM000005-3x");
    }

    @Test
    public void should_get_null_item_when_give_illegal_amount() throws Exception {
        Item item = shoppingCartParser.parse("ITEM000005-0");

        assertNull(item);
    }

    @Test
    public void should_get_null_item_when_give_illegal_line_string() throws Exception {
        Item item = shoppingCartParser.parse("ITEM000005-10-10");

        assertNull(item);
    }
}