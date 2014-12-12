package com.water.pos.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void should_get_subtotal_when_give_item_args() throws Exception {
        Item item = new Item("ITEM000001", 150.5, 2);

        double subtotal = item.getSubtotal();

        assertEquals(subtotal, 150.5*2, 0.00001);
    }
}