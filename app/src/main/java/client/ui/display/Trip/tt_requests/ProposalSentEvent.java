package client.ui.display.Trip.tt_requests;

import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import libraries.ObserverList;

public class ProposalSentEvent {

    public static class TransactionData{
        private Trip proposingTrip;
        private Trip receivingTrip;
        private Proposal sentProposal;

        public TransactionData(Trip proposingTrip, Trip receivingTrip, Proposal sentProposal) {
            this.proposingTrip = proposingTrip;
            this.receivingTrip = receivingTrip;
            this.sentProposal = sentProposal;
        }

        public Trip getProposingTrip() {
            return proposingTrip;
        }

        public Trip getReceivingTrip() {
            return receivingTrip;
        }

        public Proposal getSentProposal() {
            return sentProposal;
        }
    }

    private static ObserverList<TransactionData> observerList = new ObserverList<>();

    public static ObserverList<TransactionData> instance(){
        return observerList;
    }
}
