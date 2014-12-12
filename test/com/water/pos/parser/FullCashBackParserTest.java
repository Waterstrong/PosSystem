package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullCashBackPromotion;
import com.water.pos.promotion.IPromotion;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FullCashBackParserTest {
    @Test
    public void should_parse_correctly_when_give_full_cash_back_data() throws Exception {
        FullCashBackParser fullCashBackParser = new FullCashBackParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001:100:5");
        when(reader.ready()).thenReturn(true, false);

        Pair<String, IPromotion> promotionPair = fullCashBackParser.parse("ITEM000001:100:5");
        String barcode = promotionPair.getKey();
        FullCashBackPromotion fullCashBackPromotion = (FullCashBackPromotion)promotionPair.getValue();

        assertThat(barcode, is("ITEM000001"));
        assertEquals(fullCashBackPromotion.getFullCash(), 100d, 0.00001);
        assertEquals(fullCashBackPromotion.getCashBack(), 5d, 0.00001);
    }
}