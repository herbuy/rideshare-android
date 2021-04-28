package client.ui.libraries;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface Form {
    interface Design{

        String prize();
        Step[] steps();
    }
    interface Step{

        String getTitle();
        String getQuestion();
        View getAnswerArea();
    }

    public static abstract class ScheduleTripFragment extends Fragment implements Step{
    }

}
