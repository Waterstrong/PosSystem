package com.water.pos.market;

import com.water.pos.model.Goods;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoodsListTest {

    @Test
    public void should_add_goods_to_list_successfully_when_given_goods_array() throws Exception {
        GoodsList goodsList = new GoodsList();
        List<Goods> goodsCollection = new ArrayList<Goods>();
        goodsCollection.add(new Goods("ITEM000001", 40));

        goodsList.add(goodsCollection);
        Goods goods = goodsList.getGoods("ITEM000001");

        assertNotNull(goods);
        assertThat(goods.getBarcode(), is("ITEM000001"));
        assertEquals(goods.getPrice(), 40d, 0.00001);
    }

    @Test
    public void should_get_null_goods_when_barcode_is_not_in_the_goods_list() throws Exception {
        GoodsList goodsList = new GoodsList();

        Goods goods = goodsList.getGoods("ITEM000001");

        assertNull(goods);
    }
}