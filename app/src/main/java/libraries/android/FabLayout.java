package libraries.android;

import android.content.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Easy way to create a fab layout
 * Example
 * --------------

 new FabLayout(context)
 .setContent(view1)
 .setFabImageResource(android.R.drawable.ic_dialog_email)
 .setFabClickListener(new View.OnClickListener(){
@Override
public void onClick(View v) {
//take action when fab lcicked
}
})
 .getView()


 *
 */

public class FabLayout {

    private Context context;
    private RelativeLayout relativeLayout;
    private FloatingActionButton fab;
    private LinearLayout mainContent;

    public FabLayout(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.addView(createMainContent());
        relativeLayout.addView(createFab());

    }

    private View createMainContent() {
        mainContent = new LinearLayout(context);
        mainContent.setOrientation(LinearLayout.VERTICAL);
        mainContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(mainContent);
        scrollView.setLayoutParams(mainContentLayoutParams());
        return scrollView;
    }

    private RelativeLayout.LayoutParams mainContentLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return params;
    }

    private View createFab() {
        fab = new FloatingActionButton(context);
        fab.setLayoutParams(fabLayoutParams());
        return fab;
    }

    private RelativeLayout.LayoutParams fabLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(16, 16, 16, 16);
        return params;
    }

    public View getView() {
        return relativeLayout;
    }

    public FabLayout setContent(View content) {
        mainContent.removeAllViews();
        mainContent.addView(content);
        return this;
    }


    public FabLayout setFabImageResource(int fabImageResource) {
        fab.setImageResource(fabImageResource);
        return this;
    }

    public FabLayout setFabClickListener(View.OnClickListener onClickListener) {
        fab.setOnClickListener(onClickListener);
        return this;
    }

}
