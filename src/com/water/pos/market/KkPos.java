package com.water.pos.market;

import com.water.pos.model.Item;
import com.water.pos.promotion.PromotionStrategy;

import java.util.Iterator;

/**
 * Created by water on 14-11-28.
 */
public class KkPos {
    public void applyPrintSettlement(PromotionStrategy promotionStrategy, ShoppingCart shoppingCart) {
        System.out.println("收银台结算打印小票：");
        System.out.println("购物明细    （数量	单价    小计）");
        double beforePromotion = 0;
        double afterPromotion = 0;
        Iterator<String> iter = shoppingCart.getCartMap().keySet().iterator();
        while (iter.hasNext()) {
            String barcode = iter.next();
            Item item = shoppingCart.getCartMap().get(barcode);
            beforePromotion += item.getSubtotal();
            Item newItem = promotionStrategy.calculate(item);
            afterPromotion += newItem.getSubtotal();
            System.out.println(item.getGoods().getBarcode() + "   " + item.getAmount() + "      " + item.getGoods().getPrice() + "    " + newItem.getSubtotal());
        }
        Item totalItem = new Item("ITEM_TOTAL", afterPromotion, 1);
        afterPromotion = promotionStrategy.calculate(totalItem).getGoods().getPrice();
        System.out.println("总计金额（优惠前  优惠后  优惠差价）");
        System.out.println(afterPromotion+"    "+beforePromotion+"  "+afterPromotion+"  "+(beforePromotion-afterPromotion));
    }
}
