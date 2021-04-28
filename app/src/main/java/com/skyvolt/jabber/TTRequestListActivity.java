package com.skyvolt.jabber;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.IntentExtras;
import client.ui.display.TTRequestView;
import client.ui.display.tt_requests.TTRequestCollections;
import client.ui.libraries.HerbuyListView;
import core.businessmessages.toServer.ParamsForAcceptTTRequest;
import core.businessmessages.toServer.ParamsForCancelTTRequest;
import core.businessmessages.toServer.ParamsForRejectTTRequest;
import core.businessmessages.toServer.ParamsForGetTTRequests;
import core.businessobjects.Proposal;
import shared.BaseActivity;


public class TTRequestListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Travel Requests");

        setContentView(new TTRequestCollections(this).getView());


    }

    private View contentView2() {
        return null;
    }

    private View contentView() {
        //make ttrequest list view
        HerbuyListView<Proposal> ttRequestListView = new HerbuyListView<Proposal>(this) {
            @Override
            protected View createItemView(Proposal item) {
                TTRequestView ttRequestView = new TTRequestView(TTRequestListActivity.this, item) {
                    @Override
                    protected void onSelectCancelTTRequest(TTRequestView sender, Proposal ttRequest) {
                        Rest.api().cancelTTRequest(new ParamsForCancelTTRequest()).enqueue(sender.getCallbackForCancelTTRequest());

                    }

                    @Override
                    protected void onSelectAcceptTTRequest(TTRequestView sender, Proposal ttRequest) {
                        Rest.api().acceptTTRequest(new ParamsForAcceptTTRequest()).enqueue(sender.getCallbackForAcceptTTRequest());
                    }

                    @Override
                    protected void onSelectRejectTTRequest(TTRequestView sender, Proposal ttRequest) {
                        Rest.api().rejectTTRequest(new ParamsForRejectTTRequest()).enqueue(sender.getCallbackForRejectTTRequest());
                    }

                    @Override
                    protected void onSelectChat(IntentExtras.ChatParameters chatParameters) {
                        GotoActivity.chat(TTRequestListActivity.this, chatParameters);
                    }
                };

                return ttRequestView.getView(); //has support for accept if target, or cancel if send
                //return null;
            }
        };

        //specify the trip id
        Rest.api().getTTRequests(new ParamsForGetTTRequests()).enqueue(ttRequestListView);
        return ttRequestListView.getView();
    }
}
