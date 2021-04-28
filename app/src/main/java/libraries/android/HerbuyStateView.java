package libraries.android;

import android.view.View;

public interface HerbuyStateView {
    View getView();

    void render(View content);

    void renderError(String message, OnRetryLoad onRetryLoad);

    void renderBusy();

    interface OnRetryLoad {
        void run();
    }
}
