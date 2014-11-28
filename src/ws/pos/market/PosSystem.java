package ws.pos.market;

import ws.pos.market.PromotionStrategy;
import ws.pos.market.ShoppingCart;
import ws.pos.market.GoodsList;
import ws.pos.parser.DiscountParser;
import ws.pos.parser.FullCashBackParser;
import ws.pos.parser.SecondHalfPriceParser;
import ws.pos.promotion.Promotion;


/**
 * Created by water on 14-11-21.
 */
public class PosSystem {
    public static void main (String[] args) {
        GoodsList goodsList = new GoodsList();
        goodsList.printGoodsList();

        ShoppingCart shoppingCart = new ShoppingCart(goodsList);
        shoppingCart.printShoppingCart();

        PromotionStrategy promotionStrategy = new PromotionStrategy();
        promotionStrategy.attach(new DiscountParser(), "discount_promotion.txt");
        promotionStrategy.attach(new SecondHalfPriceParser(), "second_half_price_promotion.txt");
        promotionStrategy.attach(new FullCashBackParser(), "full_cash_back_promotion.txt");
        promotionStrategy.printGoodsPromotion();

        shoppingCart.applyPrintSettlement(promotionStrategy);

    }
}
