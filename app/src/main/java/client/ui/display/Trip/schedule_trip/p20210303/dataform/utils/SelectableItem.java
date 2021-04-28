package client.ui.display.Trip.schedule_trip.p20210303.dataform.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectableItem<ItemType> {

    //receives events, commands, and notifications from this item, could be a list
    private final Participant<ItemType> participant;
    Context context;
    LinearLayout container;
    private TextView inlineError;
    boolean isSelected = false;

    public SelectableItem(Context context, Participant<ItemType> participant) {
        this.context = context;
        this.participant = participant;
        init();
    }

    public void init() {
        initInlineErrorView();

        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected){
                    tryUnselect();
                }
                else{
                    trySelect();
                }
            }

        });

        container.setClickable(true);

        container.setBackground(participant.getBackgroundOrNull());
        Padding padding = participant.getPaddingOrNull();
        if(padding != null){
            container.setPadding(
                    participant.getPaddingOrNull().left(),
                    participant.getPaddingOrNull().top(),
                    participant.getPaddingOrNull().right(),
                    participant.getPaddingOrNull().bottom()
            );
        }

    }

    public void initInlineErrorView() {
        inlineError = new TextView(context);
        inlineError.setTextColor(Color.parseColor("#ee2222"));
        inlineError.setVisibility(View.GONE);
    }

    //this flag is used to animate the unselected.
    boolean wasPreviouslySelected = false;

    //receives events and commands from the selectable item
    private ItemType value;

    public void setValue(ItemType value){
        this.value = value;
        updateUI();
    }
    public ItemType getValue(){
        return value;
    }

    public final void tryUnselect() {
        String reasonNotToAllowUnselect = participant.getReasonToRejectUnSelectOrNull(
                getValue()
        );
        if(exists(reasonNotToAllowUnselect)){
            showInlineError(reasonNotToAllowUnselect);
            participant.onUnselectFailed(reasonNotToAllowUnselect);
        }
        else{
            //beforeUnselect(this);
            wasPreviouslySelected = isSelected;
            isSelected = false;
            updateUI();
            participant.afterUnSelect(this);
        }

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


    public final void trySelect() {
        String reasonNotToAllowSelect = participant.getReasonToRejectSelectOrNull(
                getValue()
        );
        if(exists(reasonNotToAllowSelect)){
            showInlineError(reasonNotToAllowSelect);
            participant.onSelectFailed(reasonNotToAllowSelect);
        }
        else{
            //beforeSelect(this);
            isSelected = true;
            updateUI();
            participant.afterSelect(this);
        }

    }

    private static Handler handler = new Handler();
    private void showInlineError(String message) {
        inlineError.setText(message);
        TransitionManager.beginDelayedTransition(container);
        inlineError.setVisibility(View.VISIBLE);
        handler.postDelayed(hideErrorRunnable,5000);
    }

    private Runnable hideErrorRunnable = new Runnable() {
        @Override
        public void run() {
            if(inlineError != null){
                TransitionManager.beginDelayedTransition(container);
                inlineError.setVisibility(View.GONE);
            }
        }
    };

    private boolean exists(String reason) {
        return !(reason == null || reason.trim().equalsIgnoreCase(""));
    }

    private void updateUI() {
        container.removeAllViews();
        container.addView(child());
    }

    private View child() {

        Participant.ItemRenderer<ItemType> itemRenderer = participant.getItemRenderer();
        if(itemRenderer == null){
            return defaultRendering();
        }

        View result = itemRenderer.render(getValue(),isSelected);
        if(result == null){
            return defaultRendering();
        }

        return result;

    }

    private View defaultRendering() {
        return rightArea();
    }


    private View rightArea() {
        LinearLayout right = new LinearLayout(context);
        right.setOrientation(LinearLayout.VERTICAL);
        right.addView(textView());
        removeFromParentIfAlreadyAttached();
        right.addView(inlineError);
        return right;
    }

    private void removeFromParentIfAlreadyAttached() {
        try{
            ((ViewGroup) inlineError.getParent()).removeView(inlineError);
        }
        catch (Exception ex){

        }
    }

    private TextView textView() {
        TextView child = new TextView(context);
        child.setText(Html.fromHtml(participant.getDisplayText(value)));
        if(isSelected){
            child.setTextColor(participant.textColorWhenSelected());
            child.setTextSize(participant.textSizeWhenSelected());
            child.setTypeface(null, Typeface.BOLD);
        }



        child.setPadding(0,0,0,0);
        //child.setGravity(Gravity.CENTER_HORIZONTAL);
        return child;
    }

    private int getBackgroundColor() {
        return isSelected ? participant.backgroundColorWhenSelected() : Color.TRANSPARENT;
    }

    public final View getView() {
        return container;
    }

    public interface Participant<ItemType>{
        void afterSelect(SelectableItem<ItemType> item);
        void afterUnSelect(SelectableItem<ItemType> item);

        String getReasonToRejectSelectOrNull(ItemType value);
        void onSelectFailed(String reason);
        String getReasonToRejectUnSelectOrNull(ItemType newValue);
        void onUnselectFailed(String reason);
        int getStatusImageResourceWhenSelected();
        float textSizeWhenSelected();
        int textColorWhenSelected();
        int backgroundColorWhenSelected();
        String getDisplayText(ItemType value);
        int textBackgroundResource();
        ItemRenderer<ItemType> getItemRenderer();
        Drawable getBackgroundOrNull();
        Padding getPaddingOrNull();

        interface ItemRenderer<ItemType>{
            View render(ItemType item, boolean isSelected);
        }

    }
    public interface Padding{
        int left();
        int top();
        int right();
        int bottom();
    }

}
