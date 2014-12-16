package com.water.pos.market;

import com.water.pos.common.FilePath;
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
            goodsList.add(DataParser.map(DataProvider.read(FilePath.GOODS_FILE), new GoodsParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //goodsList.showDetail();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        try {
            shoppingCart.add(DataParser.map(DataProvider.read(FilePath.SHOPPING_CART_FILE), new ShoppingCartParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        try {
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.DISCOUNT_FILE), new DiscountParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.SECOND_HALF_PRICE_FILE), new SecondHalfPriceParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.read(FilePath.FULL_CASH_BACH_FILE), new FullCashBackParser()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //promotionStrategy.showPromotionDetail();

        KkPos kkPosA = new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);

    }
}
