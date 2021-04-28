package client.ui.display;

import android.content.Context;
import android.view.View;

import client.ui.libraries.HerbuyView;
import core.businessobjects.NewsFeedItem;
import libraries.android.MakeDummy;

public class StreamItemView implements HerbuyView {
    Context context;
    NewsFeedItem newsFeedItem;

    public StreamItemView(Context context, NewsFeedItem newsFeedItem) {
        this.context = context;
        this.newsFeedItem = newsFeedItem;
    }

    @Override
    public View getView() {
        return MakeDummy.textView(context, newsFeedItem.getActivityType() );
    }
}
