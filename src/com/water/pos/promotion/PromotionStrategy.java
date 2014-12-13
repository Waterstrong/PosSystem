package com.water.pos.promotion;

import com.water.pos.common.Pair;
import com.water.pos.model.Item;

import java.util.*;

/**
 * Created by water on 14-11-27.
 */
public class PromotionStrategy {
    private HashMap<String, List<IPromotion>> promotionMap = new HashMap<String, List<IPromotion>>();
    public Item calculate(final Item item) {
        if (item == null) return null;
        Item newItem = item;
        if (promotionMap.containsKey(item.getGoods().getBarcode())) {
            Iterator<IPromotion> iter = promotionMap.get(item.getGoods().getBarcode()).iterator();
            while (iter.hasNext()) {
                IPromotion promotion = iter.next();
                newItem = promotion.calculate(newItem);
            }
        }
        return newItem;
    }

    public List<IPromotion> getPromotionsOfGoods(String barcode) {
        return promotionMap.get(barcode);
    }

    public void attach(List<Pair<String, IPromotion>> promotionList) {
        for (Pair<String, IPromotion> promotionPair : promotionList) {
            String barcode = promotionPair.getKey();
            IPromotion promotion = promotionPair.getValue();
            List<IPromotion> proList = null;
            if (promotionMap.containsKey(barcode)) {
                proList = promotionMap.get(barcode);
            } else {
                proList = new ArrayList<IPromotion>();
            }
            proList.add(promotion);
            promotionMap.put(barcode, proList);
        }
    }
    public void showPromotionDetail() {
        Iterator<String> iter = promotionMap.keySet().iterator();
        System.out.println("当前优惠促销活动：");
        System.out.println("商品条码        商品优惠活动");
        while (iter.hasNext()) {
            String barcode = iter.next();
            System.out.print(barcode + "    ");
            Iterator<IPromotion> iterPro = promotionMap.get(barcode).iterator();
            while (iterPro.hasNext()) {
                IPromotion promotion = iterPro.next();
                System.out.print(promotion.toString() + " | ");
            }
            System.out.println();
        }

    }

    //public void detach() {}
}
