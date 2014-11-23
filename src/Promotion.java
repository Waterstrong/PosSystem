/**
 * Created by water on 14-11-21.
 */
public abstract class Promotion {
    protected Promotion Next;
    public void setNextPromotion(Promotion next) {
        Next = next;
    }
    public double AcceptPromotion(Item item) {
        item = HandlePromotion(item);
        if (Next != null)
        {
            return Next.AcceptPromotion(item);
        }
        else
        {
            return item.getSubtotal();
        }
    }
    protected abstract Item HandlePromotion(Item item);

}
