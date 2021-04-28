package server.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import cache.FamilyDatabase;
import cache.TripDatabase;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;
import server.Enrich;

public class Top10Users {
    public Map<String, Integer> bySearchesConducted() {
        //from searches
        //group by searcher, count(ID) -- reduce by key
        //order by value
        //take top 10


        Map<String, Integer> results = new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip line) {
                Enrich.trip(line,null);
                return line.getForUserName();
            }
        });


        return sortMap(results);

    }

    public Map<String, Integer> byRidesShared() {
        //from searches
        //group by searcher, count(ID) -- reduce by key
        //order by value
        //take top 10


        Map<String, Integer> results = new FamilyDatabase().countByKey(new LocalDatabase.CountByKey<Family>() {
            @Override
            public String getKey(Family line) {
                Enrich.family(line,null);
                return line.getDriverName();
            }
        });


        return sortMap(results);

    }

    private LinkedHashMap<String,Integer> sortMap(Map<String, Integer> results) {
        LinkedList<Map.Entry<String, Integer>> linkedList = new LinkedList<>(results.entrySet());

        Collections.sort(linkedList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                if (lhs.getValue() > rhs.getValue()) {
                    return -1;
                } else if (lhs.getValue() < rhs.getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
        for(Map.Entry<String,Integer> item : linkedList){
            sortedHashMap.put(item.getKey(),item.getValue());
        }
        return sortedHashMap;
    }
}
