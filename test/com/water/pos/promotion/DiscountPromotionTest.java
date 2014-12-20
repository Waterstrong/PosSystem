package com.water.pos.promotion;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.water.pos.injector.MyModule;
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
        //Injector injector = Guice.createInjector(new MyModule());

        //IPromotion discountPromotion = injector.getInstance(IPromotion.class);

        IPromotion promotion = new DiscountPromotion(80);

        Item item = promotion.calculate(globalItem);

        assertEquals(item.getSubtotal(), 128.5*0.8, 0.00001);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_negative_discount_rate() throws Exception {
        IPromotion promotion =  new DiscountPromotion(-1);

        Item item = promotion.calculate(globalItem);

        assertNull(item);
    }

    @Test
    public void should_calculate_method_get_null_item_when_given_the_null_item() throws Exception {
        IPromotion promotion =  new DiscountPromotion(80);

        Item item = promotion.calculate(null);

        assertNull(item);

    }
}