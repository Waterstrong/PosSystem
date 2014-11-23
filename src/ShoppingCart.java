import java.util.HashMap;
import java.util.Iterator;
import java.lang.String;

/**
 * Created by water on 14-11-21.
 */
public class ShoppingCart {
    private HashMap<String, Integer> Cart;
    public ShoppingCart() {
        // LoadItemFromFile("cart.txt");
    }
    public void ClearCart() {
        Cart.clear();
    }
    public void ApplyPrintSettlement(ItemList itemList, Promotion promotion) {

        System.out.println("购物明细    （数量	单价	小计）");

        double beforePromotion = 0;
        double afterPromotion = 0;

        Iterator iter = Cart.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String)iter.next();
            int val = Cart.get(key);
            double price = itemList.getItemPrice(key);
            beforePromotion += val * price;
            Item item = new Item(key, price, val);
            double subtotal = promotion.AcceptPromotion(item);
            afterPromotion += subtotal;
            System.out.println(key + "  " + val + " " + price + "   " + subtotal);
        }
        System.out.println("总计金额（优惠前  优惠后  优惠差价）");
        System.out.println("   "+afterPromotion+"	    "+beforePromotion+"	   "+afterPromotion+"	   "+(beforePromotion-afterPromotion));
    }
}
