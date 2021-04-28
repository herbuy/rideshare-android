package client.ui.listeners;

import core.businessmessages.toServer.ParamsForSendTTProposal;

public interface ListenerForSubmitTravelTogetherRequest {
    void run(ParamsForSendTTProposal requestParameters);
}
