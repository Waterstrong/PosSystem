package com.water.pos.parser;

import com.water.pos.model.Item;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartParserTest {
    @Test
    public void should_parse_correctly_when_give_the_shopping_cart_data() throws Exception {
        /*ShoppingCartParser shoppingCartParser = new ShoppingCartParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001-3").thenReturn("ITEM000005");

        List<Item> itemList = shoppingCartParser.loadData(reader);

        assertThat(itemList.get(0).getGoods().getBarcode(), is("ITEM000001"));
        assertThat(itemList.get(0).getAmount(), is(3));
        assertThat(itemList.get(1).getGoods().getBarcode(), is("ITEM000005"));
        assertThat(itemList.get(1).getAmount(), is(1));
*/

        ShoppingCartParser shoppingCartParser = new ShoppingCartParser();

        Item item1 = shoppingCartParser.parse("ITEM000001-3");
        Item item2 = shoppingCartParser.parse("ITEM000005");

        assertThat(item1.getGoods().getBarcode(), is("ITEM000001"));
        assertThat(item1.getAmount(), is(3));
        assertThat(item2.getGoods().getBarcode(), is("ITEM000005"));
        assertThat(item2.getAmount(), is(1));

    }
}