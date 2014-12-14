package com.water.pos.promotion;

import com.water.pos.common.Pair;
import com.water.pos.model.Item;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PromotionStrategyTest {

    private Item item1;

    @Before
    public void setUp() throws Exception {

        item1 = new Item("ITEM000001", 50, 5);
    }

    @Test
    public void should_calculate_subtotal_correctly_when_many_promotions_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        List<Pair<String, IPromotion>> promotionPairList = new ArrayList<Pair<String, IPromotion>>();
        //IPromotion promotion = mock(IPromotion.class);
        //given(promotion.calculate(any(Item.class))).willReturn();
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", new DiscountPromotion(75))); //
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", new FullAmountDiscountPromotion(2, 50))); //
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", new FullCashBackPromotion(100, 5))); //
        promotionStrategy.attach(promotionPairList);

        Item item = promotionStrategy.calculate(item1);

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getSubtotal(), 145d, 0.00001);
    }

    @Test
    public void should_get_the_no_changed_item_when_no_promotion_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();

        Item item = promotionStrategy.calculate(item1);

        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getSubtotal(), 250, 0.00001);
    }

    @Test
    public void should_calculate_get_null_item_when_given_one_illegal_promotion() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        List<Pair<String, IPromotion>> promotionPairList = new ArrayList<Pair<String, IPromotion>>();
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", new DiscountPromotion(75)));
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", new FullAmountDiscountPromotion(-2, -50)));
        promotionStrategy.attach(promotionPairList);

        Item item = promotionStrategy.calculate(item1);

        assertNull(item);
    }

    @Test
    public void should_calculate_get_null_item_when_given_the_null_item() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();

        Item item = promotionStrategy.calculate(null);

        assertNull(item);
    }

    @Test
    public void should_get_exactly_goods_promotions_when_many_promotions_given() throws Exception {
        PromotionStrategy promotionStrategy = new PromotionStrategy();
        List<Pair<String, IPromotion>> promotionPairList = new ArrayList<Pair<String, IPromotion>>();
        DiscountPromotion discountPromotion = new DiscountPromotion(75);
        FullCashBackPromotion fullCashBackPromotion = new FullCashBackPromotion(100, 5);
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", discountPromotion));
        promotionPairList.add(new Pair<String, IPromotion>("ITEM000001", fullCashBackPromotion));
        promotionStrategy.attach(promotionPairList);

        List<IPromotion> promotionArray = promotionStrategy.getPromotionsOfGoods("ITEM000001");

        assertThat(promotionArray.get(0), Is.<IPromotion>is(discountPromotion));
        assertThat(promotionArray.get(1), Is.<IPromotion>is(fullCashBackPromotion));

    }
}