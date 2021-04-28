package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;


import android.content.Context;
import android.graphics.Color;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class IconPlusText {
    private Context context;
    LinearLayout btn;

    public IconPlusText(Context context, AppearancePlusBehavior appearancePlusBehavior) {
        this.context = context;
        btn = MakeDummy.linearLayoutVertical(context);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setAppearanceAndBehavior(appearancePlusBehavior);
    }

    private View text() {
        TextView textView = Atom.textView(context,appearancePlusBehavior.text());
        textView.setTextSize(appearancePlusBehavior.textSize());
        textView.setPadding(0,0,0,Dp.normal());
        textView.setTextColor(appearancePlusBehavior.textColor());

        return textView;
    }

    private View icon() {
        ImageView imageView = MakeDummy.imageView(
                context,
                appearancePlusBehavior.iconSize(),
                appearancePlusBehavior.iconSize(),
                appearancePlusBehavior.iconResourceId()
        );
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }


    private AppearancePlusBehavior appearancePlusBehavior = new AppearancePlusBehavior() {
        @Override
        public String text() {
            return "TEXT";
        }

        @Override
        public float textSize() {
            return Dp.scaleBy(0.7f);
        }

        @Override
        public int textColor() {
            return Color.WHITE;
        }

        @Override
        public int iconResourceId() {
            return R.drawable.bmp_arrow_right_white;
        }

        @Override
        public int iconSize() {
            return Dp.scaleBy(3.5f);
        }

        @Override
        public View.OnClickListener onClick() {
            return null;
        };
    };
    public IconPlusText setAppearanceAndBehavior(AppearancePlusBehavior appearancePlusBehavior) {
        this.appearancePlusBehavior = appearancePlusBehavior;
        updateContent();
        return this;
    }

    private void updateContent() {
        //TransitionManager.beginDelayedTransition(btn);
        btn.removeAllViews();
        btn.addView(content());
    }

    private View content() {
        LinearLayout content = MakeDummy.linearLayoutVertical(context);
        content.addView(icon());
        content.addView(text());
        content.setOnClickListener(appearancePlusBehavior.onClick());
        return content;
    }

    public View getView() {
        return btn;
    }

    public interface AppearancePlusBehavior {

        String text();
        float textSize();
        int textColor();

        int iconResourceId();
        int iconSize();
        View.OnClickListener onClick();
    }
}
