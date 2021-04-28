package client.ui.libraries;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.GridView;

public abstract class HerbuyGridView<ItemDataType> extends HerbuyAbsListView<ItemDataType> {
    public HerbuyGridView(Context context) {
        super(context);
    }

    //parameters for creating the gridview
    public static class Parameters{
        public int numColumns;
        public int horizontalSpacing;
        public int verticalSpacing;

        public Parameters() {
        }

        public Parameters(int numColumns, int horizontalSpacing, int verticalSpacing) {
            this.numColumns = numColumns;
            this.horizontalSpacing = horizontalSpacing;
            this.verticalSpacing = verticalSpacing;
        }

        public static Parameters create(int numColumns, int horizontalSpacing, int verticalSpacing){
            return new Parameters(numColumns,horizontalSpacing,verticalSpacing);
        }
    }
    @Override
    protected AbsListView makeAbsListView() {

        Parameters parameters = getGridParameters();
        GridView gridView = new GridView(getContext());
        gridView.setNumColumns(parameters.numColumns);
        gridView.setHorizontalSpacing(parameters.horizontalSpacing);
        gridView.setVerticalSpacing(parameters.verticalSpacing);

        return gridView;
    }

    protected abstract Parameters getGridParameters();
}
