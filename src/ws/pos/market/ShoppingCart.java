package ws.pos.market;

import ws.pos.model.Item;
import ws.pos.parser.ShoppingCartParser;
import ws.pos.promotion.PromotionStrategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by water on 14-11-27.
 */
public class ShoppingCart {
    private HashMap<String, Item> cartMap = new HashMap<String, Item>();
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
                Item existItem = cartMap.get(item.getBarcode());
                int newAount = item.getAmount() + (existItem == null ? 0 : existItem.getAmount());
                Double price = goodsList.getGoods(item.getBarcode()).getPrice();
                Item newItem = new Item(item.getBarcode(), price, newAount);
                cartMap.put(item.getBarcode(), newItem);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void applyPrintSettlement(PromotionStrategy promotionStrategy) {
        System.out.println("收银台结算打印小票：");
        System.out.println("购物明细    （数量	单价    小计）");
        double beforePromotion = 0;
        double afterPromotion = 0;
        Iterator<String> iter = cartMap.keySet().iterator();
        while (iter.hasNext()) {
            String barcode = iter.next();
            Item item = cartMap.get(barcode);
            beforePromotion += item.getSubtotal();
            Item newItem = promotionStrategy.calculate(item);
            afterPromotion += newItem.getSubtotal();
            System.out.println(item.getBarcode() + "   " + item.getAmount() + "      " + item.getPrice() + "    " + newItem.getSubtotal());
        }
        Item totalItem = new Item("ITEM_TOTAL", afterPromotion, 1);
        afterPromotion = promotionStrategy.calculate(totalItem).getPrice();
        System.out.println("总计金额（优惠前  优惠后  优惠差价）");
        System.out.println(afterPromotion+"    "+beforePromotion+"  "+afterPromotion+"  "+(beforePromotion-afterPromotion));
    }

    public void printShoppingCart() {
        Iterator<String> iter = cartMap.keySet().iterator();
        System.out.println("购物车信息：");
        Item.printItemTitle();
        System.out.println();
        while (iter.hasNext()) {
            String barcode = iter.next();
            Item item = cartMap.get(barcode);
            item.printItemDetail();
            System.out.println();
        }
    }


}
