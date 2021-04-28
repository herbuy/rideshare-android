package client.ui;

import android.content.Context;
import android.view.View;

import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class Molecule {
    public static View actionPanelItem(Context context,String primaryText, String secondaryText, boolean transparent, View child) {
        View circle = transparent ? Atom.circle_transparent(context, child) : Atom.circle_opaque(context,child);

        return MakeDummy.linearLayoutHorizontal(
                context,
                circle,
                MakeDummy.verticalSeparator(context, Dp.normal()),

                MakeDummy.linearLayoutVertical(
                        context,
                        Atom.textViewPrimaryNormal(context, primaryText),
                        Atom.textViewSecondary(context, secondaryText)
                )


        );
    }
}
