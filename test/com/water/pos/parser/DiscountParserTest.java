package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;
import com.water.pos.promotion.IPromotion;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DiscountParserTest {

    private DiscountParser discountParser;

    @Before
    public void setUp() throws Exception {
        discountParser = new DiscountParser();
    }

    @Test
    public void should_parse_successfully_when_give_a_discount_format_string() throws Exception {
        Pair<String, IPromotion> promotionPair = discountParser.parse("ITEM000001:75");
        String barcode = promotionPair.getKey();
        DiscountPromotion discountPromotion = (DiscountPromotion)promotionPair.getValue();

        assertThat(barcode, is("ITEM000001"));
        assertEquals(discountPromotion.getDiscountRate(), 75d, 0.00001);
    }

    @Test
    public void should_get_null_pair_when_give_illegal_format_string() throws Exception {
        Pair<String, IPromotion> promotionPair = discountParser.parse("ITEM000001-75");

        assertNull(promotionPair);
    }

    @Test
    public void should_get_null_pair_when_give_null_string() throws Exception {
        Pair<String, IPromotion> promotionPair = discountParser.parse(null);

        assertNull(promotionPair);
    }

    @Test(expected = NumberFormatException.class)
    public void should_get_number_format_exception_when_give_format_string_without_right_number_part() throws NullPointerException {
        discountParser.parse("ITEM000001:y75x");
    }

    @Test
    public void should_get_null_pair_when_give_negative_discount() throws Exception {
        Pair<String, IPromotion> promotionPair = discountParser.parse("ITEM000001:-1");

        assertNull(promotionPair);
    }

    @Test
    public void should_get_null_pair_when_give_disount_greater_than_one_hundred() throws Exception {
        Pair<String, IPromotion> promotionPair = discountParser.parse("ITEM000001:101");

        assertNull(promotionPair);
    }
}