package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

import client.data.LocalSession;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_date.ChangeDateTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_destination.ChangeDestinationTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_origin.ChangeOriginTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time.ChangeDepartureTimeTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DataCollector;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DataUploader;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.underscore.Singleton;
import resources.Dp;

public abstract class FindTravelMateForm{
    protected final Context context;

    public FindTravelMateForm(Context context) {
        this.context = context;
    }

    public View getView(){
        return wrapper.instance();
    }

    private Singleton<View> wrapper = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return MakeDummy.scrollView(view.instance());
        }
    };

    DataCollector dataCollector = new DataCollector();

    private Singleton<ViewGroup> view = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {





            LinearLayout linearLayout = MakeDummy.linearLayoutVertical(
                    context,
                    new ChangeDestinationTrigger(context).getView(),
                    new ChangeOriginTrigger(context).getView(),
                    new ChangeDateTrigger(context).getView(),
                    new ChangeDepartureTimeTrigger(context).getView()
            );
            addMoreTriggers(linearLayout);
            linearLayout.addView(actionArea.instance());
            //linearLayout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
            return linearLayout;
        }
    };



    private View space() {
        return MakeDummy.invisible(MakeDummy.textView(context,"h"));
    }

    private Singleton<View> actionArea = new Singleton<View>() {
        @Override
        protected View onCreate() {


            LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,submitButton.instance());
            wrapper.setPadding(Dp.four_em(),Dp.scaleBy(3),Dp.four_em(),Dp.scaleBy(3));

            return wrapper;
        }
    };

    private Singleton<View> submitButton = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return new DataUploader(context){
                @Override
                protected ParamsForScheduleTrip getData() {
                    ParamsForScheduleTrip params = dataCollector.getParams();
                    processBeforeUpload(params);
                    return params;
                }
            }.getView();
        }
    };

    protected void processBeforeUpload(ParamsForScheduleTrip params) {

    }

    protected abstract void addMoreTriggers(ViewGroup viewGroup);

    private Singleton<TextInputLayout> origin = new Singleton<TextInputLayout>() {
        @Override
        protected TextInputLayout onCreate() {
            TextInputLayout layout = new TextInputLayout(context);
            layout.setHint("From");
            layout.addView(Atom.editText(context,"City or District",null));
            return layout;
        }
    };
}
