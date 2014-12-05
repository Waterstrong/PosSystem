package com.water.pos.promotion;

import com.water.pos.model.Item;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FullAmountDiscountPromotionTest {
    @Test
    public void should_calculate_full_amount_discount_correctly_when_given_full_amount_and_discount_rate_args() throws Exception {
        FullAmountDiscountPromotion fullAmountDiscountPromotion = new FullAmountDiscountPromotion(2, 0.5);

        Item item = fullAmountDiscountPromotion.calculate(new Item("123", 50, 5));

        assertThat(item.getSubtotal(), is(50d+25+50+25+50));
    }
}