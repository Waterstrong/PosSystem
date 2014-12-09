package com.water.pos.market;

import com.water.pos.model.Item;
import com.water.pos.promotion.PromotionStrategy;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by water on 14-11-28.
 */
public class KkPos {
    public void applyPrintSettlement(PromotionStrategy promotionStrategy, ShoppingCart shoppingCart) {
        System.out.println("收银台结算打印小票：");
        System.out.println("购物明细    （数量	单价    小计）");
        Map<String, Item> itemMap = shoppingCart.calculate(promotionStrategy);
        Iterator<String> iter = itemMap.keySet().iterator();
        while (iter.hasNext()) {
            String barcode = iter.next();
            Item item = itemMap.get(barcode);
            System.out.println(barcode + "   " + item.getAmount() + "      " + item.getGoods().getPrice() + "    " + shoppingCart.getSubtotal(barcode));
        }
        double beforePromotion = shoppingCart.getBeforePromotionTotal();
        double afterPromotion = shoppingCart.getAfterPromotionTotal();
        System.out.println("总计金额（优惠前  优惠后  优惠差价）");
        System.out.println(afterPromotion+"    "+beforePromotion+"  "+afterPromotion+"  "+(beforePromotion-afterPromotion));
    }
}
