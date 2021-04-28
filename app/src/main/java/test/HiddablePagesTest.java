package test;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import layers.render.Atom;
import libraries.SlideList;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public class HiddablePagesTest {
    private Context context;

    public HiddablePagesTest(Context context) {
        this.context = context;
    }

    SlideList slideList = makeSlideList();

    private SlideList makeSlideList() {
        return new SlideList(context);
    }


    public View getView(){
        return RelativeLayoutFactory.alignAboveWidget(
                slideList.getView(),
                actions()
        );

    }

    private View actions() {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(
                context,
                add(),
                hideOne(),
                showOne(),
                next(),
                back()
        );
        layout.setPadding(24,24,24,24);
        return layout;
    }


    private View add() {
        TextView btn = Atom.buttonTextView(context, "+", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView item = Atom.textView(context,"ITEM "+ slideList.getCount());
                item.setBackgroundColor(Color.parseColor("#ffffcc"));
                item.setPadding(24,24,24,24);

                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageBox.show("clicked",context);
                    }
                });
                slideList.add(item);
            }
        });
        btn.setTextSize(Dp.scaleBy(0.6f));
        return btn;
    }

    private View hideOne() {
        TextView btn = Atom.buttonTextView(context, "Hi 1", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideList.animateHide(1);
            }
        });
        btn.setTextSize(Dp.scaleBy(0.6f));
        return btn;
    }
    private View showOne() {
        TextView btn = Atom.buttonTextView(context, "Sho 1", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideList.animateShow(1);
            }
        });
        btn.setTextSize(Dp.scaleBy(0.6f));
        return btn;
    }

    private View next() {
        TextView btn = Atom.buttonTextView(context, "->", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideList.animateToNext();
            }
        });
        btn.setTextSize(Dp.scaleBy(0.6f));
        return btn;
    }

    private View back() {
        TextView btn = Atom.buttonTextView(context, "<-", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideList.animateToPrevious();
            }
        });
        btn.setTextSize(Dp.scaleBy(0.6f));
        return btn;
    }
}
