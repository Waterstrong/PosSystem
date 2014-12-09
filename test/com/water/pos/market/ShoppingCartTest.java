package com.water.pos.market;

import com.water.pos.model.Item;
import com.water.pos.parser.DiscountParser;
import com.water.pos.parser.FullCashBackParser;
import com.water.pos.parser.SecondHalfPriceParser;
import com.water.pos.promotion.PromotionStrategy;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartTest {
    PromotionStrategy promotionStrategy;
    GoodsList goodsList;
    @Before
    public void setUp() throws Exception {
        promotionStrategy = new PromotionStrategy();
        BufferedReader discountReader = mock(BufferedReader.class);
        BufferedReader secondHalfPriceReader = mock(BufferedReader.class);
        BufferedReader fullCashBackReader = mock(BufferedReader.class);
        when(discountReader.readLine()).thenReturn("ITEM000001:75");
        when(discountReader.ready()).thenReturn(true, false);
        when(secondHalfPriceReader.readLine()).thenReturn("ITEM000001");
        when(secondHalfPriceReader.ready()).thenReturn(true, false);
        when(fullCashBackReader.readLine()).thenReturn("ITEM000001:100:5", "ITEM_TOTAL:300:30");
        when(fullCashBackReader.ready()).thenReturn(true, true, false);
        promotionStrategy.attach(new DiscountParser(), discountReader);
        promotionStrategy.attach(new SecondHalfPriceParser(), secondHalfPriceReader);
        promotionStrategy.attach(new FullCashBackParser(), fullCashBackReader);

        goodsList = new GoodsList();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001:80", "ITEM000002:100");
        when(reader.ready()).thenReturn(true, true, false);
        goodsList.add(reader);
    }

    @Test
    public void should_get_the_right_item_when_add_item_into_shopping_cart() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001-3");
        when(reader.ready()).thenReturn(true, false);
        shoppingCart.add(reader);

        Map<String, Item> itemMap = shoppingCart.calculate(promotionStrategy);
        Item item = itemMap.get("ITEM000001");

        assertNotNull(item);
        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getGoods().getPrice(), 80, 0.00001);
        assertThat(item.getAmount(), is(3));
    }

    @Test
    public void should_get_the_right_total_before_promotion() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001");
        when(reader.ready()).thenReturn(true, false);
        shoppingCart.add(reader);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getBeforePromotionTotal(), 80, 0.00001);
    }

    @Test
    public void should_get_the_right_total_after_promotion() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001-5", "ITEM000002");
        when(reader.ready()).thenReturn(true, true, false);
        shoppingCart.add(reader);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getAfterPromotionTotal(), 305, 0.00001);
    }

    @Test
    public void should_get_promotion_subtotal_when_give_the_barcode() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001-5");
        when(reader.ready()).thenReturn(true, false);
        shoppingCart.add(reader);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getSubtotal("ITEM000001"), 235, 0.00001);
    }
}