package com.water.pos.market;

import com.water.pos.file.*;
import com.water.pos.promotion.PromotionStrategy;
import com.water.pos.parser.DiscountParser;
import com.water.pos.parser.FullCashBackParser;
import com.water.pos.parser.SecondHalfPriceParser;


/**
 * Created by water on 14-11-21.
 */
public class PosSystem {
    public static void main (String[] args) {
        GoodsList goodsList = new GoodsList();
        try {
            goodsList.add(new GoodsFile().getBufferedReader());
        } catch (Exception e) {
            e.printStackTrace();
        }
        goodsList.showDetail();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        try {
            shoppingCart.add(new ShoppingCartFile().getBufferedReader());
        } catch (Exception e) {
            e.printStackTrace();
        }
        shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        try {
            promotionStrategy.attach(new DiscountParser(), new DiscountFile().getBufferedReader());
            promotionStrategy.attach(new SecondHalfPriceParser(), new SecondHalfPriceFile().getBufferedReader());
            promotionStrategy.attach(new FullCashBackParser(), new FullCashBackFile().getBufferedReader());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        promotionStrategy.showPromotionDetail();

        KkPos kkPosA = new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);

    }
}
