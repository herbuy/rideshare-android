package libraries.android;

import android.content.Context;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**



 *
 */
public class FooterLayout {

    private Context context;
    private RelativeLayout relativeLayout;
    private LinearLayout footer;
    private LinearLayout mainContent;

    public FooterLayout(Context context) {
        this.context = context;
        init();
        setFooterLocationToBottom();
        setTransitionToDefault();
    }

    public Context getContext() {
        return context;
    }

    private void init() {
        relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.addView(createMainContent());
        relativeLayout.addView(createFooter());

    }

    private View createMainContent() {
        mainContent = new LinearLayout(context);
        mainContent.setOrientation(LinearLayout.VERTICAL);
        mainContent.setLayoutParams(mainContentLayoutParams());
        return mainContent;
    }

    private RelativeLayout.LayoutParams mainContentLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private View createFooter() {
        footer = new LinearLayout(context);
        footer.setVisibility(View.GONE);
        return footer;
    }

    public void setFooterLocationToTop() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        footer.setLayoutParams(params);

    }

    public void setFooterLocationToBottom() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        footer.setLayoutParams(params);
    }

    public void setFooterLocationToLeft() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        footer.setLayoutParams(params);

    }

    public void setFooterLocationToRight() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        footer.setLayoutParams(params);

    }

    public View getView() {
        return relativeLayout;
    }

    public FooterLayout setMainContent(View content) {
        mainContent.removeAllViews();
        mainContent.addView(content);
        return this;
    }


    public FooterLayout setFooterContent(View footerContent) {
        footer.removeAllViews();
        footer.addView(footerContent);
        return this;
    }

    public void setTransitionToSlideInFromLeft() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){
                Slide slide = new Slide(Gravity.LEFT);
                TransitionManager.beginDelayedTransition(relativeLayout, slide);
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                Slide slide = new Slide(Gravity.LEFT);
                TransitionManager.beginDelayedTransition(relativeLayout,slide);
                footer.setVisibility(View.GONE);
            }
        };

    }

    public void setTransitionToSlideInFromRight() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){
                Slide slide = new Slide(Gravity.RIGHT);
                TransitionManager.beginDelayedTransition(relativeLayout, slide);
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                Slide slide = new Slide(Gravity.RIGHT);
                TransitionManager.beginDelayedTransition(relativeLayout,slide);
                footer.setVisibility(View.GONE);
            }
        };

    }

    public void setTransitionToSlideInFromTop() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){
                Slide slide = new Slide(Gravity.TOP);
                TransitionManager.beginDelayedTransition(relativeLayout, slide);
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                Slide slide = new Slide(Gravity.TOP);
                TransitionManager.beginDelayedTransition(relativeLayout,slide);
                footer.setVisibility(View.GONE);
            }
        };

    }

    public void setTransitionToSlideInFromBottom() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){
                Slide slide = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition(relativeLayout, slide);
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                Slide slide = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition(relativeLayout,slide);
                footer.setVisibility(View.GONE);
            }
        };

    }

    public void setTransitionToFade() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){

                TransitionManager.beginDelayedTransition(relativeLayout, new Fade());
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                TransitionManager.beginDelayedTransition(relativeLayout,new Fade());
                footer.setVisibility(View.GONE);
            }
        };

    }

    public void setTransitionToDefault() {
        toggleImplementation = new ToggleFooterImpl(){

            @Override
            public void show(){
                TransitionManager.beginDelayedTransition(relativeLayout);
                footer.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide(){
                TransitionManager.beginDelayedTransition(relativeLayout);
                footer.setVisibility(View.GONE);
            }
        };

    }


    private interface ToggleFooterImpl {
        void show();
        void hide();
    }
    private ToggleFooterImpl toggleImplementation;


    public void showFooter(){
        toggleImplementation.show();
    }
    public void hideFooter(){
        toggleImplementation.hide();
    }


    public void hideFooterAfterMillis(int millis){
        footer.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideFooter();
            }
        }, millis);
    }
}
