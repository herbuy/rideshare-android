package client.ui.display.Trip.schedule_trip.p20210303.dataform.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import com.skyvolt.jabber.R;
import libraries.android.MakeDummy;
import libraries.android.MakeStateListDrawable;
import resources.ItemColor;

public abstract class ItemPickerForApplication<ItemType> extends ItemPicker<ItemType> {
    public ItemPickerForApplication(Context context) {
        super(context);
    }

    @Override
    protected ViewGroup getContainer(Context context) {
        return MakeDummy.linearLayoutVertical(context);
    }


    @Override
    public int getStatusImageResourceWhenSelected() {
        return R.drawable.ic_menu_tick;
    }

    @Override
    public int textBackgroundResource() {
        return R.drawable.border_bottom;
    }

    @Override
    public Drawable getBackgroundOrNull() {
        return MakeStateListDrawable.where(Color.WHITE, ItemColor.highlight());
    }

    @Override
    public SelectableItem.Padding getPaddingOrNull() {
        return null;
    }
}
