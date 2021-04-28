package test.design_patterns;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Iterator;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.underscore.Singleton;

public abstract class StepIterator implements Iterator<View> {
    Context context;

    public StepIterator(Context context) {
        this.context = context;
        init();
    }

    Singleton<ViewGroup> contentContainer = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout layout = MakeDummy.linearLayoutVertical(context);
            layout.setPadding(24,24,24,24);
            return layout;
        }
    };

    Singleton<ViewGroup> iteratorControls = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout layout = MakeDummy.linearLayoutVertical(context);
            layout.setPadding(24,24,24,24);
            layout.addView(nextButton.instance());
            return layout;
        }
    };

    private Singleton<View> nextButton = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return Atom.button(context,"Next",new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(hasNext()){
                        next();
                    }
                    else{
                        view.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    };

    Singleton<ViewGroup> finalContainer = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(
                    context,
                    contentContainer.instance(),
                    iteratorControls.instance()
            );
        }
    };

    private void init() {

    }
    public View getView(){
        return finalContainer.instance();
    }
}
