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

    BufferedReader discountReader;
    BufferedReader secondHalfPriceReader;
    BufferedReader fullCashBackReader;

    @Before
    public void setUp() throws Exception {
        discountReader = mock(BufferedReader.class);
        secondHalfPriceReader = mock(BufferedReader.class);
        fullCashBackReader = mock(BufferedReader.class);
        when(discountReader.readLine()).thenReturn("ITEM000001:75");
        when(secondHalfPriceReader.readLine()).thenReturn("ITEM000001");
        when(fullCashBackReader.readLine()).thenReturn("ITEM000001:100:5");
    }

    @Test
    public void should_promotion_caculate_correctly_when_many_promotions_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        promotionStrategy.attach(new DiscountParser(), discountReader);
        promotionStrategy.attach(new SecondHalfPriceParser(), secondHalfPriceReader);
        promotionStrategy.attach(new FullCashBackParser(), fullCashBackReader);

        Item item = promotionStrategy.calculate(new Item("ITEM000001", 50, 5));

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertThat(item.getSubtotal(), is(145d));

    }
}