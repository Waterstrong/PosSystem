package com.water.pos.parser;

import com.water.pos.common.Pair;
import com.water.pos.promotion.FullCashBackPromotion;
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

       /* FullCashBackParser fullCashBackParser = new FullCashBackParser();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ITEM000001:100:5");
        List<Pair<String, FullCashBackPromotion>> pairList = fullCashBackParser.loadData(reader);
        String barcode = pairList.get(0).getKey();
        FullCashBackPromotion fullCashBackPromotion = pairList.get(0).getValue();

        assertThat(barcode, is("ITEM000001"));
        assertThat(fullCashBackPromotion.getFullCash(), is(100d));
        assertThat(fullCashBackPromotion.getCashBack(), is(5d));
        */

        FullCashBackParser fullCashBackParser = new FullCashBackParser();
        Pair<String, FullCashBackPromotion> pair = fullCashBackParser.parse("ITEM000001:100:5");
        assertThat(pair.getKey(), is("ITEM000001"));
        assertThat(pair.getValue().getFullCash(), is(100d));
        assertThat(pair.getValue().getCashBack(), is(5d));

    }
}