package demos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

import layers.render.Atom;
import libraries.android.MakeDummy;

public class FragmentDemo {

    public View getView(Context context, FragmentManager mgr){
        LinearLayout container = MakeDummy.linearLayoutVertical(context);
        container.setId(Math.abs(new Random().nextInt()));

        FragmentTransaction trans = mgr.beginTransaction();
        Fragment frag = new MyFrag(context);
        trans.add(container.getId(),frag,"Frag 1");
        trans.commit();

        return container;
    }

    public static class MyFrag extends Fragment {
        private Context context;

        public MyFrag(Context context) {
            this.context = context;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return Atom.centeredText(context,"FRAG 1");
        }
    }
}
