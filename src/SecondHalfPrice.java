import java.util.HashSet;

/**
 * Created by water on 14-11-21.
 */
public class SecondHalfPrice extends Promotion {
    protected int DiscountStandard;
    protected double DiscountDegree;
    protected HashSet<String> PromotionList;
    protected void HandleItem(String item) {
        // ...
    }
    public SecondHalfPrice() {
        // LoadItemFromFile("second_half_price_promotion.txt");
        DiscountStandard = 2;
        DiscountDegree = 0.5;
    }
    public void ClearPromotionList() {
        PromotionList.clear();
    }
    @Override
    protected Item HandlePromotion(Item item) {
        if (PromotionList.contains(item.getBarcode())) {
            int discountAmount = item.getAmount() / DiscountStandard;
            double totalPrice = item.getPrice() * DiscountDegree * discountAmount + (item.getAmount()-discountAmount)*item.getPrice();
            item.setPrice(totalPrice/item.getAmount());
        }
        return item;
    }
}
