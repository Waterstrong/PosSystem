package com.water.pos.market;

import com.water.pos.file.*;
import com.water.pos.parser.*;
import com.water.pos.promotion.PromotionStrategy;


/**
 * Created by water on 14-11-21.
 */
public class PosSystem {
    public static void main (String[] args) {
        GoodsList goodsList = new GoodsList();
        try {
            goodsList.add(DataParser.map(DataProvider.importData(new GoodsFile()), new GoodsParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        goodsList.showDetail();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        try {
            shoppingCart.add(DataParser.map(DataProvider.importData(new ShoppingCartFile()), new ShoppingCartParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        try {
            promotionStrategy.attach(DataParser.map(DataProvider.importData(new DiscountFile()), new DiscountParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.importData(new SecondHalfPriceFile()), new SecondHalfPriceParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.importData( new FullCashBackFile() ), new FullCashBackParser()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        promotionStrategy.showPromotionDetail();

        KkPos kkPosA = new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);

    }
}
