package client.ui.display.family;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;

public class FamilyChatHeader extends FamilyListItem{
    public FamilyChatHeader(Context context, Family item) {
        super(context, item);
    }

    @Override
    protected View getActionBar() {
        TextView invisibleView = Atom.textView(context,"");
        invisibleView.setVisibility(View.GONE);
        return invisibleView;
    }

    @Override
    protected View notificationBadge() {
        return MakeDummy.invisible(Atom.textView(context,"h"));
    }
}
