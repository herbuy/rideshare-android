package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.ItemColor;

public class CreateFamilyClickOptions {
    private Context context;
    private Family family;

    private CreateFamilyClickOptions() {
    }
    public static View where(Context context, Family item){
        CreateFamilyClickOptions instance = new CreateFamilyClickOptions();
        instance.init(context, item);
        return instance.getView();

    }

    private View getView() {
        LinearLayout layout = actions();
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }

    private LinearLayout actions() {
        return family.isCompleted() ? actionForCompletedTrip() : actionsForNonCompletedTrip();
    }

    private LinearLayout actionForCompletedTrip() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                rateCompletedTrip()
        );
        return layout;
    }

    private View rateCompletedTrip() {
        Button btn = Atom.button(context, "Rate", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.rateTrip(context, family);

            }
        });
        return btn;
    }


    private LinearLayout actionsForNonCompletedTrip() {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(
                context,
                viewMessages(),
                space(),
                markAsCompleted()
        );
        layout.setVisibility(View.GONE);
        return layout;
    }

    private View space(){
        return MakeDummy.verticalSeparator(context,Dp.half_em());
    }

    private void init(Context context, Family item) {
        this.context = context;
        this.family = item;
    }

    private View viewMessages() {

        TextView btn = Atom.textViewPrimaryNormal(context, String.format("Messages (%d)",family.getUnseenMessageCount()));
        btn.setTextSize(Dp.scaleBy(0.6f));
        btn.setPadding(8, 8, 8, 8);
        btn.setBackgroundResource(R.drawable.button);
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setTextColor(Color.WHITE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.familyChat(context, family);
            }
        });
        return btn;

    }

    private View markAsCompleted() {
        TextView btn = Atom.textViewPrimaryNormal(context, "Mark as Completed");
        btn.setTextSize(Dp.scaleBy(0.6f));
        btn.setPadding(8, 8, 8, 8);
        btn.setBackgroundResource(R.drawable.chat_edit_text);
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setTextColor(ItemColor.primary());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.reportArrived(context, family);
            }
        });
        return btn;

    }

}
