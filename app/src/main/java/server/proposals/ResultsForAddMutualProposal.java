package server.proposals;

import java.util.UUID;

import cache.MutualProposalsDatabase;
import core.businessobjects.MutualProposal;

public class ResultsForAddMutualProposal {

    public ResultsForAddMutualProposal(String tripIdFirstToPropose, String tripIdSecondToPropose) {

        if(!MutualTTProposalExists.where(tripIdFirstToPropose,tripIdSecondToPropose)){

            MutualProposal mutualProposal = new MutualProposal();
            mutualProposal.setRecordId(UUID.randomUUID().toString());
            mutualProposal.setTripIdFirstToPropose(tripIdFirstToPropose);
            mutualProposal.setTripIdSecondToPropose(tripIdSecondToPropose);
            mutualProposal.setTimestampCreatedInMillis(System.currentTimeMillis());
            new MutualProposalsDatabase().save(mutualProposal.getRecordId(), mutualProposal);
        }

    }
}
