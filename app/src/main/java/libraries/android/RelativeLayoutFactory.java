package libraries.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Random;

public class RelativeLayoutFactory {
    public static RelativeLayout alignleftOfWidget(View leftWidget,View rightWidget){
        return alignleftOfWidget(leftWidget,rightWidget,verticalAlignCenter());
    }

    public static RelativeLayout alignleftOfWidget(View leftWidget,View rightWidget, int verticalAlign){

        rightWidget.setLayoutParams(paramsForAlignParentRight(verticalAlign));
        leftWidget.setLayoutParams(paramsForAlignLeftOf(rightWidget, verticalAlign));

        RelativeLayout layout = new RelativeLayout(rightWidget.getContext());
        layout.addView(leftWidget);
        layout.addView(rightWidget);
        return layout;
    }

    public static RelativeLayout alignRightOfWidget(View leftWidget,View rightWidget){
        leftWidget.setLayoutParams(paramsForAlignParentLeft());
        rightWidget.setLayoutParams(paramsForAlignRightOf(leftWidget));

        RelativeLayout layout = new RelativeLayout(rightWidget.getContext());
        layout.addView(leftWidget);
        layout.addView(rightWidget);
        return layout;
    }

    public static RelativeLayout threeColumn(View leftWidget,View middleWidget,View rightWidget){
        leftWidget.setLayoutParams(paramsForAlignParentLeft());
        rightWidget.setLayoutParams(paramsForAlignParentRight(verticalAlignCenter()));
        middleWidget.setLayoutParams(paramsForAlignCenterOf(leftWidget, rightWidget));

        RelativeLayout layout = new RelativeLayout(middleWidget.getContext());
        layout.addView(leftWidget);
        layout.addView(middleWidget);
        layout.addView(rightWidget);
        layout.setGravity(RelativeLayout.CENTER_VERTICAL);
        return layout;
    }

    private static int verticalAlignCenter() {
        return  RelativeLayout.CENTER_VERTICAL;

    }

    public static ViewGroup alignAboveWidget(View topWidget, View bottomWidget) {

        bottomWidget.setLayoutParams(paramsForAlignParentBottom());
        topWidget.setLayoutParams(paramsForAlignAbove(bottomWidget));

        RelativeLayout layout = new RelativeLayout(bottomWidget.getContext());
        layout.addView(topWidget);
        layout.addView(bottomWidget);
        return layout;
    }

    public static RelativeLayout alignBelowWidget(View topChild, View bottomChild) {

        topChild.setLayoutParams(paramsForAlignParentTop());
        bottomChild.setLayoutParams(paramsForAlignBelow(topChild));

        RelativeLayout layout = new RelativeLayout(topChild.getContext());
        layout.addView(topChild);
        layout.addView(bottomChild);

        return layout;
    }



    private static ViewGroup.LayoutParams paramsForAlignLeftOf(View rightWidget, int verticalAlign) {

        if(rightWidget.getId() == -1){
            rightWidget.setId(Math.abs(new Random().nextInt()));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.LEFT_OF, rightWidget.getId());
        params.addRule(verticalAlign);

        return params;

    }

    private static ViewGroup.LayoutParams paramsForAlignRightOf(View leftWidget) {

        if(leftWidget.getId() == -1){
            leftWidget.setId(Math.abs(new Random().nextInt()));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, leftWidget.getId());
        return params;

    }

    private static ViewGroup.LayoutParams paramsForAlignCenterOf(View leftWidget, View rightWidget) {

        if(leftWidget.getId() == -1){
            leftWidget.setId(Math.abs(new Random().nextInt()));
        }
        if(rightWidget.getId() == -1){
            rightWidget.setId(Math.abs(new Random().nextInt()));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, leftWidget.getId());
        params.addRule(RelativeLayout.LEFT_OF, rightWidget.getId());
        return params;

    }

    private static ViewGroup.LayoutParams paramsForAlignAbove(View bottomWidget) {
        if(bottomWidget.getId() == -1){
            bottomWidget.setId(Math.abs(new Random().nextInt()));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, bottomWidget.getId());
        return params;
    }

    private static ViewGroup.LayoutParams paramsForAlignBelow(View topChild) {
        if(topChild.getId() == -1){
            topChild.setId(Math.abs(new Random().nextInt()));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, topChild.getId());
        return params;
    }

    private static ViewGroup.LayoutParams paramsForAlignParentRight(int verticalAlign) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(verticalAlign);
        return params;
    }
    private static ViewGroup.LayoutParams paramsForAlignParentLeft() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        return params;
    }
    private static ViewGroup.LayoutParams paramsForAlignParentBottom() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return params;
    }
    private static ViewGroup.LayoutParams paramsForAlignParentTop() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        return params;
    }

}
