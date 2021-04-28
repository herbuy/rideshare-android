package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.content.Context;
import android.text.Html;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import layers.render.Atom;
import libraries.ObserverList;
import libraries.underscore.Singleton;

public abstract class ChangeValueTrigger0<ValueType> {
    protected final Context context;

    public ChangeValueTrigger0(Context context) {
        this.context = context;
    }

    public View getView(){
        return view.instance();
    }

    private Singleton<TextInputLayout> view = new Singleton<TextInputLayout>() {
        @Override
        protected TextInputLayout onCreate() {
            final TextInputLayout layout = Atom.textInputLayout(context, question(), "", false, clickListener.instance(), null);
            subscribeToEventBus(valueChangedObserver.instance());
            return layout;
        }


    };

    private Singleton<View.OnClickListener> clickListener = new Singleton<View.OnClickListener>() {
        @Override
        protected View.OnClickListener onCreate() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View clickedView) {
                    onClickChangeValue(view.instance().getEditText());
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
        if(selectedValue == null){
            view.instance().getEditText().setText("");
        }
        else{
            view.instance().getEditText().setText(Html.fromHtml(getDisplayText(eventArgs)));
        }
    }

    protected abstract String getDisplayText(ValueType eventArgs);

    protected abstract String question();
    protected abstract void onClickChangeValue(View sender);
}
