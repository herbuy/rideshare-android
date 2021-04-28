package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.RelativeLayoutFactory;
import libraries.android.DisableInterceptingTouchEvents;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class TripInputDialog {

    protected final Context context;
    protected final TripDataInputListener inputListener;

    public TripInputDialog(Context context, TripDataInputListener inputListener) {
        this.context = context;
        this.inputListener = inputListener;
    }

    private final View makeWrapper(String headerText, View content, boolean wrapBodyInScrollView){
        return makeWrapper(headerText,content,wrapBodyInScrollView,null);
    }

    private final View makeWrapper(String headerText, View content, boolean wrapBodyInScrollView, TitleSetCallback callback){
        View header = header(content.getContext(),headerText,callback);

        LinearLayout bodyPadding = MakeDummy.linearLayoutVertical(content.getContext(),content);
        bodyPadding.setBackgroundColor(Color.WHITE);
        bodyPadding.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );
        bodyPadding.setPadding(Dp.normal(),Dp.two_em(),Dp.normal(),0);

        ViewGroup bodyMargin = wrapBodyInScrollView ? makeScrollView(bodyPadding) : MakeDummy.linearLayoutVertical(content.getContext(),bodyPadding);

        bodyMargin.setBackgroundColor(dialogBackgroundColor());
        bodyMargin.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
        bodyMargin.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return RelativeLayoutFactory.alignBelowWidget(
                header,
                bodyMargin
        );
    }


    private ScrollView makeScrollView(LinearLayout bodyPadding) {
        ScrollView scrollView = MakeDummy.scrollView(bodyPadding);
        DisableInterceptingTouchEvents.where(scrollView);
        return scrollView;
    }

    protected final View makeWrapper(String headerText, View content){
        return makeWrapper(headerText,content,false);
    }
    protected final View makeWrapper(String headerText, View content, TitleSetCallback callback){
        return makeWrapper(headerText,content,false,callback);
    }
    protected final View makeWrapperWithScrollableBody(String headerText, View content){
        return makeWrapper(headerText,content,true);
    }

    protected final int dialogBackgroundColor() {
        return Color.parseColor("#e8e8e8");
    }



    protected View header(Context context, String text){
        return header(context,text,null);
    }

    protected View header(Context context, String text, TitleSetCallback callback){

        View finalView = RelativeLayoutFactory.threeColumn(
                backButton.instance().getView(),
                headerTitle(context, text,callback),
                nextButton.instance().getView()
        );

        finalView.setBackgroundColor(ItemColor.primary());
        finalView.setPadding(Dp.one_point_5_em(),0,Dp.one_point_5_em(),0);
        return finalView;

    }


    private TextView headerTitle(Context context, String text) {
        return headerTitle(context,text,null);
    }

    interface TitleSetCallback{
        interface TitleChangerLink {
            void changeTitle(String newTitle);
        }
        void receiveLink(TitleChangerLink changer);
    }

    private TextView headerTitle(Context context, String text, TitleSetCallback callback) {
        final TextView textView = Atom.textViewPrimaryBold(context,text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(Dp.scaleBy(1.2f));
        //textView.setBackgroundColor(Color.parseColor("#444444"));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        if(callback != null){
            callback.receiveLink(new TitleSetCallback.TitleChangerLink() {
                @Override
                public void changeTitle(String newTitle) {
                    textView.setText(Html.fromHtml(newTitle));
                }
            });
        }
        return textView;
    }

    Singleton<IconPlusText> nextButton = new Singleton<IconPlusText>() {
        @Override
        protected IconPlusText onCreate() {
            IconPlusText btnNext = new IconPlusText(context, nextAppearance);
            return btnNext;
        }
    };


    private Singleton<IconPlusText> backButton = new Singleton<IconPlusText>() {
        @Override
        protected IconPlusText onCreate() {
            IconPlusText btn = new IconPlusText(context,backCommandAppearance);
            return btn;
        }
    };

    float navButtonSize = Dp.scaleBy(0.7f);
    int navButtonTextColor = Color.WHITE;
    int navButtonIconSize = Dp.scaleBy(3.5f);

    private IconPlusText.AppearancePlusBehavior nextAppearance = new IconPlusText.AppearancePlusBehavior(){
        @Override
        public String text() {
            return "NEXT";
        }

        @Override
        public int iconResourceId() {
            return R.drawable.bmp_arrow_right_white;
        }
        @Override
        public View.OnClickListener onClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputListener.displayNextPage();
                }
            };
        }
        //------------------------

        @Override
        public float textSize() {
            return navButtonSize;
        }

        @Override
        public int textColor() {
            return navButtonTextColor;
        }

        @Override
        public int iconSize() {
            return navButtonIconSize;
        }

    };

    Singleton<IconPlusText.AppearancePlusBehavior> uploadAppearanceAndBehavior = new Singleton<IconPlusText.AppearancePlusBehavior>() {
        @Override
        protected IconPlusText.AppearancePlusBehavior onCreate() {
            return new IconPlusText.AppearancePlusBehavior(){
                @Override
                public String text() {
                    return "UPLOAD";
                }

                @Override
                public int iconResourceId() {
                    return R.drawable.bmp_arrow_right_white;
                }
                @Override
                public View.OnClickListener onClick() {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //inputListener.displayNextPage();

                        }
                    };
                }
                //------------------------

                @Override
                public float textSize() {
                    return navButtonSize;
                }

                @Override
                public int textColor() {
                    return navButtonTextColor;
                }

                @Override
                public int iconSize() {
                    return navButtonIconSize;
                }

            };
        }
    };

    private IconPlusText.AppearancePlusBehavior backCommandAppearance = new IconPlusText.AppearancePlusBehavior(){
        @Override
        public String text() {
            return "PREV";
        }


        @Override
        public int iconResourceId() {
            return R.drawable.bmp_arrow_left_white;
        }
        @Override
        public View.OnClickListener onClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputListener.displayPreviousPage();
                }
            };
        }
        //------------------------

        @Override
        public float textSize() {
            return navButtonSize;
        }

        @Override
        public int textColor() {
            return navButtonTextColor;
        }

        @Override
        public int iconSize() {
            return navButtonIconSize;
        }

    };

    public abstract View getView();

    public void notifyMovedForward(int position) {
        changeApperanceOfNextButtonIfNecessary();

        if(!inputListener.pageApplicableToCurrentUser(position)){
            inputListener.displayNextPage();
        }
    }

    public void notifyMovedBackwards(int position) {
        changeApperanceOfNextButtonIfNecessary();

        if(!inputListener.pageApplicableToCurrentUser(position)){
            inputListener.displayPreviousPage();
        }

    }

    public void changeApperanceOfNextButtonIfNecessary() {
        nextButton.instance().getView().setVisibility(View.VISIBLE);
        if (inputListener.isOnLastPage()) {

            /*nextButton.instance().setAppearanceAndBehavior(uploadAppearanceAndBehavior.instance());
            if(!inputListener.readyToUpload()){
                nextButton.instance().getView().setVisibility(View.INVISIBLE);
            }*/
            nextButton.instance().getView().setVisibility(View.INVISIBLE);

        } else {
            nextButton.instance().setAppearanceAndBehavior(nextAppearance);
        }

    }

    public boolean appliesToPassenger() {
        return true;
    }
}
