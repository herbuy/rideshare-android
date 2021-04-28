package client.ui.display.Trip.tripList;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import libraries.android.HerbuyStateView;

public class FrameLayoutBasedHerbuyStateView implements HerbuyStateView {
    private Context context;
    FrameLayout frameLayout;

    public FrameLayoutBasedHerbuyStateView(Context context) {
        this.context = context;
        frameLayout = new FrameLayout(context){
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                FrameLayoutBasedHerbuyStateView.this.onAttachedToWindow((ViewGroup)frameLayout);
            }

            @Override
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                FrameLayoutBasedHerbuyStateView.this.onDetachedFromWindow((ViewGroup)frameLayout);
            }
        };
        renderDefault();
    }

    //optional method a viewContainer could implement do some work once it is visible
    //this is the place where it can subscribe, etc
    protected void onAttachedToWindow(ViewGroup viewContainer) {

    }

    //optional method a view container could implement to do something whenever it is no longer visible
    //this is the place where it should unsubscribe to updates to avoid memory leaks
    protected void onDetachedFromWindow(ViewGroup viewContainer) {

    }

    public void renderDefault() {

    }

    @Override
    public ViewGroup getView() {
        return frameLayout;
    }

    @Override
    public void render(View content) {
        setContent(content);

    }

    protected void setContent(View content) {
        frameLayout.removeAllViews();
        TransitionManager.beginDelayedTransition(frameLayout);
        frameLayout.addView(content);
    }

    @Override
    public void renderError(String message, final OnRetryLoad onRetryLoad) {
        setContent(renderErrorImpl(message, onRetryLoad));
    }

    private View renderErrorImpl(String message, final OnRetryLoad onRetryLoad) {
        View error = makeErrorView(message);
        Button btn = makeButton();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRetryLoad != null) {
                    onRetryLoad.run();
                }
            }
        });

        LinearLayout linearLayout = makeErrorContainer();

        TransitionManager.beginDelayedTransition(frameLayout);
        linearLayout.addView(error);
        linearLayout.addView(vspace());
        linearLayout.addView(btn);


        return linearLayout;
    }

    private LinearLayout makeErrorContainer() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        //linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        //linearLayout.setPadding(24,24,24,24);
        return linearLayout;
    }

    private View makeErrorView(String message) {
        return defaultErrorView(message);
        //return options != null ? options.errorView(message) : defaultErrorView(message);
    }

    private TextView defaultErrorView(String message) {
        TextView error = new TextView(context);
        error.setText(message);
        //error.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return error;
    }

    private View vspace() {
        TextView textView = new TextView(context);
        textView.setText("h");
        textView.setVisibility(View.INVISIBLE);
        return textView;
    }

    private Button makeButton() {
        return options != null ? options.retryButton(context) : makeDefaultButton();
    }

    private Button makeDefaultButton() {
        Button btn = new Button(context);
        btn.setText("Retry");
        return btn;
    }

    public interface Options {
        View errorView(String message);
        Button retryButton(Context context);
    }
    private static Options options;

    public static void setOptions(Options options) {
        FrameLayoutBasedHerbuyStateView.options = options;
    }

    @Override
    public void renderBusy() {
        TextView busy = new TextView(context);
        busy.setText("Please wait...");

        setContent(busy);

    }
}
