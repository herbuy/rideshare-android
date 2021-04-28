package server.proposals;

import cache.MutualProposalsDatabase;
import core.businessobjects.MutualProposal;
import libraries.data.LocalDatabase;

public class MutualTTProposalExists {
    public static boolean where(final String tripId1, final String tripId2) {

        MutualProposal mutualProposal = new MutualProposalsDatabase().selectFirst(new LocalDatabase.Where<MutualProposal>() {
            @Override
            public boolean isTrue(MutualProposal proposal) {
                return (
                        proposal.firstToProposeIsTripId(tripId1)
                        && proposal.secondToProposeIsTripId(tripId2)
                )
                        ||
                        (
                                proposal.firstToProposeIsTripId(tripId2)
                                        && proposal.secondToProposeIsTripId(tripId1)
                        )
                        ;
            }
        });

        return mutualProposal != null;
    }
}
