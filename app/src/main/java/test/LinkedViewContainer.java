package test;

import android.content.Context;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.HashSet;
import java.util.Set;

import layers.render.Atom;
import libraries.HerbuyCalendar;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import resources.Dp;

public class LinkedViewContainer {
    private LinearLayout listContainer;
    private Context context;
    private LinearLayout finalContainer;
    LinkedViewList linkedViewList;

    public LinkedViewContainer(Context context) {
        this.context = context;
        listContainer = new LinearLayout(context);
        listContainer.setOrientation(LinearLayout.VERTICAL);


        finalContainer = new LinearLayout(context);
        finalContainer.setOrientation(LinearLayout.VERTICAL);
        finalContainer.addView(listContainer);
        finalContainer.addView(actions());
        finalContainer.setPadding(Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em());


        //==================
        linkedViewList = new LinkedViewList();
        linkedViewList.addView(pageWhereTo(context));
        linkedViewList.addView(pageOrigin(context));
        linkedViewList.addView(pageDate(context));
        linkedViewList.addView(pageTime(context));
        linkedViewList.addView(pageIsDriving(context));
        linkedViewList.addView(pageSeatsWanted(context));
        linkedViewList.addView(pageCarModel(context));
        linkedViewList.addView(pageCarRegNum(context));
        linkedViewList.addView(pageSeatsAvailable(context));
        //linkedViewList.addView(Atom.textView(context,"Seats Wanted"));
        linkedViewList.addView(pageFuelContribution(context));
        linkedViewList.addView(Atom.textView(context,"Review and upload"));
        //linkedViewList.hideViewIndex(1);
        refreshContainer();



    }

    int fuelContribution;

    public View pageFuelContribution(final Context context) {
        return new QuestionExpectingSingleAnswer<Integer>(context,"Fuel Contribution"){
            @Override
            protected Integer[] getOptions() {
                return new Integer[]{
                        0, 5000, 10000, 15000, 20000
                };
            }

            @Override
            protected View getItemView(Integer data) {
                String text = "";
                if(data == 0){
                    text = "To be discussed";
                }
                else{
                    text = data.toString() + "Ushs.";
                }
                return itemView(text,context);
            }

            @Override
            protected void answerSelected(Integer answer) {
                fuelContribution = answer;
                gotoNextVisiblePage();
            }
        }.getView();
    }

    private String selectedCarModel = "";
    public View pageCarModel(Context context) {
        return new QuestionExpectingAnswerAsSingleString(context,"Your car model:"){
            @Override
            protected String[] getOptions() {
                return new String[]{
                        "Subaru forester",
                        "Toyota Premio",
                        "Land cruiser"
                };
            }

            @Override
            protected void answerSelected(String answer) {
                selectedCarModel = answer;
                gotoNextVisiblePage();
            }
        }.getView();
    }

    private String selectedCarRegNumber = "";
    public View pageCarRegNum(Context context) {
        return new QuestionExpectingAnswerAsSingleString(context,"Your car registration number:"){
            @Override
            protected String[] getOptions() {
                return new String[]{
                        "UAX 506 F",
                        "UAB 586 T"
                };
            }

            @Override
            protected void answerSelected(String answer) {
                selectedCarRegNumber = answer;
                gotoNextVisiblePage();
            }
        }.getView();
    }


    private int seatCount;
    public View pageSeatsWanted(final Context context) {
        return new QuestionExpectingSingleAnswer<Integer>(context,"Want how many seats?"){
            @Override
            protected Integer[] getOptions() {
                return new Integer[]{1,2,3,4,5};
            }

            @Override
            protected int getOptionsPerLine() {
                return 5;
            }

            @Override
            protected View getItemView(Integer data) {
                return itemView(data.toString(),context);
            }

            @Override
            protected void answerSelected(Integer answer) {
                seatCount = answer;
                gotoNextVisiblePage();
            }
        }.getView();
    }

    public View pageSeatsAvailable(final Context context) {
        return new QuestionExpectingSingleAnswer<Integer>(context,"Seats available"){
            @Override
            protected Integer[] getOptions() {
                return new Integer[]{1,2,3,4,5};
            }

            @Override
            protected int getOptionsPerLine() {
                return 5;
            }

            @Override
            protected View getItemView(Integer data) {
                return itemView(data.toString(),context);
            }

            @Override
            protected void answerSelected(Integer answer) {
                seatCount = answer;
                gotoNextVisiblePage();
            }
        }.getView();
    }

    private TextView itemView(String text, Context context) {
        TextView view = Atom.textView(context,text);
        view.setBackgroundResource(R.drawable.button_accent);
        view.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
        return view;
    }

    private Boolean isDriving;
    public View pageIsDriving(final Context context) {
        return new QuestionExpectingSingleAnswer<Boolean>(context,"Are you driving"){
            @Override
            protected Boolean[] getOptions() {
                return new Boolean[]{true,false};
            }

            @Override
            protected View getItemView(Boolean data) {
                TextView textView = Atom.textView(context,true == data ? "Yes, and want passengers":"No, looking for a lift");
                textView.setBackgroundResource(R.drawable.button_accent);
                textView.setBackgroundResource(R.drawable.button_accent);
                textView.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
                return textView;
            }

            @Override
            protected void answerSelected(Boolean answer) {
                isDriving = answer;
                if(isPassengerTrip()){
                    linkedViewList.hideViewIndex(6,7,8,9);
                    linkedViewList.showViewIndex(5);
                }
                else{
                    linkedViewList.showViewIndex(5,6,7,8,9);
                    linkedViewList.hideViewIndex(5);
                }
                gotoNextVisiblePage();
            }

            private void showPassengerPages() {

            }

            protected int getOptionsPerLine() {
                return 1;
            }
        }.getView();
    }

    private boolean isPassengerTrip() {
        return isDriving == null || !isDriving;
    }

    public static class Hour {
        private int hour;

        public Hour(int hour) {
            this.hour = hour;
        }

        public String getFriendlyHour(){
            if(hour == 0){
                return "12am";
            }
            else if(hour < 12){
                return hour + "am";
            }
            else if(hour == 12){
                return "12pm";
            }
            else{
                return (hour - 12) + "pm";
            }

        }

        public String toTime() {
            return String.format("%d:00", hour);
        }

        public int get() {
            return hour;
        }
    }
    private Hour selectedTime;
    public View pageTime(final Context context) {
        return new QuestionExpectingSingleAnswer<Hour>(context,"Leaving at"){
            @Override
            protected Hour[] getOptions() {
                return new Hour[]{
                        new Hour(6),
                        new Hour(7),
                        new Hour(8),
                        new Hour(9),
                        new Hour(10),
                        new Hour(11),
                        new Hour(12),
                        new Hour(13),
                        new Hour(14),
                        new Hour(15),
                        new Hour(16),
                        new Hour(17),
                        new Hour(18),
                        new Hour(19),
                        new Hour(20),
                        new Hour(21),
                        new Hour(22),
                        new Hour(23),
                        new Hour(0),
                        new Hour(1),
                        new Hour(2),
                        new Hour(3),
                        new Hour(4),
                        new Hour(5)
                };
            }

            @Override
            protected View getItemView(Hour data) {
                TextView textView = Atom.textView(context,data.getFriendlyHour());
                textView.setBackgroundResource(R.drawable.button_accent);
                textView.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
                return textView;
            }

            @Override
            protected void answerSelected(Hour answer) {
                selectedTime = answer;
                gotoNextVisiblePage();

            }

            @Override
            protected int getOptionsPerLine() {
                return 4;
            }
        }.getView();
    }

    private HerbuyCalendar selectedDate;
    public View pageDate(final Context context) {

        return new QuestionExpectingSingleAnswer<HerbuyCalendar>(context,"Travelling On:"){

            @Override
            protected HerbuyCalendar[] getOptions() {
                HerbuyCalendar dateToday = new HerbuyCalendar(System.currentTimeMillis());
                return new HerbuyCalendar[]{
                        dateToday,
                        dateToday.nextDay(),
                        dateToday.nextDay().nextDay(),
                        dateToday.nextDay().nextDay().nextDay(),
                        dateToday.nextDay().nextDay().nextDay().nextDay(),
                        dateToday.nextDay().nextDay().nextDay().nextDay(),
                        dateToday.nextDay().nextDay().nextDay().nextDay().nextDay()
                };
            }

            @Override
            protected View getItemView(HerbuyCalendar data) {
                TextView view = Atom.textView(context,data.getFriendlyDate());
                view.setBackgroundResource(R.drawable.button_accent);
                view.setPadding(Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em());
                return view;
            }

            @Override
            protected void answerSelected(HerbuyCalendar answer) {
                selectedDate = answer;
                gotoNextVisiblePage();

            }
        }.getView();

    }

    private String selectedDestination;


    abstract class QuestionExpectingSingleAnswer<AnswerType>{
        private final AnswerType[] options;
        private String yesOrNoPrefix;
        private Context context;

        public QuestionExpectingSingleAnswer(Context context, String yesOrNoPrefix) {
            this.context = context;
            this.yesOrNoPrefix = yesOrNoPrefix;
            this.options = getOptions();
        }

        protected abstract AnswerType[] getOptions();

        public View getView(){
            TextView yesOrNoPrefix = getQuestionView();


            GridLayout optionList = new GridLayout(context);
            optionList.setColumnCount(getOptionsPerLine());
            for(final AnswerType answer: options){
                final View option = getItemView(answer);
                option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        answerSelected(answer);

                    }
                });
                optionList.addView(option);
            }

            LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                    context,
                    yesOrNoPrefix,
                    optionList
            );
            return wrapper;
        }

        protected TextView getQuestionView() {
            TextView yesOrNoPrefix = Atom.textViewPrimaryBold(context, this.yesOrNoPrefix);
            yesOrNoPrefix.setTextSize(Dp.one_point_5_em());
            return yesOrNoPrefix;
        }

        protected int getOptionsPerLine() {
            return 2;
        }

        protected abstract View getItemView(AnswerType data);

        protected abstract void answerSelected(AnswerType answer) ;

    }

    abstract class QuestionExpectingAnswerAsSingleString extends QuestionExpectingSingleAnswer<String> {
        public QuestionExpectingAnswerAsSingleString(Context context, String yesOrNoPrefix) {
            super(context, yesOrNoPrefix);
        }

        /** can override the appearance of each item in the list */
        public TextView getItemView(String data) {
            final TextView option = itemView(data, context);
            option.setClickable(true);
            return option;
        }
    }
    abstract class QuestionExpectingAnswerAsSingleLocation extends QuestionExpectingAnswerAsSingleString {
        public QuestionExpectingAnswerAsSingleLocation(Context context, String yesOrNoPrefix) {
            super(context, yesOrNoPrefix);
        }

        @Override
        protected String[] getOptions() {
            return new String[]{"Kampala","Mityana","Mubende","Fort Portal"};
        }

        @Override
        protected int getOptionsPerLine() {
            return 3;
        }
    }

    public View pageWhereTo(Context context) {
        return new QuestionExpectingAnswerAsSingleLocation(context,"Am Travelling to:"){
            @Override
            protected void answerSelected(String text) {
                destinationSelected(text);
            }
        }.getView();
    }
    public View pageOrigin(Context context) {
        return new QuestionExpectingAnswerAsSingleLocation(context,"Travelling from:"){
            @Override
            protected void answerSelected(String text) {
                destinationSelected(text);
            }
        }.getView();
    }

    private void destinationSelected(CharSequence text) {
        this.selectedDestination = text.toString();
        gotoNextVisiblePage();
    }

    private View actions() {
        LinearLayout actions = new LinearLayout(context);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        Button btnPrev = Atom.button(context, "PREV", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPreviousVisiblePage();
            }
        });
        actions.addView(btnPrev);
        Button btnNext = Atom.button(context, "NEXT", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNextVisiblePage();

            }
        });
        actions.addView(btnNext);
        return actions;
    }

    public void gotoPreviousVisiblePage() {
        if(linkedViewList.hasPreviousVisibleIndex()){
            linkedViewList.moveToPreviousVisibleIndex();
            refreshContainer();
        }
        else{
            MessageBox.show("Nothing previous",context);
        }
    }

    private void gotoNextVisiblePage() {
        if(linkedViewList.hasNextVisibleIndex()){

            linkedViewList.moveToNextVisibleIndex();
            refreshContainer();
        }
        else{
            MessageBox.show("Nothing next",context);
        }
    }

    public void refreshContainer() {
        Slide slideOut = new Slide();
        slideOut.setSlideEdge(Gravity.RIGHT);
        TransitionManager.beginDelayedTransition(listContainer, slideOut);
        listContainer.removeAllViews();


        Slide slideIn = new Slide();
        slideIn.setSlideEdge(Gravity.LEFT);
        TransitionManager.beginDelayedTransition(listContainer,slideIn);
        listContainer.addView(linkedViewList.getCurrentVisibleView());

    }

    public View getView(){
        return finalContainer;
    }
}
