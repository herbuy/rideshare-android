package com.skyvolt.jabber;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.display.Trip.OtherTripToDestinationZoomedView;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessobjects.Proposal;
import libraries.android.HideActionBar;
import shared.BaseActivity;

public class OtherTripToDestinationZoomedInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideActionBar.run(this);
        setContentView(contentView());

    }

    private View contentView() {
        OtherTripToDestinationZoomedView view = new OtherTripToDestinationZoomedView(this) {
            @Override
            protected void onSendTTRequest(ParamsForSendTTProposal paramsForSendTTRequest, AppCallback<List<Proposal>> callbackForSendTTRequest) {
                Rest.api().sendProposal(paramsForSendTTRequest).enqueue(callbackForSendTTRequest);

            }

            @Override
            protected void onViewTTRequests() {
                GotoActivity.tripTTRequestsReceived(OtherTripToDestinationZoomedInActivity.this);

            }

            @Override
            protected void onGoBack() {
                onBackPressed();
            }
        };


        return view.getView();
    }


}
