package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FullAmountDiscountPromotionTest {
    Item globalItem;
    @Before
    public void setUp() throws Exception {
        globalItem = new Item("ITEM000001", 50, 5);
    }

    @Test
    public void should_calculate_promotion_correctly_when_given_full_amount_and_discount_rate_args() throws Exception {
        FullAmountDiscountPromotion fullAmountDiscountPromotion = new FullAmountDiscountPromotion(2, 50);

        Item item = fullAmountDiscountPromotion.calculate(globalItem);

        assertEquals(item.getSubtotal(), 50d+25+50+25+50, 0.00001);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_negative_discount_rate() throws Exception {
        FullAmountDiscountPromotion fullAmountDiscountPromotion = new FullAmountDiscountPromotion(2, -1);

        Item item = fullAmountDiscountPromotion.calculate(globalItem);

        assertNull(item);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_illegal_full_amount() throws Exception {
        FullAmountDiscountPromotion fullAmountDiscountPromotion = new FullAmountDiscountPromotion(0, 50);

        Item item = fullAmountDiscountPromotion.calculate(globalItem);

        assertNull(item);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_null_item() throws Exception {
        FullAmountDiscountPromotion fullAmountDiscountPromotion = new FullAmountDiscountPromotion(2, 50);

        Item item = fullAmountDiscountPromotion.calculate(null);

        assertNull(item);

    }
}