package client.ui.display.family;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;

import core.businessobjects.Family;
import libraries.android.MakeDummy;
import libraries.android.HerbuyViewGroup;
import resources.Dp;

//could even pass in the family just, so that we add the logic for updating the family
//updating a family list is a single responsibility even if it could be initiated by so many events and status changes
public class FamilyListUpdater3 extends FamilyListUpdater2 {
    public FamilyListUpdater3(Context context) {
        super(context);
    }

    @Override
    protected final View listOfFamilies(List<Family> body) {
        HerbuyViewGroup<Family> listViewer = new HerbuyViewGroup<Family>(context) {
            @Override
            protected ViewGroup createAbsListView(Context context) {
                return MakeDummy.linearLayoutVertical(context);
            }

            @Override
            protected View createItemView(Context context, Family item) {
                return MakeDummy.linearLayoutVertical(
                        context,
                        new FamilyListItem(context, item).getView(),
                        MakeDummy.lineSeparator(context, Dp.scaleBy(0.5f))
                );
            }
        };

        listViewer.setComparator(new Comparator<Family>() {
            @Override
            public int compare(Family lhs, Family rhs) {
                if(lhs.getTimestampLastUpdatedInMillis() > rhs.getTimestampLastUpdatedInMillis()){
                    return -1;
                }
                else if(lhs.getTimestampLastUpdatedInMillis() < rhs.getTimestampLastUpdatedInMillis()){
                    return 1;
                }
                return 0;
            }
        });
        listViewer.setContent(body);
        return listViewer.getView();

    }


}
