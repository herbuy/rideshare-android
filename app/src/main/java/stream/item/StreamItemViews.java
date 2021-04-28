package stream.item;

import android.content.Context;
import android.view.View;

import core.businessobjects.NewsFeedItem;

public abstract class StreamItemViews {
    protected final Context context;
    protected final NewsFeedItem newsFeedItem;

    public StreamItemViews(Context context, NewsFeedItem newsFeedItem) {
        this.context = context;
        this.newsFeedItem = newsFeedItem;
    }

    public abstract View travelTogether();
    public abstract View defaultView();
}
