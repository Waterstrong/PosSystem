package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DiscountPromotionTest {

    @Test
    public void should_calculate_discount_rightly_when_give_a_discount_rate() throws Exception {
        DiscountPromotion discountPromotion = new DiscountPromotion(0.8);

        Item item = discountPromotion.calculate(new Item("123", 128.5, 1));

        assertThat(item.getSubtotal(), is(128.5*0.8));
    }
}