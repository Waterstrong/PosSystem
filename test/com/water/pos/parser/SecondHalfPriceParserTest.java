package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullAmountDiscountPromotion;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecondHalfPriceParserTest {
    @Test
    public void should_parse_correctly_when_give_the_second_half_price_data() throws Exception {
/*
        SecondHalfPriceParser secondHalfPriceParser = new SecondHalfPriceParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001");
        List<Pair<String, FullAmountDiscountPromotion>> pairList = secondHalfPriceParser.loadData(reader);
        assertThat(pairList.get(0).getKey(), is("ITEM000001"));
*/
        SecondHalfPriceParser secondHalfPriceParser = new SecondHalfPriceParser();

        Pair<String, FullAmountDiscountPromotion> pair = secondHalfPriceParser.parse("ITEM000001");

        assertThat(pair.getKey(), is("ITEM000001"));

    }
}