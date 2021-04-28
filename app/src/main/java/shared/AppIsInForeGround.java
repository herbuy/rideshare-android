package shared;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

//we opted for this implementation bse it seems the stop lifecycle event
//is called after starting another activity
//which could cause bugs in the sense that
//if first activity is started, and a second activity lauchned from the first,
//the sequence of events that will happen is:-
//start 1 -> start 2 -> stop 1
//this means than we end in a state of 'false'
//which can wrongly indicate that our app is in background.
//by tracking the foreground state of each activity separately,
//an activity going in background will not erase the data of other activities
//so to check if the app is in the foreground,
//we just have to check that there exists an activity
//that is in the foreground
//if all activities are in the background,
//then it will return false.
public class AppIsInForeGround {
    private static final Map<Class<? extends Activity>,Boolean> states = new HashMap<>();
    synchronized public static void set(Class<? extends Activity> activityClass, boolean isInForeground) {
        states.put(activityClass,isInForeground);
    }

    public static boolean isTrue(){
        for(Class<? extends Activity> currentClass : states.keySet()){
            if(states.get(currentClass)){
                return true;
            }
        }
        return false;
    }
}
