package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.skyvolt.jabber.R;
import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import libraries.AdapterForList;
import libraries.android.MakeDummy;
import libraries.android.MakeStateListDrawable;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class PickerDialog<ValueType> {
    private AlertDialog dialog;
    private Context context;

    public PickerDialog(Context context) {
        this.context = context;
        createDialog(context);
    }
    public void show(){
        dialog.show();
    }

    public void createDialog(Context context) {
        dialog = new AlertDialog.Builder(context)
                .setTitle(getDialogTitle())
                .setAdapter(adapter.instance(), null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.hide();
                    }
                })
                .create();
    }

    protected abstract String getDialogTitle();

    private Singleton<ListAdapter> adapter = new Singleton<ListAdapter>() {
        @Override
        protected ListAdapter onCreate() {
            List<ValueType> valueList = createDateList();
            return makeAdapter(valueList);

        }
    };

    private AdapterForList<ValueType> makeAdapter(final List<ValueType> dateList) {
        return new AdapterForList<ValueType>(dateList) {
            @Override
            protected View makeView(ViewGroup parent, final ValueType data) {
                SelectableItem<ValueType> item = new SelectableItem<>(context, new SelectableItem.Participant<ValueType>() {
                    @Override
                    public SelectableItem.Padding getPaddingOrNull() {
                        return null;
                    }

                    @Override
                    public void afterSelect(SelectableItem<ValueType> item) {
                        dialog.hide();
                        notifyObservers(data);
                    }

                    @Override
                    public void afterUnSelect(SelectableItem<ValueType> item) {

                    }

                    @Override
                    public String getReasonToRejectSelectOrNull(ValueType value) {
                        return PickerDialog.this.getReasonToRejectSelectOrNull(value);
                    }

                    @Override
                    public void onSelectFailed(String reason) {

                    }

                    @Override
                    public String getReasonToRejectUnSelectOrNull(ValueType newValue) {
                        return null;
                    }

                    @Override
                    public void onUnselectFailed(String reason) {

                    }

                    @Override
                    public int getStatusImageResourceWhenSelected() {
                        return R.drawable.ic_menu_tick;
                    }

                    @Override
                    public float textSizeWhenSelected() {
                        return Dp.one_point_5_em();
                    }

                    @Override
                    public int textColorWhenSelected() {
                        return ItemColor.primary();
                    }

                    @Override
                    public int backgroundColorWhenSelected() {
                        return Color.parseColor("#eeeeee");
                    }

                    @Override
                    public String getDisplayText(ValueType value) {
                        return PickerDialog.this.getDisplayText(value);
                    }

                    @Override
                    public int textBackgroundResource() {
                        return 0;
                    }

                    @Override
                    public ItemRenderer<ValueType> getItemRenderer() {
                        return null;
                    }

                    @Override
                    public Drawable getBackgroundOrNull() {
                        return MakeStateListDrawable.where(Color.WHITE,ItemColor.highlight());
                    }
                });
                item.setValue(data);
                return item.getView();

            }
        };
    }

    protected String getReasonToRejectSelectOrNull(ValueType value) {
        return null;
    }

    protected abstract String getDisplayText(ValueType value);
    protected abstract void notifyObservers(ValueType data);
    protected abstract List<ValueType> createDateList();
}
