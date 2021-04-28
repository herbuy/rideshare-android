package client.ui.display.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.businessobjects.Trip;
import libraries.UnixTimestampInMillis;

/**
 * Returns the distance between two trips, based on the following factors
 * Assuming matching Distance between...
 * 1. from
 * 1. trip dates
 * 2. trips times
 */
public class TripRelevancyScore {
    private final Trip tripBeingViewed;
    private final Trip viewingTrip;

    public TripRelevancyScore(Trip tripBeingViewed, Trip viewingTrip) {
        this.tripBeingViewed = tripBeingViewed;
        this.viewingTrip = viewingTrip;
    }

    public double get(){
        //1 free mark to make sure log is possible

        double totalScore = 0
                + fromOrigin()
                + fromLocality()
                + fromGPS()

                + toDestination()
                + toLocality()
                + toGPS()

                + tripDate()
                + tripTime()
                + rating()

                + timestampLastUpdated()
                + timestampCreated()
                + timestampLastSeen()
                + passingVia()
                + profilePic()
                + unseenMessageCount()
                ;

        return totalScore;

    }

    private float rating() {
        return 10 * Math.max(0,Math.min(5,tripBeingViewed.getUserRatingOverall())) / 5;
    }

    private double unseenMessageCount() {
        return 10 / Math.log10(10 + Math.abs(tripBeingViewed.getUnseenMessageCount()));
    }

    private int profilePic() {
        return tripBeingViewed.hasProfilePic() ? 10 : 0;
    }

    private float passingVia(){
        return 10 * tanimotoPassingVia();
    }

    private float tanimotoPassingVia() {
        Set<String> passingViaForTripBeingViewed = new HashSet(Arrays.asList(tripBeingViewed.getPassingViaArray()));
        Set<String> passingViaForViewingTrip = new HashSet<>(Arrays.asList(viewingTrip.getPassingViaArray()));


        //construct the union set
        Set<String> union = new HashSet<>();
        union.addAll(passingViaForTripBeingViewed);
        union.addAll(passingViaForViewingTrip);

        // construct the intersection set
        List<String> intersection = new ArrayList<>();

        for(String item : passingViaForTripBeingViewed){
            if(passingViaForViewingTrip.contains(item)){
                intersection.add(item);
            }
        }




        return union.size() < 1 ? 0 : intersection.size() / union.size();

    }

    private double tripDate() {
        long dateForTripBeingViewed = UnixTimestampInMillis.fromDate(tripBeingViewed.getTripDate());
        long dateForViewingTrip = UnixTimestampInMillis.fromDate(viewingTrip.getTripDate());
        return 30 / Math.log10(30 + Math.abs(dateForTripBeingViewed - dateForViewingTrip));
    }

    private double tripTime() {
        long timeForTripBeingViewed = UnixTimestampInMillis.fromTime(tripBeingViewed.getTripTime());
        long timeForViewingTrip = UnixTimestampInMillis.fromTime(viewingTrip.getTripTime());
        return 15 / Math.log10(15 + Math.abs(timeForTripBeingViewed - timeForViewingTrip));
    }

    private double timestampLastUpdated() {

        return 10 / Math.log10(10 + Math.abs(tripBeingViewed.getTimestampLastUpdatedInMillis() - System.currentTimeMillis()));
    }

    private double timestampCreated() {

        return 10 / Math.log10(10 + Math.abs(tripBeingViewed.getTimestampCreatedInMillis() - System.currentTimeMillis()));
    }

    private double timestampLastSeen() {

        return 10 / Math.log10(10 + Math.abs(tripBeingViewed.getForUserTimestampLastSeenMillis() - System.currentTimeMillis()));
    }

    private double fromGPS() {
        double distance = Math.sqrt(
                0
                        + Math.pow((tripBeingViewed.getFromLatitude() - viewingTrip.getFromLatitude()), 2)
                        + Math.pow((tripBeingViewed.getFromLongtitude() - viewingTrip.getFromLongtitude()), 2)
        );

        return 40 / (1 + distance);
    }

    private double toGPS() {
        double distance = Math.sqrt(
                0
                        + Math.pow((tripBeingViewed.getToLatitude() - viewingTrip.getToLatitude()), 2)
                        + Math.pow((tripBeingViewed.getToLongtitude() - viewingTrip.getToLongtitude()), 2)
        );

        return 40 / (1 + distance);
    }

    private float fromOrigin() {
        return tripBeingViewed.isFromSameOrigin(viewingTrip) ? 10 : 0;
    }

    private float toDestination() {
        return tripBeingViewed.isToSameDestination(viewingTrip) ? 10 : 0;
    }

    private float fromLocality() {
        return tripBeingViewed.isFromSameLocality(viewingTrip) ? 10 : 0;
    }

    private float toLocality() {
        return tripBeingViewed.isToSameLocality(viewingTrip) ? 10 : 0;
    }


    private boolean isSame(String value1, String value2) {
        return
                value1 != null
                        && !value1.trim().equalsIgnoreCase("")
                        && value2 != null
                        && !value2.trim().equalsIgnoreCase("")
                        && value1.trim().equalsIgnoreCase(value2.trim())
                ;
    }
}
