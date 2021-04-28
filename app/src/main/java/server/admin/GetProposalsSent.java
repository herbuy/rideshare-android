package server.admin;

import java.util.Comparator;

import cache.ProposalDatabase;
import core.businessobjects.Proposal;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetProposalsSent {

    public static int overall(){
        return new ProposalDatabase().count();
    }
    public static int today(){
        return new ProposalDatabase().countWhere(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new ProposalDatabase().countWhere(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new ProposalDatabase().countWhere(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new ProposalDatabase().countWhere(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        Proposal result = new ProposalDatabase().first(new Comparator<Proposal>() {
            @Override
            public int compare(Proposal lhs, Proposal rhs) {
                if (lhs.getTimestampCreatedInMillis() < rhs.getTimestampCreatedInMillis()) {
                    return -1;
                } else if (lhs.getTimestampCreatedInMillis() > rhs.getTimestampCreatedInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ? 0 : result.getTimestampCreatedInMillis();
    }

    public static long timestampMostRecent() {
        Proposal result = new ProposalDatabase().first(new Comparator<Proposal>() {
            @Override
            public int compare(Proposal lhs, Proposal rhs) {
                if (lhs.getTimestampCreatedInMillis() > rhs.getTimestampCreatedInMillis()) {
                    return -1;
                } else if (lhs.getTimestampCreatedInMillis() < rhs.getTimestampCreatedInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ? 0 : result.getTimestampCreatedInMillis();
    }
}
