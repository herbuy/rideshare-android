package client.ui.display.user_activities;

import android.view.View;

import core.businessobjects.TripActivity;

//an activity renderer is an object that is capable of rendering different types of activities
//how an activity is rendered depends on the context in which it is being rendered
//for that reason, we define a templare object that is capable of rendering each type of activity
//however, we leave the user to decide how that type of activity is renderee in their context
//that way, we can even switch renders and see how the different objects will be rendered in the different contexts
//we can later define a context object that returns for us the renderer
public abstract class ActivityRenderingContext {

    public View render(TripActivity tripActivity){
        switch (tripActivity.getActivityType()){
            case TripActivity.Type.Trip.RECIPIENT_SCHEDULED_TRIP:
                return recipientScheduledTrip(tripActivity);
            case TripActivity.Type.Trip.ACTOR_SCHEDULED_TRIP:
                return actorScheduledTrip(tripActivity);

            case TripActivity.Type.Trip.ACTOR_SENT_TT_REQUEST:
                return actorSentTTRequest(tripActivity);
            case TripActivity.Type.Trip.RECIPIENT_SENT_TT_REQUEST:
                return youSentTTRequest(tripActivity);

        }
        return unSupportedActivity(tripActivity);
    }

    protected abstract View youSentTTRequest(TripActivity tripActivity);

    protected abstract View actorScheduledTrip(TripActivity tripActivity);

    protected abstract View actorSentTTRequest(TripActivity tripActivity);

    protected abstract View recipientScheduledTrip(TripActivity tripActivity);

    protected abstract View unSupportedActivity(TripActivity tripActivity);
}
