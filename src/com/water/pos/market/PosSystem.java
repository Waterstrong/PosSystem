package com.water.pos.market;

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
        goodsList.showDetail();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        shoppingCart.showDetail();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        promotionStrategy.attach(new DiscountParser(), "discount_promotion.txt");
        promotionStrategy.attach(new SecondHalfPriceParser(), "second_half_price_promotion.txt");
        promotionStrategy.attach(new FullCashBackParser(), "full_cash_back_promotion.txt");
        promotionStrategy.showPromotionDetail();

        KkPos kkPosA = new KkPos();
        kkPosA.applyPrintSettlement(promotionStrategy, shoppingCart);

    }
}
