package client.ui;

import core.businessobjects.Proposal;

//this class is intended for test what would happen if the value of something was set to something
//the reason we put the methods in one class is becase we want to be able to see
//all instances where a mock method is being used so that we can remove when not needed
public class Mock {
    public static void setRequestStatus(Proposal proposal, String requestStatus){
        proposal.setProposalStatus(requestStatus);
    }
}
