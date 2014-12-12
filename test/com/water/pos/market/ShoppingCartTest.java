package com.water.pos.market;

import com.water.pos.common.Pair;
import com.water.pos.model.Goods;
import com.water.pos.model.Item;
import com.water.pos.promotion.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ShoppingCartTest {
    PromotionStrategy promotionStrategy;
    GoodsList goodsList;
    @Before
    public void setUp() throws Exception {
        promotionStrategy = new PromotionStrategy();
        List<Pair<String, IPromotion>> promotionList = new ArrayList<Pair<String, IPromotion>>();
        promotionList.add(new Pair<String, IPromotion>("ITEM000001",  new DiscountPromotion(75)));
        promotionList.add(new Pair<String, IPromotion>("ITEM000001", new FullAmountDiscountPromotion(2, 50)));
        promotionList.add(new Pair<String, IPromotion>("ITEM000001", new FullCashBackPromotion(100, 5)));
        promotionList.add(new Pair<String, IPromotion>("ITEM_TOTAL", new FullCashBackPromotion(300, 30)));
        promotionStrategy.attach(promotionList);
        goodsList = new GoodsList();
        List<Goods> goodsArray = new ArrayList<Goods>();
        goodsArray.add(new Goods("ITEM000001", 80));
        goodsArray.add(new Goods("ITEM000002", 100));
        goodsList.add(goodsArray);
    }

    @Test
    public void should_get_the_right_item_when_add_item_into_shopping_cart() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("ITEM000001", 0, 3));
        shoppingCart.add(itemList);

        Map<String, Item> itemMap = shoppingCart.calculate(promotionStrategy);
        Item item = itemMap.get("ITEM000001");

        assertNotNull(item);
        assertThat(item.getGoods().getBarcode(), is("ITEM000001"));
        assertEquals(item.getGoods().getPrice(), 80, 0.00001);
        assertThat(item.getAmount(), is(3));
    }

    @Test
    public void should_get_the_right_total_before_promotion() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("ITEM000001", 0, 1 ));
        shoppingCart.add(itemList);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getBeforePromotionTotal(), 80, 0.00001);
    }

    @Test
    public void should_get_the_right_total_after_promotion() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("ITEM000001", 0, 5 ));
        itemList.add(new Item("ITEM000002", 0, 1));
        shoppingCart.add(itemList);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getAfterPromotionTotal(), 305, 0.00001);
    }

    @Test
    public void should_get_promotion_subtotal_when_give_the_barcode() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("ITEM000001", 0, 5 ));
        shoppingCart.add(itemList);

        shoppingCart.calculate(promotionStrategy);

        assertEquals(shoppingCart.getSubtotal("ITEM000001"), 235, 0.00001);
    }
}