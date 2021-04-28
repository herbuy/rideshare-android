package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.ItemColor;
import shared.BaseActivity;

public abstract class DialogActivity extends BaseActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                contentView()
        );
    }

    View view;
    private View contentView() {
        view = MakeDummy.backgroundColor(
                MakeDummy.linearLayoutVertical(
                        this,
                        getHeader(),
                        getBody()
                )
                ,
                Color.WHITE
        );
        view.setTransitionName(getTransitionName());

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(this,view);
        wrapper.setGravity(Gravity.CENTER_VERTICAL);
        wrapper.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setDimAmount(0);
                onBackPressed();
            }
        });
        return wrapper;
    }

    protected abstract String getTransitionName();


    private View getBody() {
        LinearLayout view = MakeDummy.linearLayoutVertical(
                this,
                getActivityContent()
        );
        view.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }

    private TextView getHeader() {
        TextView header = Atom.textViewPrimaryBold(this, getHeaderText());
        header.setBackgroundColor(ItemColor.primary());
        header.setTextColor(Color.WHITE);
        header.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return header;
    }

    protected abstract String getHeaderText();


    protected abstract View getActivityContent();

    public View getView() {
        return view;
    }

    protected SelectableItem.Padding getPadding(){
        return new SelectableItem.Padding() {
            @Override
            public int left() {
                return Dp.two_em();
            }

            @Override
            public int top() {
                return Dp.normal();
            }

            @Override
            public int right() {
                return Dp.two_em();
            }

            @Override
            public int bottom() {
                return Dp.normal();
            }
        };
    }

    protected final void onBackPressed(View sharedView) {
        getView().setTransitionName("");
        sharedView.setTransitionName(getTransitionName());
        sharedView.setPadding(0,0,0,0);
        getWindow().setDimAmount(0);
        onBackPressed();
    }

}