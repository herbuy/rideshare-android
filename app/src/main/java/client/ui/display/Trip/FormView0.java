package client.ui.display.Trip;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.skyvolt.jabber.R;

import java.util.Random;

import client.ui.libraries.Form;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.AdapterForFragmentPager;
import resources.Dp;

class FormView0 {

    private final AppCompatActivity context;
    LinearLayout wrapper;

    public FormView0(AppCompatActivity context) {
        this.context = context;
        wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);

    }

    ViewPager viewPager;
    private View viewPager(Form.Step[] steps) {
        adapter(steps);

        viewPager = new ViewPager(context);
        viewPager.setId(Math.abs(new Random().nextInt()));
        viewPager.setOffscreenPageLimit(adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);

        viewPager.setAdapter(adapter);



        return viewPager;
    }

    public void gotoPage(int pageNumber) {
        if(pageNumber < adapter.getCount()){
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
        return wrapper;
    }

    public void setDesign(Form.Design design){
        LinearLayout linearLayout = MakeDummy.linearLayoutVertical(context);
        linearLayout.addView(prizeView(design.prize()));
        linearLayout.addView(stepsView(design.steps()));
        wrapper.removeAllViews();
        wrapper.addView(linearLayout);
        wrapper.setBackgroundColor(Color.WHITE);

    }

    private int paddingHorizontal(){
        return Dp.one_point_5_em();
    }

    private View stepsView(Form.Step[] steps) {

        RelativeLayout wrapper = new RelativeLayout(context);
        wrapper.addView(viewPager(steps));
        //wrapper.addView(fab());
        return wrapper;
    }
    AdapterForFragmentPager adapter;
    private AdapterForFragmentPager adapter(Form.Step[] steps) {
        adapter = new AdapterForFragmentPager(context.getSupportFragmentManager());

        int stepNumber = 0;
        int totalSteps = steps.length;
        for(Form.Step step: steps){
            stepNumber += 1;
            adapter.addTab("", stepView(step, stepNumber, totalSteps));
            adapter.notifyDataSetChanged();
        }
        return adapter;
        //Step[] steps = steps();
        //return new AdapterForFragmentPager(context.getSupportFragmentManager());
    }

    private View stepView(Form.Step step, int stepNumber, int totalSteps) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
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
                String.format("<font color='#ff9900'>STEP: %d/%d</font>",stepNumber,totalSteps)
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
