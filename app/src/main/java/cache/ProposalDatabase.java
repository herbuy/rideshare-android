package cache;

import core.businessobjects.Proposal;

public class ProposalDatabase extends AppDatabase<Proposal> {

    @Override
    protected Class<Proposal> getClassOfT() {
        return Proposal.class;
    }

    @Override
    protected final String getTableName() {
        return "tt_requests_table";
    }
}
