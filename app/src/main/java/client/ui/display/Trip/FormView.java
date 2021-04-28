package client.ui.display.Trip;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skyvolt.jabber.R;

import java.util.Locale;

import client.ui.libraries.Form;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.ViewPagerWithoutFragments;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

class FormView {

    private final AppCompatActivity context;
    LinearLayout wrapper;

    public FormView(AppCompatActivity context) {
        this.context = context;
        wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setBackgroundColor(Color.WHITE);

    }

    ViewPagerWithoutFragments viewPager;

    public void gotoPage(int pageNumber) {
        if(pageNumber < viewPager.getAdapter().getCount()){
            viewPager.setCurrentItem(pageNumber);
        }
    }

    public void gotoNextPage() {
        gotoPage(viewPager.getCurrentItem() + 1);
    }

    public boolean hasNextPage() {
        return hasPage(viewPager.getCurrentItem() + 1);
    }

    public boolean hasPage(int index) {
        return index < viewPager.getAdapter().getCount();
    }

    public int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    public View getView(){
        /*View layout = RelativeLayoutFactory.alignBelowWidget(
                prizeView(design.prize()),
                makeViewPager()
        );
        return layout;*/
        return wrapper;
    }

    public View makeViewPager() {
        ViewPagerWithoutFragments viewPager = new ViewPagerWithoutFragments(context);
        for(int i = 0; i < design.steps().length; i++){
            viewPager.add(stepView(design.steps()[i],i+1,design.steps().length));
        }

        viewPager.setOffscreenPageLimit(design.steps().length + 2);
        return viewPager;
    }

    private Form.Design design;
    public void setDesign(Form.Design design){
        this.design = design;
        wrapper.removeAllViews();
        wrapper.addView(content());
    }

    private View content() {
        View layout = RelativeLayoutFactory.alignBelowWidget(
                prizeView(design.prize()),
                makeViewPager()
        );
        return layout;
    }

    private int paddingHorizontal(){
        return Dp.one_point_5_em();
    }

    private View stepView(final Form.Step step, final int stepNumber, final int totalSteps) {

        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                //MessageBox.show("Attached",context);
                linearLayout.addView(
                        MakeDummy.linearLayoutHorizontal(
                                context,
                                progressView(stepNumber, totalSteps),
                                Atom.textView(context,": "),
                                stepTitleView(step)
                        )
                );
                linearLayout.addView(question(step));
                linearLayout.addView(step.getAnswerArea());
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                //MessageBox.show("DETACHED",context);
                linearLayout.removeAllViews();
            }
        });

        return linearLayout;
    }

    private TextView question(Form.Step step) {
        TextView question = Atom.textView(context,step.getQuestion());
        question.setTextSize(Dp.scaleBy(0.8f));
        question.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return question;
    }

    private TextView stepTitleView(Form.Step step) {
        TextView stepTitle = Atom.textViewPrimaryBold(context,step.getTitle().toUpperCase());
        stepTitle.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return stepTitle;
    }

    private View progressView(Integer stepNumber, Integer totalSteps) {
        TextView stepCount = Atom.textViewPrimaryBold(
                context,
                String.format(Locale.ENGLISH,"<font color='#ff9900'>STEP: %d/%d</font>",stepNumber,totalSteps)
        );
        stepCount.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return stepCount;
    }

    private View prizeView(String prize) {
        TextView textView = Atom.textViewPrimaryNormal(context,prize);
        textView.setTextSize(Dp.scaleBy(1.5f));
        textView.setBackgroundResource(R.drawable.bmp_header_bg);
        textView.setPadding(Dp.two_em(),Dp.two_em(),Dp.two_em(),Dp.two_em());
        textView.setTextColor(Color.parseColor("#ffffff"));
        return textView;
    }



}
