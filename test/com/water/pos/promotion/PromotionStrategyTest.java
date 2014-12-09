package com.water.pos.promotion;

import com.water.pos.model.Item;
import com.water.pos.parser.DiscountParser;
import com.water.pos.parser.FullCashBackParser;
import com.water.pos.parser.SecondHalfPriceParser;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class PromotionStrategyTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should_calculate_subtotal_correctly_when_many_promotions_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        BufferedReader discountReader = mock(BufferedReader.class);
        BufferedReader secondHalfPriceReader = mock(BufferedReader.class);
        BufferedReader fullCashBackReader = mock(BufferedReader.class);
        when(discountReader.readLine()).thenReturn("ITEM000001:75");
        when(discountReader.ready()).thenReturn(true, false);
        when(secondHalfPriceReader.readLine()).thenReturn("ITEM000001");
        when(secondHalfPriceReader.ready()).thenReturn(true, false);
        when(fullCashBackReader.readLine()).thenReturn("ITEM000001:100:5");
        when(fullCashBackReader.ready()).thenReturn(true, false);
        promotionStrategy.attach(new DiscountParser(), discountReader);
        promotionStrategy.attach(new SecondHalfPriceParser(), secondHalfPriceReader);
        promotionStrategy.attach(new FullCashBackParser(), fullCashBackReader);

        Item item = promotionStrategy.calculate(new Item("ITEM000001", 50, 5));

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getSubtotal(), 145d, 0.00001);
    }
}