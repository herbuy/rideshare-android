package libraries;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * helps in identifying bottlenecks slowing down the app
 *
 */
public class TimeMonitor {


    //a map storing activity name, start time, and end time
    private static class TaskInfo{
        private Long startTime = 0L;
        private Long endTime = 0L;
    }

    static Map<String,TaskInfo> map = new HashMap<>();

    synchronized public static void start(String taskName) {

        TaskInfo info = new TaskInfo();
        info.startTime = System.currentTimeMillis();
        map.put(taskName,info);

    }

    synchronized public static void stop(String taskName){
        if(map.get(taskName) == null){
            throw new RuntimeException("task "+taskName+" does not exist");
        }
        else if(map.get(taskName).endTime != 0){
            throw new RuntimeException("task "+taskName+" already ended");
        }
        map.get(taskName).endTime = System.currentTimeMillis();

        TaskInfo info = map.get(taskName);
        long duration = info.endTime - info.startTime;

        Log.d("__________    FINISHED", String.format("%s (%d ms)", taskName, duration));
    }


}
