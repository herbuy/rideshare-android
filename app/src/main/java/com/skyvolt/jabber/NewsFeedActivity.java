package com.skyvolt.jabber;

import android.os.Bundle;
import android.view.View;

import client.data.Rest;
import client.ui.display.StreamItemView;
import client.ui.libraries.HerbuyListView;
import core.businessobjects.NewsFeedItem;
import libraries.android.MakeDummy;
import resources.Dp;
import shared.BaseActivity;

public class NewsFeedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
    }

    private View contentView() {
        HerbuyListView<NewsFeedItem> streamItemListView = new HerbuyListView<NewsFeedItem>(this) {
            @Override
            protected View createItemView(NewsFeedItem item) {
                StreamItemView streamItemView = new StreamItemView(NewsFeedActivity.this,item);
                return streamItemView.getView();
            }
        };
        Rest.api().getStreamItems().enqueue(streamItemListView);
        return MakeDummy.linearLayoutVertical(
                this,
                MakeDummy.textSize(MakeDummy.textView(this, "Stream Activity"), Dp.scaleBy(1.5f)),
                streamItemListView.getView()
        );
    }
}
