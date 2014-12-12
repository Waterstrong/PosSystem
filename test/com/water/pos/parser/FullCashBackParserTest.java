package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullCashBackPromotion;
import com.water.pos.promotion.IPromotion;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FullCashBackParserTest {

    private FullCashBackParser fullCashBackParser;

    @Before
    public void setUp() throws Exception {
        fullCashBackParser = new FullCashBackParser();
    }

    @Test
    public void should_parse_correctly_when_give_full_cash_back_data() throws Exception {
        Pair<String, IPromotion> promotionPair = fullCashBackParser.parse("ITEM000001:100:5");
        String barcode = promotionPair.getKey();
        FullCashBackPromotion fullCashBackPromotion = (FullCashBackPromotion)promotionPair.getValue();

        assertThat(barcode, is("ITEM000001"));
        assertEquals(fullCashBackPromotion.getFullCash(), 100d, 0.00001);
        assertEquals(fullCashBackPromotion.getCashBack(), 5d, 0.00001);
    }

    @Test
    public void should_get_null_pair_when_give_null_line_string() throws Exception {
        Pair<String, IPromotion> promotionPair = fullCashBackParser.parse(null);

        assertNull(promotionPair);
    }

    @Test
    public void should_get_null_pair_when_give_illegal_format_string() throws Exception {
        Pair<String, IPromotion> promotionPair = fullCashBackParser.parse("ITEM000001:105");

        assertNull(promotionPair);
    }

    @Test(expected = NumberFormatException.class)
    public void should_parse_exception_when_give_string_without_right_number() throws NumberFormatException {
        fullCashBackParser.parse("ITEM000001:100x:y5");
    }
}