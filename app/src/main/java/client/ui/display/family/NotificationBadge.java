package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.libraries.HerbuyView;
import resources.Dp;

public class NotificationBadge implements HerbuyView {
    View view;

    private NotificationBadge() {
    }

    public static NotificationBadge where(Context context, int unseenMessageCount) {
        NotificationBadge notificationBadge = new NotificationBadge();
        notificationBadge.init(context, unseenMessageCount);
        return notificationBadge;
    }

    private void init(Context context, int unseenMessageCount) {
        TextView item = new TextView(context);

        item.setBackgroundResource(R.drawable.circle);
        item.setTextColor(Color.WHITE);
        item.setTextSize(Dp.scaleBy(0.7f));
        item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        item.setPadding(Dp.scaleBy(0.4f), Dp.scaleBy(0.2f), Dp.scaleBy(0.4f), Dp.scaleBy(0.2f));
        item.setMinimumWidth(Dp.scaleBy(2f));
        item.setMinimumHeight(Dp.scaleBy(2f));
        item.setGravity(Gravity.CENTER);
        item.setText(String.valueOf(unseenMessageCount));

        view = item;

        if(unseenMessageCount < 1){

            view.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public View getView() {
        return view;
    }
}
