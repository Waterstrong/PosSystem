package ws.pos.promotion;


import ws.pos.model.Item;

/**
 * Created by water on 14-11-27.
 */
public interface Promotion {
    public abstract Item calculate(final Item item);
    public abstract String getDescription();
}
