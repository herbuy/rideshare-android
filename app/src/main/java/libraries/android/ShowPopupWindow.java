package libraries.android;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ShowPopupWindow {
    private static PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public static void run(View content, View anchor){
        if(content.getBackground() == null ){
            content.setBackgroundColor(Color.parseColor("#ffffcc"));
        }

        content.setMinimumHeight(content.getResources().getDisplayMetrics().heightPixels);

        popupWindow.setContentView(content);
        popupWindow.showAsDropDown(anchor);

    }
    public static void dismiss(){
        popupWindow.dismiss();
    }

}
