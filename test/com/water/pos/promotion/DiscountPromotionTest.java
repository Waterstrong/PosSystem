package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DiscountPromotionTest {

    Item globalItem;

    @Before
    public void setUp() throws Exception {
        globalItem = new Item("ITEM000001", 128.5, 1);
    }

    @Test
    public void should_calculate_method_calculate_discount_correctly_when_give_a_discount_rate() throws Exception {
        DiscountPromotion discountPromotion = new DiscountPromotion(80);

        Item item = discountPromotion.calculate(globalItem);

        assertNotNull(item);
        assertEquals(item.getSubtotal(), 128.5*0.8, 0.00001);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_negative_discount_rate() throws Exception {
        DiscountPromotion discountPromotion = new DiscountPromotion(-1);

        Item item = discountPromotion.calculate(globalItem);

        assertNull(item);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_null_item() throws Exception {
        DiscountPromotion discountPromotion = new DiscountPromotion(80);

        Item item = discountPromotion.calculate(null);

        assertNull(item);

    }
}