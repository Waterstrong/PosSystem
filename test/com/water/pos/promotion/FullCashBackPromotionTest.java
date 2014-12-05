package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FullCashBackPromotionTest {

    @Test
    public void should_cash_back_correctly_when_reach_expect_cash() throws Exception {
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(300, 30);

        Item item = fullCashBackPromotion.calculate(new Item("123", 300, 1));

        assertThat(item.getSubtotal(), is(300d-30));

    }
}