/**
 * Created by water on 14-11-21.
 */
public class PosSystem {
    public static void main (String[] args) {
        ItemList itemList = new ItemList();

        SecondHalfPrice secondHalfPrice = new  SecondHalfPrice();
        Discount discount = new Discount();
        secondHalfPrice.setNextPromotion(discount);
        //discount.SetNextPromotion(&secondHalfPrice);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.ApplyPrintSettlement(itemList, secondHalfPrice);

    }
}
