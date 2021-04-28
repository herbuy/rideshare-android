package server.proposals;

import java.util.List;

import cache.ProposalDatabase;
import core.businessobjects.Proposal;
import libraries.data.LocalDatabase;

//given two trips, returns proposals sent from one trip to another
public class ProposalsFromTripToTrip {
    private boolean notFound = false;
    private List<Proposal> proposals;

    public ProposalsFromTripToTrip(final String fromTripId, final String toTripId){
        List<Proposal> matchingProposals = new ProposalDatabase().select(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal proposal) {
                return proposal.isFromTrip(fromTripId) && proposal.isToTrip(toTripId);
            }
        });
        if(matchingProposals == null || matchingProposals.size() < 1){
            notFound = true;
        }
        else{
            proposals = matchingProposals;
        }
    }

    public boolean isNotFound() {
        return notFound;
    }


    public List<Proposal> getProposals() {
        return proposals;
    }

    public boolean isFound() {
        return !isNotFound();
    }
}
