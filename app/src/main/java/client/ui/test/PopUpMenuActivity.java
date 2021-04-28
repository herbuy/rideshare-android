package client.ui.test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.ShowPopupWindow;
import resources.Dp;
import shared.BaseActivity;

public class PopUpMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Pop Up Menu Example");

        setContentView(contentView());
    }

    private View contentView() {
        LinearLayout itemList = new LinearLayout(this);
        itemList.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < 5; i++){
            itemList.addView(item());
        }

        return itemList;
    }

    private View item() {
        LinearLayout toolbar = new LinearLayout(this);
        toolbar.addView(view());
        toolbar.addView(like());
        toolbar.addView(comment());
        toolbar.addView(more());

        return toolbar;
    }

    private View view() {
        TextView item = new TextView(this);
        item.setText("VIEW");
        item.setPadding(16, 16, 16, 16);
        return item;
    }
    private View like() {
        TextView item = new TextView(this);
        item.setText("LIKE");
        item.setPadding(16, 16, 16, 16);
        return item;
    }
    private View comment() {
        TextView item = new TextView(this);
        item.setText("COMMENT");
        item.setPadding(16, 16, 16, 16);
        return item;
    }
    private View more() {
        TextView item = new TextView(this);
        item.setText("MORE");
        item.setPadding(16, 16, 16, 16);
        item.setId(new Random().nextInt());
        item.setOnClickListener(onClickShowPopupMenu(item));
        return item;
    }

    private View.OnClickListener onClickShowPopupMenu(final View anchor) {
        return new View.OnClickListener() {
            private View.OnClickListener onClickItemListener(){
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowPopupWindow.dismiss();
                    }
                };
            }
            @Override
            public void onClick(final View sender) {



                ShowPopupWindow.run(
                        makeWindowContent(anchor, onClickItemListener()),
                        anchor
                );




            }

            protected View makeWindowContent(View sender, View.OnClickListener onClickListener) {
                LinearLayout layout = MakeDummy.linearLayoutVertical(PopUpMenuActivity.this);
                String actions[] = new String[]{
                        "Add to Starred", "Share","Make available offline",
                        "Details","Link sharing off","Details","Link sharring off",
                        "Copy Link","Send a copy","Make a copy","Saved as Word",
                        "Rename", "Add shortcut to Drive","Move","Print","Add to Home Screen","Remove"
                };
                for(String action: actions){
                    TextView actionView = Atom.textViewPrimaryNormal(sender.getContext(), action);
                    actionView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
                    layout.addView(actionView);
                    actionView.setOnClickListener(onClickListener);

                }

                ScrollView scrollView = new ScrollView(sender.getContext());
                scrollView.addView(layout);
                return scrollView;
            }


        };
    }
}
