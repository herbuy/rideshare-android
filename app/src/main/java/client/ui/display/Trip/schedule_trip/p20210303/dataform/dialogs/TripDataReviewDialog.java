package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.List;
import java.util.Locale;

import client.data.AppCallback;
import client.data.Rest2;
import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.HerbuyCalendar;
import libraries.android.HerbuyStateView;
import libraries.underscore.Singleton;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class TripDataReviewDialog extends TripInputDialog{

    private String defaultDateText = "<font color='#242424'><b>Travel Date</b></font><br/>Not specified";
    private String defaultTimeLabel = "Departure time";

    public TripDataReviewDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    public View getView(){
        return finalView.instance();
    }

    Singleton<View> finalView = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return makeWrapperWithScrollableBody("Confirm your details<br/><small><small>(Then upload to see people you can travel with)</small></small>",body());
        }
    };

    private View body() {

        View view = MakeDummy.linearLayoutVertical(
                context,
                originField.instance(),
                destinationField.instance(),
                routeField.instance(),
                dateField.instance(),
                timeField.instance(),
                carModelField.instance(),
                carNumberField.instance(),
                seatsAvailableField.instance(),
                fuelChargeField.instance(),
                space(),
                uploadButton.instance(),
                space()

        );

        return view;
    }

    String defaultText = "Not specified";
    private View space() {
        return MakeDummy.invisible(Atom.textView(context,"h"));
    }
    //==================================================

    Singleton<View> originField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    Atom.textViewPrimaryBold(context,"Leaving"),
                    //space(),
                    valueFrom.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> valueFrom = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setOrigin(Location newValue) {

        valueFrom.instance().setText(Html.fromHtml(
                newValue == null ?
                        defaultText : new CSVFromLocation(newValue).getText()
        ));
    }

    //==================================================

    Singleton<View> destinationField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    Atom.textViewPrimaryBold(context,"To"),
                    //space(),
                    toValue.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> toValue = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setDestination(Location newValue) {

        toValue.instance().setText(Html.fromHtml(
                newValue == null ?
                        defaultText : new CSVFromLocation(newValue).getText()
        ));
    }

    Singleton<TextView> routeField = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView view = Atom.textView(context,defaultText);
            view.setVisibility(View.GONE);
            view.setBackgroundResource(R.drawable.header_background);
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            return view;
        }
    };

    public void setRoute(List<Location> route) {
        if(route == null || route.size() < 1){
            routeField.instance().setVisibility(View.GONE);
        }
        else{
            routeField.instance().setVisibility(View.VISIBLE);

            String separator = "";
            StringBuilder builder = new StringBuilder(route.size() * 2);
            for(Location item : route){
                builder.append(separator);
                builder.append(item.getNameShort());
                separator = ", ";
            }

            routeField.instance().setText(Html.fromHtml(String.format(Locale.ENGLISH,
                    "<b><font color='#242424'>Via</font></b> %s",
                    builder.toString()
            )));

        }

    }



    //=================================================
    Singleton<View> dateField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    dateValue.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> dateValue = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultDateText);
        }
    };

    public void setTravelDate(HerbuyCalendar newValue) {
        if(newValue == null){

        }

        dateValue.instance().setText( newValue == null ?
                Html.fromHtml(defaultDateText) : (
                        newValue.isToday() || newValue.isTomorrow() || newValue.isYesterday() ?
                                newValue.getFriendlyDate():
                                Html.fromHtml("<b><font color='#242424'>On</font></b> "+newValue.getFriendlyDate())
                ));
    }

    //=================================================
    Singleton<View> timeField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    timeFieldLabel.instance(),
                    //space(),
                    timeValue.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> timeFieldLabel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewPrimaryBold(context,defaultTimeLabel);
        }
    };

    Singleton<TextView> timeValue = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setTravelTime(TimeInputDialog.Hour newValue) {
        if(newValue == null){
            timeFieldLabel.instance().setText(defaultTimeLabel);
            timeValue.instance().setText(defaultText);
        }
        else{
            timeFieldLabel.instance().setText(defaultTimeLabel);
            timeValue.instance().setText(newValue.getFriendlyHour());
        }
    }


    public void setDrivingStatus(Boolean newValue) {
        if(newValue == null || newValue == true){
            carModelField.instance().setVisibility(View.VISIBLE);
            carNumberField.instance().setVisibility(View.VISIBLE);
            fuelChargeField.instance().setVisibility(View.VISIBLE);
            seatsLabel.instance().setText(defaultTextForSeatsIfDriver);

        }
        else{
            carModelField.instance().setVisibility(View.GONE);
            carNumberField.instance().setVisibility(View.GONE);
            fuelChargeField.instance().setVisibility(View.GONE);
            seatsLabel.instance().setText(defaultTextForSeatsIfPassenger);
        }

    }


    //=================================================
    private String defaultCarModelLabel = "Car Model";
    Singleton<View> carModelField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    carModelLabel.instance(),
                    //space(),
                    carModel.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> carModelLabel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewPrimaryBold(context,defaultCarModelLabel);
        }
    };

    Singleton<TextView> carModel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setCarModel(CarModel newValue) {
        if(newValue == null){
            carModelLabel.instance().setText(defaultCarModelLabel);
            carModel.instance().setText(defaultText);
        }
        else{
            carModelLabel.instance().setText("Driving");
            carModel.instance().setText(newValue.model);
        }

    }

    //=================================================
    Singleton<View> carNumberField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    Atom.textViewPrimaryBold(context,"Car Reg. Number"),
                    //space(),
                    carRegNumber.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> carRegNumber = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setCarRegNumber(CarRegNumber newValue) {

        carRegNumber.instance().setText( newValue == null ?
                defaultText : newValue.regNumber);
    }

    //=================================================
    String defaultTextForSeatsIfDriver = "Seats Available";
    String defaultTextForSeatsIfPassenger = "Seats Wanted";
    Singleton<TextView> seatsLabel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewPrimaryBold(context,defaultTextForSeatsIfDriver);
        }
    };
    Singleton<View> seatsAvailableField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    seatsLabel.instance(),
                    //space(),
                    seatsAvailable.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> seatsAvailable = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setSeatsAvailable(Integer newValue) {
        seatsAvailable.instance().setText( newValue == null ?
                defaultText : newValue.toString());
    }

    //=================================================
    Singleton<View> fuelChargeField = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View view = MakeDummy.linearLayoutVertical(
                    context,
                    Atom.textViewPrimaryBold(context,"Fuel contribution (Per Seat)"),
                    //space(),
                    fuelCharges.instance()
            );
            view.setPadding(0,Dp.normal(),0,Dp.normal());
            view.setBackgroundResource(R.drawable.header_background);
            return view;
        }
    };

    Singleton<TextView> fuelCharges = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textView(context,defaultText);
        }
    };

    public void setFuelCharges(Integer newValue) {
        fuelCharges.instance().setText( newValue == null ?
                defaultText : newValue.toString() + " Ushs");
    }

    private Singleton<View> uploadButton = new Singleton<View>() {
        FrameLayoutBasedHerbuyStateView view;
        @Override
        protected View onCreate() {
            view = new FrameLayoutBasedHerbuyStateView(context);
            view.render(defaultButton());
            return view.getView();

        }

        private View defaultButton() {
            return Atom.button(context, "Upload", new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ParamsForScheduleTrip params = inputListener.getParamsForScheduleTrip();
                    view.render(Atom.textView(context,"Uploading..."));

                    Rest2.api().scheduleTrip(params).enqueue(new AppCallback<List<Trip>>() {
                        @Override
                        protected void onSuccess(Call<List<Trip>> call, Response<List<Trip>> response) {
                            view.render(Atom.textView(context, "SUCCESS!!"));
                            Trip trip = response.body().get(0);
                            inputListener.tripScheduled(trip);
                        }

                        @Override
                        protected void onError(Call<List<Trip>> call, Throwable t) {
                            view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                                @Override
                                public void run() {
                                    onClick(v);
                                }
                            });
                        }
                    });
                }
            });
        }
    };

}
