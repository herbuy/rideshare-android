package client.ui.display.Trip.schedule_trip;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class SelectableItem<ItemType> {

    Context context;
    LinearLayout container;

    boolean isSelected = false;

    public SelectableItem(Context context) {
        this.context = context;
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        updateUI();
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected){
                    unselect();
                }
                else{
                    select();
                }

            }

        });
    }

    //this flag is used to animate the unselected.
    boolean wasPreviouslySelected = false;

    public final void unselect() {
        String reasonNotToAllowUnselect = reasonNotToAllowUnSelect(getValue());
        if(exists(reasonNotToAllowUnselect)){
            onUnselectFailed(reasonNotToAllowUnselect);
        }
        else{
            beforeUnselect(this);
            wasPreviouslySelected = isSelected;
            isSelected = false;
            updateUI();
            afterUnSelect(this);
        }

    }


    //override this method if u want to support things like checking is max selectable items exceeded
    //then return a message like 'can not select more than x items' or 'this item is already selected'
    protected String reasonNotToAllowSelect(ItemType value) {
        return "";
    }

    //override this method to allow notify end user when selection fails, giving them the reason
    //e,g can not select more than N items, u have already reached your limit
    //item already selected
    //can not select item x and item y at the same time
    //selecting this item would cause conflict in x
    protected void onSelectFailed(String reason) {

    }

    //by default, we have not reason to disallow Unselect
    //but u may have a reason, so u override this method to make some checks and return the reason
    //if it exists, else return empty string or null
    protected String reasonNotToAllowUnSelect(ItemType value) {
        return "";
    }


    //by default, we do nothing. You can override to decide how to indicate failure to unselect
    //and to display the reason
    protected void onUnselectFailed(String reason) {

    }

    public final void select() {
        String reasonNotToAllowSelect = reasonNotToAllowSelect(getValue());
        if(exists(reasonNotToAllowSelect)){
            onSelectFailed(reasonNotToAllowSelect);
        }
        else{
            beforeSelect(this);
            isSelected = true;
            updateUI();
            afterSelect(this);
        }

    }

    private boolean exists(String reason) {
        return !(reason == null || reason.trim().equalsIgnoreCase(""));
    }


    protected abstract void beforeSelect(SelectableItem selectableItem);
    protected abstract void afterSelect(SelectableItem selectableItem);


    protected abstract void beforeUnselect(SelectableItem selectableItem);
    protected abstract void afterUnSelect(SelectableItem selectableItem);


    //we basically redraw the entire list of items, because the state of one of the items has changed
    String text = "Item";
    private void updateUI() {
        container.removeAllViews();
        container.addView(child());
    }

    private LinearLayout child() {
        LinearLayout child = new LinearLayout(context);
        child.setOrientation(LinearLayout.HORIZONTAL);
        child.setGravity(Gravity.CENTER_VERTICAL);
        child.addView(space());
        child.addView(tick());
        child.addView(textView());
        child.setBackgroundColor(getBackgroundColor());
        child.setPadding(0,16,0,16);
        return child;
    }

    private View space() {
        TextView textView = new TextView(context);
        textView.setText("h");
        textView.setVisibility(View.INVISIBLE);
        return textView;
    }

    private View tick() {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(48,48));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(getStatusImageResource());

        if(isSelected){
            imageView.setScaleX(0);
            imageView.setScaleY(0);
            imageView.animate().scaleX(1).scaleY(1);
        }
        else if(wasPreviouslySelected){
            imageView.animate().scaleX(0).scaleY(0);
        }
        else{
            imageView.setScaleX(0);
            imageView.setScaleY(0);

        }


        return imageView;
    }

    private int getStatusImageResource() {
        return imageResourceToIndicateSelected();//return isSelected ? imageResourceToIndicateSelected() : 0;
    }

    protected abstract int imageResourceToIndicateSelected();

    private TextView textView() {
        TextView child = new TextView(context);
        child.setText(text);
        if(isSelected){
            child.setTextColor(textColorWhenSelected());
            child.setTextSize(textSizeWhenSelected());
            child.setTypeface(null, Typeface.BOLD);
        }


        child.setPadding(16,0,0,0);
        return child;
    }

    protected abstract float textSizeWhenSelected();

    protected abstract int textColorWhenSelected();


    public final void setText(String text){
        this.text = text;
        updateUI();
    }

    private int getBackgroundColor() {
        return isSelected ? backgroundColorWhenSelected() : Color.TRANSPARENT;
    }

    protected abstract int backgroundColorWhenSelected();

    public final View getView() {
        return container;
    }

    public abstract ItemType getValue();
}
