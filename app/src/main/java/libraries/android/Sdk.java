package libraries.android;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;


public class Sdk {
    public static void setElevation(int elevation, View button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setElevation(elevation);
        }
    }

    public static void setTransitionName(View view, String transitionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setTransitionName(transitionName);
        }
    }

    public static ArrayList<Pair<View, String>> createPairs(Activity activity) {
        final ArrayList<Pair<View, String>> pairs = new ArrayList<>();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View statusBar = activity.findViewById(android.R.id.statusBarBackground);
            if(statusBar != null){
                pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
            }

            View navigationBar = activity.findViewById(android.R.id.navigationBarBackground);
            if(navigationBar != null){
                pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
            }
        }

        return pairs;
    }

    private static Bundle bundle(Activity fromActivity, View sharedElement, String transitionName) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
                fromActivity, sharedElement, transitionName

        ).toBundle();
    }


    public static class StartActivity {

        public static void noTransition(Activity fromActivity, Class<?> toClass) {
            fromActivity.startActivity(
                    new Intent(fromActivity, toClass)
            );
        }

        public static void singleSharedElement(Activity fromActivity, Class<?> toClass, View sharedView){
            Intent i = new Intent(fromActivity, toClass);

            String transitionName = sharedView.getTransitionName();

            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(fromActivity, sharedView, transitionName);
            fromActivity.startActivity(i, transitionActivityOptions.toBundle());
        }

        public static void multipleSharedElements(Activity fromActivity, Class<?> toClass, Pair<View, String>... sharedElements){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fromActivity.startActivity(
                        new Intent(fromActivity, toClass),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                fromActivity,
                                sharedElements

                        ).toBundle()
                );
            }
            else{
                noTransition(fromActivity, toClass);
            }


        }

        public static void noSharedElement(Activity fromActivity, Class<?> toClass){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(fromActivity, toClass);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                fromActivity.startActivity(
                        intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(fromActivity).toBundle()
                );
            }
            else{
                noTransition(fromActivity, toClass);
            }


        }



    }


    public static class StartActivityForResult {

        public static void noTransition(Activity fromActivity, Class<?> toClass, int requestCode) {
            fromActivity.startActivityForResult(
                    new Intent(fromActivity, toClass), requestCode
            );
        }

        public static void noTransition(Activity fromActivity, Intent intent, int requestCode) {

            fromActivity.startActivityForResult(
                    intent, requestCode
            );
        }

        public static void singleSharedElement(Activity fromActivity,Intent intent, View sharedElement, int requestCode){


            //16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fromActivity.startActivityForResult(
                        intent,
                        requestCode,
                        bundle(fromActivity, sharedElement, sharedElement.getTransitionName())
                );
            }
            else{
                noTransition(fromActivity, intent, requestCode);
            }


        }


        public static void singleSharedElement(Activity fromActivity, Class<?> toClass, View sharedElement, int requestCode){


            //16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fromActivity.startActivityForResult(
                        null,
                        requestCode,
                        bundle(fromActivity, sharedElement, sharedElement.getTransitionName())
                );
            }
            else{
                noTransition(fromActivity, toClass, requestCode);
            }


        }


        public static void multipleSharedElements(Activity fromActivity, Class<?> toClass,int requestCode, Pair<View, String>... sharedElements){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fromActivity.startActivityForResult(
                        new Intent(fromActivity, toClass),
                        requestCode,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                fromActivity,
                                sharedElements

                        ).toBundle()
                );
            }
            else{
                noTransition(fromActivity, toClass,requestCode);
            }


        }

        public static void noSharedElement(Activity fromActivity, Class<?> toClass, int requestCode){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(fromActivity, toClass);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                fromActivity.startActivityForResult(
                        intent,
                        requestCode,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(fromActivity).toBundle()
                );
            }
            else{
                noTransition(fromActivity, toClass, requestCode);
            }


        }



    }

    public static class ShowActivity{
        public static void fadeIn(Activity incomingActivity){




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Fade enterTransition = new Fade();
                enterTransition.setDuration(400);
                incomingActivity.getWindow().setEnterTransition(enterTransition);
            }


        }

        //====================
        private static void slideInFrom(int side,Activity incomingActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Slide enterTransition = new Slide();
                enterTransition.setDuration(getDefaultDuration());
                enterTransition.setSlideEdge(side);
                applyExcludeTargets(enterTransition);


                incomingActivity.getWindow().setEnterTransition(enterTransition);
            }

        }

        public static void slideInFromLeft(Activity incomingActivity){
            slideInFrom(Gravity.LEFT, incomingActivity);
        }

        public static void slideInFromTop(Activity incomingActivity){
            slideInFrom(Gravity.TOP, incomingActivity);
        }

        public static void slideInFromRight(Activity incomingActivity){
            slideInFrom(Gravity.RIGHT,incomingActivity);
        }
        public static void slideInFromBottom(Activity incomingActivity){

            slideInFrom(Gravity.BOTTOM,incomingActivity);
        }

        public static void explode(Activity incomingActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Explode enterTransition = new Explode();
                enterTransition.setDuration(getDefaultDuration());
                incomingActivity.getWindow().setEnterTransition(enterTransition);
            }


        }

    }

    private static int getDefaultDuration() {
        return 300;
    }

    private static ArrayList<Integer> excludeIds = new ArrayList<>();
    public static void excludeTargets(Integer... targetIds){
        for(Integer targetId: targetIds){
            excludeIds.add(targetId);
        }
    }
    private static void applyExcludeTargets(Transition transition) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            transition.excludeTarget(android.R.id.statusBarBackground,true);
            transition.excludeTarget(android.R.id.navigationBarBackground,true);

            for(Integer targetId: excludeIds){
                transition.excludeTarget(targetId, true);
            }

        }

    }


    public static class HideActivity{
        public static void fadeOut(Activity outgoingActivity){




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Fade exitTransition = new Fade();
                exitTransition.setDuration(400);
                outgoingActivity.getWindow().setExitTransition(exitTransition);
            }


        }

        //====================
        private static void slideOutTo(int side,Activity outgoingActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Slide exitTransition = new Slide();
                exitTransition.setDuration(getDefaultDuration());
                exitTransition.setSlideEdge(side);
                outgoingActivity.getWindow().setExitTransition(exitTransition);
            }

        }

        public static void slideOutToLeft(Activity outgoingActivity){
            slideOutTo(Gravity.LEFT, outgoingActivity);
        }

        public static void slideOutToTop(Activity outgoingActivity){
            slideOutTo(Gravity.TOP, outgoingActivity);
        }

        public static void slideOutToRight(Activity outgoingActivity){
            slideOutTo(Gravity.RIGHT, outgoingActivity);
        }
        public static void slideOutToBottom(Activity outgoingActivity){

            slideOutTo(Gravity.BOTTOM, outgoingActivity);
        }

        public static void explode(Activity outgoingActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Explode exitTransition = new Explode();
                exitTransition.setDuration(getDefaultDuration());
                outgoingActivity.getWindow().setExitTransition(exitTransition);
            }


        }

    }

}
