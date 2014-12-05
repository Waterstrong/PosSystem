package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.DiscountPromotion;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class DiscountParserTest {

    @Test
    public void should_parse_successfully_when_give_a_discount_format_string() throws Exception {
        /*DataParser<Pair<String, DiscountPromotion>> parser = new DiscountParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001:75");

        List<Pair<String, DiscountPromotion>> pairList = parser.loadData(reader);
        String barcode = pairList.get(0).getKey();
        DiscountPromotion discountPromotion = pairList.get(0).getValue();
*/
        //assertThat(barcode, is("ITEM000001"));
       // assertTrue(discountPromotion.equals(new DiscountPromotion(75)));

        DiscountParser discountParser = new DiscountParser();
        Pair<String, DiscountPromotion> pair = discountParser.parse("ITEM000001:75");

        assertThat(pair.getKey(), is("ITEM000001"));

        assertThat(pair.getValue().getDiscountRate(), is(75d));
    }
}