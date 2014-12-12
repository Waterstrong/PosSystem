package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;
import com.water.pos.promotion.IPromotion;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DiscountParserTest {

    @Test
    public void should_parse_successfully_when_give_a_discount_format_string() throws Exception {
        DiscountParser discountParser = new DiscountParser();

        Pair<String, IPromotion> promotionPair = discountParser.parse("ITEM000001:75");
        String barcode = promotionPair.getKey();
        DiscountPromotion discountPromotion = (DiscountPromotion)promotionPair.getValue();

        assertThat(barcode, is("ITEM000001"));
        assertEquals(discountPromotion.getDiscountRate(), 75d, 0.00001);
    }
}