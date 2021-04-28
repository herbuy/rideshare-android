package libraries.android;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class MultiStateNetworkButton {
    private Context context;
    private LinearLayout view;

    public MultiStateNetworkButton(Context context) {
        this.context = context;
        view = new LinearLayout(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        changeViewToDefault();
    }

    private void changeViewTo(View newView) {
        view.removeAllViews();
        view.addView(newView);
    }

    protected abstract View defaultView();

    public void changeViewToDefault(){
        changeViewTo(defaultView());
    }
    public void changeViewToBusy(){
        changeViewTo(busyView());
    }

    protected abstract View busyView();

    public void changeViewToSuccess(){
        changeViewTo(successView());
    }

    protected abstract View successView();

    public void changeViewToFailed(){
        changeViewTo(errorView());
    }

    protected abstract View errorView();

    public View getView() {
        return view;
    }
}
