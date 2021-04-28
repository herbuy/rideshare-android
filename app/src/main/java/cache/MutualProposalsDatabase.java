package cache;

import core.businessobjects.MutualProposal;

public class MutualProposalsDatabase extends AppDatabase<MutualProposal> {

    @Override
    protected Class<MutualProposal> getClassOfT() {
        return MutualProposal.class;
    }

    @Override
    protected final String getTableName() {
        return "mutual_tt_proposals_table";
    }
}
