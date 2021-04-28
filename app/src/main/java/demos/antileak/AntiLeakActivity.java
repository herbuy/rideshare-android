package demos.antileak;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

public class AntiLeakActivity extends Activity {

    private static class HoldingObject extends Handler{
        WeakReference<AntiLeakActivity> weakHolder;

        public HoldingObject(AntiLeakActivity heldObject) {
            this.weakHolder = new WeakReference<>(heldObject);
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //no point in continuing to run tasks for results that will be displayed here
        //remove any subscriptions and listeners

    }
}
