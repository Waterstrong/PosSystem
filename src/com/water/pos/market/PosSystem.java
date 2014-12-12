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
            goodsList.add(DataParser.map(DataProvider.read(new FileStream(FilePath.GOODS_FILE).getBufferReader()), new GoodsParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        goodsList.showDetail();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        try {
            shoppingCart.add(DataParser.map(DataProvider.read(new FileStream(FilePath.SHOPPING_CART_FILE).getBufferReader()), new ShoppingCartParser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        try {
            promotionStrategy.attach(DataParser.map(DataProvider.read(new FileStream(FilePath.DISCOUNT_FILE).getBufferReader()), new DiscountParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.read(new FileStream(FilePath.SECOND_HALF_PRICE_FILE).getBufferReader()), new SecondHalfPriceParser()));
            promotionStrategy.attach(DataParser.map(DataProvider.read(new FileStream(FilePath.FULL_CASH_BACH_FILE).getBufferReader()), new FullCashBackParser()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        promotionStrategy.showPromotionDetail();

        KkPos kkPosA = new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);

    }
}
