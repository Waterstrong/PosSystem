/**
 * Created by water on 14-11-21.
 */
import java.lang.String;
public abstract class IFileStream {
    protected abstract void HandleItem(String item);
    public void LoadItemFromFile(String fileName)
    {
        // file operation
        // ...
        //HandleItem(item); // handle item string
    }
}
