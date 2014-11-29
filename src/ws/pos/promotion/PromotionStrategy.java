package ws.pos.promotion;

import ws.pos.common.Pair;
import ws.pos.model.Item;
import ws.pos.parser.DataParser;
import ws.pos.promotion.DiscountPromotion;
import ws.pos.promotion.Promotion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by water on 14-11-27.
 */
public class PromotionStrategy {
    private HashMap<String, List<Promotion>> promotionMap = new HashMap<String, List<Promotion>>();
    public Item calculate(final Item item) {
        Item newItem = item;
        if (promotionMap.containsKey(item.getBarcode())) {
            Iterator<Promotion> iter = promotionMap.get(item.getBarcode()).iterator();
            while (iter.hasNext()) {
                Promotion promotion = iter.next();
                newItem = promotion.calculate(newItem);
            }
        }
        return newItem;
    }
    public void attach(DataParser dataParser, String fileName) {
        try {
            List<Pair<String, Promotion>> promotionList = dataParser.loadFromFile(fileName);
            Iterator<Pair<String, Promotion>> iter = promotionList.iterator();
            //promotionMap.clear();
            while (iter.hasNext()) {
                Pair<String, Promotion> promotionPair = iter.next();
                String barcode = promotionPair.getKey();
                Promotion promotion = promotionPair.getValue();
                List<Promotion> proList = null;
                if (promotionMap.containsKey(barcode)) {
                    proList = promotionMap.get(barcode);
                } else {
                    proList = new LinkedList<Promotion>();
                }
                proList.add(promotion);
                promotionMap.put(barcode, proList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void printGoodsPromotion() {
        Iterator<String> iter = promotionMap.keySet().iterator();
        System.out.println("当前优惠促销活动：");
        System.out.println("商品条码        商品优惠活动");
        while (iter.hasNext()) {
            String barcode = iter.next();
            System.out.print(barcode + "    ");
            Iterator<Promotion> iterPro = promotionMap.get(barcode).iterator();
            while (iterPro.hasNext()) {
                Promotion promotion = iterPro.next();
                System.out.print(promotion.getDescription() + " | ");
            }
            System.out.println();
        }

    }

    //public void detach() {}
}