package characters;

import android.content.Context;
import android.view.View;

public interface ItemContainer<T> {
    void add(View view);
    void getView();
    Context getContext();
    T getData();
}
