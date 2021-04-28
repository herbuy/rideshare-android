package layers.render.stream;

import android.view.View;

import core.businessobjects.Subjects;
import stream.item.StreamItemViews;

public final class StreamItemViewSelector {
    public static View select(String subject, StreamItemViews streamItemViews) {
        switch (subject){
            case Subjects.travelTogetherRequest:
                return streamItemViews.travelTogether();

            default:
                return streamItemViews.defaultView();

        }

    }
}
