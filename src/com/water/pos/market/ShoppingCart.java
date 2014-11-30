package com.water.pos.market;

import com.water.pos.model.Item;
import com.water.pos.parser.ShoppingCartParser;
import com.water.pos.promotion.PromotionStrategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by water on 14-11-27.
 */
public class ShoppingCart {
    private Map<String, Item> cartMap = new HashMap<String, Item>();
    private GoodsList goodsList = null;
    public ShoppingCart(GoodsList goodsList) {
        this.goodsList = goodsList;
        init();
    }
    private void init() {
        try {
            List<Item> itemList = new ShoppingCartParser().loadFromFile("cart.txt");
            Iterator<Item> iter = itemList.iterator();
            while (iter.hasNext()) {
                Item item = iter.next();
                Item existItem = cartMap.get(item.getGoods().getBarcode());
                int newAount = item.getAmount() + (existItem == null ? 0 : existItem.getAmount());
                Double price = goodsList.getGoods(item.getGoods().getBarcode()).getPrice();
                Item newItem = new Item(item.getGoods().getBarcode(), price, newAount);
                cartMap.put(item.getGoods().getBarcode(), newItem);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Map<String, Item> getCartMap() {
        return cartMap;
    }
    public void showDetail() {
        Iterator<String> iter = cartMap.keySet().iterator();
        System.out.println("购物车信息：");
        System.out.println("商品条码    商品单价    购买数量");
        while (iter.hasNext()) {
            String barcode = iter.next();
            Item item = cartMap.get(barcode);
            System.out.println(item.toString());
        }
    }


}
