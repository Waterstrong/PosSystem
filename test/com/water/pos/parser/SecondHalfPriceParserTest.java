package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullAmountDiscountPromotion;
import com.water.pos.promotion.IPromotion;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecondHalfPriceParserTest {
    SecondHalfPriceParser secondHalfPriceParser;

    @Before
    public void setUp() throws Exception {
        secondHalfPriceParser = new SecondHalfPriceParser();
    }

    @Test
    public void should_parse_correctly_when_give_the_second_half_price_data() throws Exception {
        Pair<String, IPromotion> promotionPair = secondHalfPriceParser.parse("ITEM000001");
        String barcode = promotionPair.getKey();
        FullAmountDiscountPromotion fullAmountDiscountPromotion = (FullAmountDiscountPromotion)promotionPair.getValue();

        assertThat(barcode, is("ITEM000001"));
        assertThat(fullAmountDiscountPromotion.getFullAmount(), is(2));
        assertEquals(fullAmountDiscountPromotion.getDiscountRate(), 50, 0.00001);
    }

    @Test
    public void should_get_null_pair_when_give_null_line_string() throws Exception {
        Pair<String, IPromotion> promotionPair = secondHalfPriceParser.parse(null);

        assertNull(promotionPair);
    }
}