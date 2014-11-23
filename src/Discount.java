import java.util.HashMap;

/**
 * Created by water on 14-11-21.
 */
public class Discount extends Promotion {
    protected HashMap<String, Double> PromotionList;
    protected void HandleItem(String item) {
        // ...
    }
    public Discount() {
        // LoadItemFromFile("discount_promotion.txt");
    }
    public void ClearPromotionList() {
        PromotionList.clear();
    }

    @Override
    protected Item HandlePromotion(Item item) {
        if (PromotionList.containsKey(item.getBarcode())) {
            item.setPrice(item.getPrice()*PromotionList.get(item.getBarcode()));
        }
        return item;
    }
}
