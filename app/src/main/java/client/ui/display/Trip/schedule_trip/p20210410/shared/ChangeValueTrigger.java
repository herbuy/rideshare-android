package client.ui.display.Trip.schedule_trip.p20210410.shared;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.skyvolt.jabber.R;

import layers.render.Atom;
import libraries.ObserverList;
import libraries.android.AnimateBackgroundColorThenBack;
import libraries.android.MakeDummy;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class ChangeValueTrigger<ValueType> {
    protected final Context context;

    public ChangeValueTrigger(Context context) {
        this.context = context;
    }

    public View getView(){
        return view.instance();
    }

    private Singleton<LinearLayout> view = new Singleton<LinearLayout>() {
        @Override
        protected LinearLayout onCreate() {

            LinearLayout paddingBox = MakeDummy.linearLayoutVertical(
                    context,
                    label.instance(),
                    editText.instance()
            );

            paddingBox.setPadding(Dp.four_em(),Dp.normal(),Dp.four_em(),Dp.normal());
            //paddingBox.setBackground(MakeStateListDrawable.where(Color.WHITE,ItemColor.highlight()));


            paddingBox.setClickable(true);
            paddingBox.setOnClickListener(clickListener.instance());
            paddingBox.setBackgroundResource(R.drawable.border_bottom);




            LinearLayout container = MakeDummy.linearLayoutVertical(context,paddingBox);
            //container.setBackgroundResource(R.drawable.border_bottom);


            subscribeToEventBus(valueChangedObserver.instance());

            //return paddingBox;
            return container;
        }


    };

    private Singleton<TextView> label = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView label = Atom.textView(context,question());
            return label;
        }
    };

    private Singleton<TextInputEditText> editText = new Singleton<TextInputEditText>() {
        @Override
        protected TextInputEditText onCreate() {
            TextInputEditText editText = new TextInputEditText(context);
            editText.setText(Html.fromHtml(""));
            editText.setFocusable(false);
            editText.setBackgroundColor(Color.TRANSPARENT);
            editText.setPadding(0,0,0,0);
            editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setVisibility(View.GONE);
            editText.setOnClickListener(clickListener.instance());
            return editText;
        }
    };


    private Singleton<View.OnClickListener> clickListener = new Singleton<View.OnClickListener>() {
        @Override
        protected View.OnClickListener onCreate() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View clickedView) {

                    AnimateBackgroundColorThenBack.where(Color.WHITE, ItemColor.highlight(), view.instance(), null);
                    onClickChangeValue(editText.instance());



                    //onClickChangeValue(editText.instance());
                }
            };
        }
    };

    protected abstract void subscribeToEventBus(ObserverList.Observer<ValueType> calllback);
    private ValueType selectedValue;

    private Singleton<ObserverList.Observer<ValueType>> valueChangedObserver = new Singleton<ObserverList.Observer<ValueType>>() {
        @Override
        protected ObserverList.Observer<ValueType> onCreate() {
            return new ObserverList.Observer<ValueType>() {
                @Override
                public void notifyEvent(ValueType eventArgs) {
                    updateValue(eventArgs);
                }
            };
        }
    };

    private void updateValue(ValueType eventArgs) {
        selectedValue = eventArgs;
        if(isNullOrEmpty(eventArgs)){
            editText.instance().setVisibility(View.GONE);
            editText.instance().setText("");
        }
        else{
            editText.instance().setVisibility(View.VISIBLE);
            editText.instance().setText(Html.fromHtml(getDisplayText(eventArgs)));
        }
    }

    protected boolean isNullOrEmpty(ValueType eventArgs) {
        return eventArgs == null;
    }

    protected abstract String getDisplayText(ValueType eventArgs);

    protected abstract String question();
    protected abstract void onClickChangeValue(View sender);

}
