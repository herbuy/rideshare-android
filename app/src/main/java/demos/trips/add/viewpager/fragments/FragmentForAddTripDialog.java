package demos.trips.add.viewpager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;

public abstract class FragmentForAddTripDialog<NewValue> extends Fragment {
    Parameters<NewValue> params;

    public FragmentForAddTripDialog(Parameters<NewValue> params) {
        this.params = params;
    }

    protected final Parameters<NewValue> params() {
        return params;
    }

    public interface Parameters<NewValue>{
        Context getContext();
        void onValueChanged(NewValue newValue);
    }

    LinearLayout view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = MakeDummy.linearLayoutVertical(
                params.getContext(),
                Atom.textView(params.getContext(),question()),
                makePicker()
        );
        view = new LinearLayout(getContext()){
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                MessageBox.show("Attached to window",getContext());
            }

            @Override
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
            }
        };
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.removeAllViews();
    }

    protected abstract View makePicker();

    protected abstract String question();

}
