package com.water.pos.market;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.water.pos.common.FilePath;
import com.water.pos.file.*;
import com.water.pos.parser.*;
import com.water.pos.promotion.PromotionStrategy;


/**
 * Created by water on 14-11-21.
 */
public class PosSystem {
    public static void main (String[] args) {
/*
        Injector injector = Guice.createInjector();

        GoodsList goodsList = GoodList.getInstance();
        try {
            goodsList.add(DataParser.map(DataProvider.read(FilePath.GOODS_FILE), injector.getInstance(GoodsParser.class))); // new GoodsParser()
        } catch (Exception e) {
            e.printStackTrace();
        }
        //goodsList.showDetail();



        ShoppingCart shoppingCart = injector.getInstance(ShoppingCart.class); // new ShoppingCart(goodsList);
        try {
            shoppingCart.add(DataParser.map(DataProvider.read(FilePath.SHOPPING_CART_FILE), injector.getInstance(ShoppingCartParser.class))); // new ShoppingCartParser()
        } catch (Exception e) {
            e.printStackTrace();
        }
        //shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = injector.getInstance(PromotionStrategy.class); //new PromotionStrategy();
        try {
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.DISCOUNT_FILE), injector.getInstance(DiscountParser.class))); //new DiscountParser()
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.SECOND_HALF_PRICE_FILE), injector.getInstance(SecondHalfPriceParser.class) )); // new SecondHalfPriceParser()
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.FULL_CASH_BACH_FILE), injector.getInstance(FullCashBackParser.class))); //new FullCashBackParser()
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //promotionStrategy.showPromotionDetail();

        KkPos kkPosA = injector.getInstance(KkPos.class); // new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);
*/
    }
}
