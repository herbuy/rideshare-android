package client.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import client.ui.display.admin.AdminActivity;
import client.ui.display.admin.KYCActivity;
import client.ui.display.contacts.AddContactsActivity;
import client.ui.display.contacts.ContactsActivity;
import client.ui.display.family.FamilyChatActivity;
import client.ui.display.family.RateTripActivity;
import client.ui.display.family.ReportArrivedActivity;
import client.ui.display.tt_requests.MatchFoundActivity;
import client.ui.display.users.AboutMeActivity;
import client.ui.display.users.chat.ChatActivity;
import com.skyvolt.jabber.FriendsWhoBeenToLocationActivity;
import com.skyvolt.jabber.HomeActivity;
import com.skyvolt.jabber.LDUpdateActivity;
import com.skyvolt.jabber.LocationListActivity;
import com.skyvolt.jabber.LocationProfileActivityGeneric;
import com.skyvolt.jabber.LoginActivity;
import com.skyvolt.jabber.LoginOrSignUpActivity;
import com.skyvolt.jabber.MyTripsActivity;
import com.skyvolt.jabber.OtherTripToDestinationZoomedInActivity;
import com.skyvolt.jabber.OtherTripsToDestinationActivity;

import client.ui.display.ChatSplashScreen;
import client.ui.display.Location.SelectLocationActivity;
import com.skyvolt.jabber.SignUpActivity;
import com.skyvolt.jabber.TTRequestListActivity;
import com.skyvolt.jabber.MyTripLatestActivitiesActivity;
import client.ui.display.Trip.TripDetailsActivity;
import client.ui.display.Trip.TripListActivity;
import com.skyvolt.jabber.TripReactionsActivity;
import com.skyvolt.jabber.TripScheduledSuccessfullyActivity;
import com.skyvolt.jabber.TripTTRequestsReceivedActivity;
import com.skyvolt.jabber.UserTTProfileActivity;
import com.skyvolt.jabber.UsersActivity;

import client.ui.display.Location.AddLocationActivity;
import client.ui.display.Trip.ScheduleTripActivity;
import client.ui.display.users.profile_photo.ChangeProfilePictureActivity;
import core.businessmessages.toServer.ParamsForGetTripActivities;
import core.businessobjects.Location;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import core.businessobjects.User;
import core.businessobjects.UserLoginDetails;
import libraries.JsonEncoder;
import libraries.android.Sdk;
import server.families.CompletedTripsActivity;

public class GotoActivity {
    public static void tripDetails(Context context, String tripId){
        Intent intent = new Intent(
                context,
                TripDetailsActivity.class
        );
        intent.putExtra(IntentExtras.tripId,tripId);
        context.startActivity(intent);
    }

    public static void tripDetails(Context context, Trip trip){
        Intent intent = new Intent(
                context,
                TripDetailsActivity.class
        );
        intent.putExtra(IntentExtras.tripDetails,JsonEncoder.encode(trip));
        context.startActivity(intent);
    }



    public static void users(Context context) {
        context.startActivity(new Intent(
                context,
                UsersActivity.class
        ));

    }

    public static void adminPanel(Context context) {
        context.startActivity(new Intent(
                context,
                AdminActivity.class
        ));

    }

    public static void tripList(Context context) {
        Intent intent = new Intent(context,TripListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);

    }

    public static void home(Context context) {
        context.startActivity(new Intent(
                context,
                HomeActivity.class
        ));
    }

    public static void locationList(Context context) {
        context.startActivity(new Intent(
                context,
                LocationListActivity.class

        ));
    }

    public static void friendsWhoBeenToLocation(Context context, Location location) {
        Intent intent = new Intent(context,FriendsWhoBeenToLocationActivity.class);
        intent.putExtra(IntentExtras.location, JsonEncoder.encode(location));
        context.startActivity(intent);
    }

    public static void chat(Context context, IntentExtras.ChatParameters chatParameters) {
        Intent intent = new Intent(
                context,
                ChatActivity.class
        );

        intent.putExtra(IntentExtras.chatParameters, JsonEncoder.encode(chatParameters));
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    public static void myTrips(Context context) {
        Intent intent = new Intent(
                context,
                MyTripsActivity.class
        );
        context.startActivity(intent);
    }

    public static void tripScheduledSuccessfully(Context context, Trip trip) {
        Intent intent = new Intent(
                context,
                TripScheduledSuccessfullyActivity.class
        );
        intent.putExtra(IntentExtras.tripDetails,JsonEncoder.encode(trip));

        context.startActivity(intent);
    }

    public static void otherTripsToDestinationExceptTripId(Context context,String tripId) {
        Intent intent = new Intent(
                context,
                OtherTripsToDestinationActivity.class
        );

        context.startActivity(intent);
    }

    public static void otherTripToDestinationZoomedIn(Activity context, View sharedElement, String transitionName) {
        /*Intent intent = new Intent(
                context,
                OtherTripToDestinationZoomedInActivity.class
        );

        context.startActivity(intent);*/

        Sdk.StartActivity.singleSharedElement(
                context,
                OtherTripToDestinationZoomedInActivity.class,
                sharedElement
        );
    }

    public static void tripTTRequestsReceived(Context context) {
        Intent intent = new Intent(
                context,
                TTRequestListActivity.class
        );
        context.startActivity(intent);
    }

    public static void tripTTRequestsReceived(Context context, Trip trip) {
        Intent intent = new Intent(
                context,
                TripTTRequestsReceivedActivity.class
        );
        intent.putExtra(IntentExtras.tripDetails,JsonEncoder.encode(trip));
        context.startActivity(intent);
    }

    public static void myTripLatestActivities(Context context, ParamsForGetTripActivities paramsForGetTripActivities) {
        context.startActivity(new Intent(context, MyTripLatestActivitiesActivity.class));
    }

    public static void userProfile(Context context, User user) {
        Intent intent = new Intent(context, UserTTProfileActivity.class);
        intent.putExtra(IntentExtras.user, JsonEncoder.encode(user));
        context.startActivity(intent);
    }

    public static void locationProfileGeneric(Context context, Location location) {
        Intent intent = new Intent(
                context,
                LocationProfileActivityGeneric.class
        );
        intent.putExtra(IntentExtras.location, JsonEncoder.encode(location));
        context.startActivity(intent);
    }

    public static void selectLocation(AppCompatActivity context, int requestCode) {

        context.startActivityForResult(
                new Intent(context, SelectLocationActivity.class),
                requestCode
        );
    }

    public static void selectLocation(AppCompatActivity context, int requestCode, View fromSharedElement, String title) {
        Intent intent = new Intent(context,SelectLocationActivity.class);
        intent.putExtra(IntentExtras.title,title);
        Sdk.StartActivityForResult.singleSharedElement(
                context,
                intent,
                fromSharedElement,
                requestCode
        );

        /*
        context.startActivityForResult(
                new Intent(context, SelectLocationActivity.class),
                requestCode
        );*/
    }

    public static void updateLoginDetails(Context context, UserLoginDetails item) {
        Intent intent = new Intent(
                context, LDUpdateActivity.class
        );
        intent.putExtra(IntentExtras.userLoginDetails,JsonEncoder.encode(item));
        context.startActivity(intent);
    }

    public static void login(Context context) {
        Intent intent = new Intent(
                context, LoginActivity.class
        );

        context.startActivity(intent);
    }

    public static void signUp(Context context) {
        Intent intent = new Intent(
                context, SignUpActivity.class
        );

        context.startActivity(intent);
    }

    public static void loginOrSignup(Context context) {
        Intent intent = new Intent(
                context, LoginOrSignUpActivity.class
        );

        context.startActivity(intent);
    }

    public static void tripReactions(Context context, Trip trip) {
        Intent intent = new Intent(
                context, TripReactionsActivity.class
        );
        intent.putExtra(IntentExtras.tripDetails,JsonEncoder.encode(trip));

        context.startActivity(intent);
    }

    public static void addLocation(AppCompatActivity context) {
        Intent intent = new Intent(
                context, AddLocationActivity.class
        );

        context.startActivityForResult(intent, RequestCodes.ADD_LOCATION);
    }

    public static void scheduleTrip(Context context,String formDisplayParameters) {
        Intent intent = new Intent(
                context, ScheduleTripActivity.class
        );
        intent.putExtra(IntentExtras.formDisplayParameters, formDisplayParameters);
        context.startActivity(intent);
    }

    public static void splashScreen(Context context) {
        context.startActivity(new Intent(context,ChatSplashScreen.class));
    }

    public static void contacts(Context context) {
        context.startActivity(new Intent(context, ContactsActivity.class));
    }

    public static void addContacts(Context context) {
        context.startActivity(new Intent(context, AddContactsActivity.class));
    }

    public static void familyChat(Context context,Family family) {
        Intent intent = new Intent(context, FamilyChatActivity.class);
        intent.putExtra(IntentExtras.family,JsonEncoder.encode(family));
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    public static void reportArrived(Context context, Family family) {
        Intent intent = new Intent(context, ReportArrivedActivity.class);
        intent.putExtra(IntentExtras.family,JsonEncoder.encode(family));
        context.startActivity(intent);
    }

    public static void completedTrips(Context context) {
        Intent intent = new Intent(context, CompletedTripsActivity.class);
        context.startActivity(intent);

    }

    public static void rateTrip(Context context, Family family) {
        Intent intent = new Intent(context, RateTripActivity.class);
        intent.putExtra(IntentExtras.family,JsonEncoder.encode(family));
        context.startActivity(intent);
    }

    public static void customerAnalytics(Context context) {
        Intent intent = new Intent(context, KYCActivity.class);
        context.startActivity(intent);
    }

    public static void matched(Context context) {
        Intent intent = new Intent(context, MatchFoundActivity.class);
        context.startActivity(intent);
    }

    public static void aboutMe(Context context) {

        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }

    public static void changeProfilePic(Context context) {
        Intent intent = new Intent(context, ChangeProfilePictureActivity.class);
        context.startActivity(intent);
    }
}
