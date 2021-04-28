package core.businessobjects;

public class Subjects {
    //*** we can keep a long of all the changes that happened to an account as system, admin, etc

    //account related
    //requested account [account requested]
    public static final String accountRequest = "accountRequest";

    //confirmed account [account confirmed]
    public static final String accountConfirmationRequest = "accountConfirmationRequest";
    public static final String accountConfirmation = "";

    //requested import contacts [import contacts requested]
    public static final String friendsImportRequest = "friendsImportRequest";

    //imported contacts [contacts imported]
    public static final String friendsImportedNotification = "friendsImportedNotification";

    //scheduled a trip [trip scheduled]
    public static final String travelNotification = "travelNotification"; //the wording can change depending on who is viewing it e.g coming yo way

    //requested review [reviewRequested]
    public static final String reviewRequest = "reviewRequest";
    public static final String offerRequest = "offerRequest";

    //DISTRICTS/LOCATIONS
    //if someone has been to a place, we want to getOrientation recommendations or chat with them.
    // That means, we need to display a list of locations, and pple who have been there
    //first step is to choose a district, then go to the details page of that district, see people who have been there and click chat
    public static final String recommendationRequest = "recommendationRequest";
    public static final String destinationInquiry = "destinationInquiry";

    //TRIPS
    //goto list of trips, and send a travel together request, see travel together requests
    public static final String travelTogetherRequest = "travelTogetherRequest";
    public static final String comingYourWayNotification = "comingYourWayNotification";//notifiy departure
    public static final String passingYourWayNotification = "passingYourWayNotification";
    public static final String arrivalInquiry = "arrivalInquiry";

    public static final String safeJourneyMessage = "safeJourneyMessage";
    public static final String welcomeMessage = "welcomeMessage";
    public static final String priceQuotation = "priceQuotation";
    public static final String waitingToSeeYouMessage = "waitingToSeeYouMessage";
    public static final String departureNotification = "departureNotification";

    //SHARED TRIPS [when u share a trip with a message such as departing, arrived e.g x shared a trip saying "departing"

    //TRIP COMMENTS [commented, replied to yo comment]
    public static final String thankYouMessage = "thankYouMessage";

    public static final String arrivalNotification = "arrivalNotification";
    public static final String almostThereNotification = "";
    public static final String AtLocationNotification = "AtLocationNotification";
    public static final String notYetArrivedReply = "notYetArrivedReply";
    public static final String good2KnowMessage = "good2KnowMessage";
    public static final String notYetDeparted = "notYetDeparted";
    public static final String tripRateRequest = "tripRateRequest";

    public static final String tripRating = "tripRating";

    public static final String serviceListingRequest = "tripRateRequest";


    public static String[] getArray(){
        return new String[]{
                /*accountRequest,
                accountConfirmationRequest,
                accountConfirmation,
                friendsImportRequest,
                friendsImportedNotification,
                travelNotification,
                reviewRequest,
                offerRequest,
                recommendationRequest,*/
                travelTogetherRequest,
                /*comingYourWayNotification,
                passingYourWayNotification,
                destinationInquiry,
                safeJourneyMessage,
                welcomeMessage,
                priceQuotation,
                waitingToSeeYouMessage,
                departureNotification,
                thankYouMessage,
                arrivalInquiry,
                arrivalNotification,
                almostThereNotification,
                AtLocationNotification,
                notYetArrivedReply,
                good2KnowMessage,
                notYetDeparted,
                tripRateRequest,
                tripRating,
                serviceListingRequest*/


        };
    }
}
