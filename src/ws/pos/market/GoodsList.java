package ws.pos.market;

import ws.pos.model.Goods;
import ws.pos.parser.GoodsParser;

import java.util.HashMap;
import java.lang.String;
import java.util.Iterator;
import java.util.List;

/**
 * Created by water on 14-11-21.
 */
public class GoodsList {
    private HashMap<String, Goods> goodsMap = new HashMap<String, Goods>();
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
    public void printGoodsList() {
        Iterator<String> iter = goodsMap.keySet().iterator();
        System.out.println("商品基本信息：");
        Goods.printGoodsTitle();
        System.out.println();
        while (iter.hasNext()) {
            String barcode = iter.next();
            Goods goods = goodsMap.get(barcode);
            goods.printGoodsDetail();
            System.out.println();
        }
    }
}
