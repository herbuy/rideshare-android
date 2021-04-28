package client.ui.display.login_details.view_list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyStateView;

public class HostForLoginDetailsList implements HerbuyStateView {
    private Context context;
    private LinearLayout view;

    public HostForLoginDetailsList(Context context) {
        this.context = context;
        view = MakeDummy.linearLayoutVertical(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void render(View content) {
        changeViewTo(content);

    }

    public void changeViewTo(View content) {
        view.removeAllViews();
        view.addView(content);
    }

    @Override
    public void renderError(String message, OnRetryLoad onRetryLoad) {
        changeViewTo(renderErrorImpl(message, onRetryLoad));
    }

    private View renderErrorImpl(String message, final OnRetryLoad onRetryLoad) {
        return MakeDummy.linearLayoutVertical(
                context,
                Atom.textView(context,message),
                Atom.button(context, "Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onRetryLoad != null){
                            onRetryLoad.run();
                        }
                    }
                })
        );
    }

    @Override
    public void renderBusy() {
        changeViewTo(renderBusyImpl());

    }

    private View renderBusyImpl() {
        return MakeDummy.textView(context,"Loading..");
    }
}
