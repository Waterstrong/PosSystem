package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FullCashBackPromotionTest {

    Item globalItem;

    @Before
    public void setUp() throws Exception {
        globalItem = new Item("ITEM000001", 300, 1);
    }

    @Test
    public void should_cash_back_correctly_when_reach_expect_cash() throws Exception {
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(300, 30);

        Item item = fullCashBackPromotion.calculate(globalItem);

        assertEquals(item.getSubtotal(), 300d-30, 0.00001);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_negative_full_cash_arg() throws Exception {
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(-300, 30);

        Item item = fullCashBackPromotion.calculate(globalItem);

        assertNull(item);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_negative_cash_back_arg() throws Exception {
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(300, -30);

        Item item = fullCashBackPromotion.calculate(globalItem);

        assertNull(item);

    }

    @Test
    public void should_calculate_method_get_null_item_when_given_null_item() throws Exception {
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(300, 30);

        Item item = fullCashBackPromotion.calculate(null);

        assertNull(item);

    }
}