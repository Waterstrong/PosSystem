package com.water.pos.promotion;

import com.water.pos.common.Pair;
import com.water.pos.market.GoodsList;
import com.water.pos.model.Item;
import com.water.pos.parser.DiscountParser;
import com.water.pos.parser.FullCashBackParser;
import com.water.pos.parser.SecondHalfPriceParser;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class PromotionStrategyTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should_calculate_subtotal_correctly_when_many_promotions_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        List<Pair<String, IPromotion>> promotionList = new ArrayList<Pair<String, IPromotion>>();
        promotionList.add(new Pair<String, IPromotion>("ITEM000001", new DiscountPromotion(75)));
        promotionList.add(new Pair<String, IPromotion>("ITEM000001", new FullAmountDiscountPromotion(2, 50)));
        promotionList.add(new Pair<String, IPromotion>("ITEM000001", new FullCashBackPromotion(100, 5)));
        promotionStrategy.attach(promotionList);

        Item item = promotionStrategy.calculate(new Item("ITEM000001", 50, 5));

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getSubtotal(), 145d, 0.00001);
    }
}