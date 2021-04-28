package libraries.android;

import android.os.CountDownTimer;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/** useful for delaying a task until the value stops changing.
 * Can be useful in running tasks like validation only after
 * we gauge that data has stopped streaming in for a while now
 * */
public abstract class ValueUnchangedListener<DataType> {
    DataType value;
    Timer timer = new Timer();
    long waitPeriod;

    public ValueUnchangedListener(long waitPeriod) {
        this.waitPeriod = waitPeriod;
    }

    protected abstract void callback(DataType value);

    private Handler handler = new Handler();

    synchronized public void set(final DataType value){
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback(value);
            }
        }, waitPeriod);

    }
}
