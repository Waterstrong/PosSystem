package com.water.pos.market;

import com.water.pos.model.Goods;
import com.water.pos.parser.GoodsParser;

import java.util.HashMap;
import java.lang.String;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by water on 14-11-21.
 */
public class GoodsList {
    private Map<String, Goods> goodsMap = new HashMap<String, Goods>();
    public GoodsList() {
        init();
    }
    private void init(){
        try {
            List<Goods> goodsList = new GoodsParser().loadFromFile("itemlist.txt");
            Iterator<Goods> iter = goodsList.iterator();
            while (iter.hasNext()) {
                Goods goods = iter.next();
                goodsMap.put(goods.getBarcode(), goods);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Goods getGoods(String barcode) {
        return goodsMap.get(barcode);
    }
    public void showDetail() {
        Iterator<String> iter = goodsMap.keySet().iterator();
        System.out.println("商品基本信息：");
        System.out.println("商品条码    商品单价");
        while (iter.hasNext()) {
            String barcode = iter.next();
            Goods goods = goodsMap.get(barcode);
            System.out.println(goods.toString());
        }
    }
}
