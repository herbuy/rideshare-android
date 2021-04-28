package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import layers.render.Atom;
import libraries.android.TextChangedListener;
import libraries.underscore.Singleton;

public abstract class ChangeTextTrigger {
    private Context context;

    public ChangeTextTrigger(Context context) {
        this.context = context;
    }

    public View getView(){
        return view.instance();
    }

    private String currentValue = getDefaultAnswer();
    private Singleton<View> view = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TextInputLayout textInputLayout = Atom.textInputLayout(
                    context,
                    getQuestion(),
                    getDefaultAnswer(),
                    true,
                    null,
                    new TextChangedListener(){
                        @Override
                        public void afterTextChanged(Editable editable) {
                            currentValue = editable.toString();
                        }
                    }
            );
            return textInputLayout;
        }
    };

    protected abstract String getQuestion();
    protected abstract String getDefaultAnswer();


}
