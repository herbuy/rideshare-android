package client.ui.display.Trip.schedule_trip.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import client.ui.libraries.Form;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public abstract class TripSchedulingStep  extends Form.ScheduleTripFragment {
    public boolean isForDriversOnly(){
        return false;
    }

    private int stepNumber;
    private int totalSteps;

    public TripSchedulingStep setStepData(int stepNumber, int totalSteps) {
        this.stepNumber = stepNumber;
        this.totalSteps = totalSteps;
        return this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return stepView(this,stepNumber,totalSteps);
    }

    private View stepView(TripSchedulingStep step, int stepNumber, int totalSteps) {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(
                MakeDummy.linearLayoutHorizontal(
                        getContext(),
                        progressView(stepNumber, totalSteps),
                        Atom.textView(getContext(),": "),
                        stepTitleView(step)
                )
        );
        linearLayout.addView(question(step));
        linearLayout.addView(step.getAnswerArea());

        return linearLayout;
    }

    private TextView question(Form.Step step) {
        TextView question = Atom.textView(getContext(),step.getQuestion());
        question.setTextSize(Dp.scaleBy(0.8f));
        question.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return question;
    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }


    private TextView stepTitleView(Form.Step step) {
        TextView stepTitle = Atom.textViewPrimaryBold(getContext(),step.getTitle().toUpperCase());
        stepTitle.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return stepTitle;
    }

    private View progressView(Integer stepNumber, Integer totalSteps) {
        TextView stepCount = Atom.textViewPrimaryBold(
                getContext(),
                String.format("<font color='#ff9900'>STEP: %d/%d</font>",stepNumber,totalSteps)
        );
        stepCount.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return stepCount;
    }


}
