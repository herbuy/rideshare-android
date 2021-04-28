package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.pickers.HourOfDayPicker;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.HerbuyCalendar;
import resources.Dp;

public abstract class TravelInfoReviewSystem  extends TripSchedulingStep {
    Context context;

    public TravelInfoReviewSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "SUMMARY";
    }

    @Override
    public String getQuestion() {
        return "Please review and confirm";
    }

    @Override
    public View getAnswerArea() {
        return MakeDummy.scrollView(
                MakeDummy.linearLayoutVertical(
                        context,
                        enteredDetails(),
                        vspace(),
                        submitButton(),
                        vspace(),
                        vspace()
                )
        );

    }

    private View row(Context context, View left, View right){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1f;
        left.setLayoutParams(params);
        ((TextView)left).setGravity(Gravity.RIGHT);
        left.setPadding(0,0,Dp.normal(),0);

        params.weight = 2f;
        right.setLayoutParams(params);
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(context,left,right);
        layout.setPadding(0,Dp.scaleBy(0.5f),0,Dp.scaleBy(0.5f));
        return layout;
    }

    private View enteredDetails() {
        LinearLayout tableLayout = MakeDummy.linearLayoutVertical(
                context,
                row(
                        context,
                        fromLabel(),
                        fromValue()
                ),
                row(
                        context,
                        passingViaLabel(),
                        passingViaValue()
                ),
                row(
                        context,
                        toLabel(),
                        toValue()
                ),
                row(
                        context,
                        dateLabel(),
                        dateValue()
                ),
                row(
                        context,
                        timeLabel(),
                        timeValue()
                ),
                row(
                        context,
                        modelLabel(),
                        modelValue()
                ),
                row(
                        context,
                        regLabel(),
                        regValue()
                ),
                row(
                        context,
                        seatsAvailableLabel(),
                        seatAvailableValue()
                ),/*
                row(
                        context,
                        seatsRequiredLabel(),
                        seatsRequiredValue()
                ),*/
                row(
                        context,
                        fuelLabel(),
                        fuelValue()
                )
        );
        tableLayout.setPadding(Dp.one_point_5_em(),Dp.scaleBy(0.5f),Dp.one_point_5_em(),Dp.scaleBy(0.5f));


        return tableLayout;
    }

    private View vspace() {
        return MakeDummy.invisible(Atom.textView(context,"h"));
    }

    private View fromLabel() {
        return Atom.textViewPrimaryBold(context,"Setting from");
    }

    private static final String defaultText = "Not specified";
    TextView txtFrom;
    private View fromValue() {
        txtFrom = Atom.textView(context,defaultText);
        return txtFrom;
    }
    public void updateOrigin(Location newValue){
        txtFrom.setText(
                newValue == null?
                defaultText:
                new CSVFromLocation(newValue).getText()
        );
    }



    private View toLabel() {
        return Atom.textViewPrimaryBold(context,"To");
    }

    TextView txtTo;
    private View toValue() {
        txtTo = Atom.textView(context,defaultText);
        return txtTo;
    }

    public void updateDestination(Location newValue){
        txtTo.setText(
                newValue == null?
                        defaultText:
                        new CSVFromLocation(newValue).getText()
        );
    }




    private View passingViaLabel() {
        return Atom.textViewPrimaryBold(context,"Via");
    }

    TextView txtPassingVia;
    private View passingViaValue() {
        txtPassingVia = Atom.textView(context,defaultText);
        return txtPassingVia;
    }

    public void updatePassingVia(List<Location> selectedValues) {
        txtPassingVia.setText(
                selectedValues == null || selectedValues.size() < 1 ? defaultText:
                        Html.fromHtml(csvFromLocationList(selectedValues))
        );
    }

    private String csvFromLocationList(List<Location> selectedValues) {
        String csv = "";
        String separator = "";
        for(Location location : selectedValues){
            csv += separator + new CSVFromLocation(location).getText();
            separator = "<br/>";
        }
        return csv;
    }

    private View dateLabel() {
        return Atom.textViewPrimaryBold(context,"On");
    }

    TextView txtTravelDate;
    private View dateValue() {
        txtTravelDate = Atom.textView(context,defaultText);
        return txtTravelDate;
    }

    public void updateTravelDate(HerbuyCalendar newValue) {
        txtTravelDate.setText(
                newValue == null?
                        defaultText:
                        newValue.getFriendlyDate()
        );
    }

    private View timeLabel() {
        return Atom.textViewPrimaryBold(context,"At around");
    }

    TextView travelTime;
    private View timeValue() {
        travelTime = Atom.textView(context,defaultText);
        return travelTime;
    }

    public void updateTravelTime(HourOfDayPicker.Hour hour) {
        travelTime.setText(hour == null ? defaultText : hour.getFriendlyHour());
    }

    private View modelLabel() {
        return Atom.textViewPrimaryBold(context,"Car Model");
    }

    TextView txtCarModel;
    private View modelValue() {
        txtCarModel = Atom.textView(context,defaultText);
        return txtCarModel;
    }
    public void updateCarModel(CarModel item){
        txtCarModel.setText(item == null ? defaultText:item.model);
    }

    private View regLabel() {
        return Atom.textViewPrimaryBold(context,"Car Number");
    }

    TextView txtCarRegNum;
    private View regValue() {
        txtCarRegNum = Atom.textView(context,defaultText);
        return txtCarRegNum;
    }
    public void updateCarRegistrationNumber(String newValue){
        txtCarRegNum.setText(isNullOrEmpty(newValue) ? defaultText:newValue);
    }

    private boolean isNullOrEmpty(String newValue) {
        return newValue == null || newValue.trim().equalsIgnoreCase("");
    }

    private View seatsAvailableLabel() {
        return Atom.textViewPrimaryBold(context,"Seats available");
    }

    TextView txtSeatsAvailable;
    private View seatAvailableValue() {
        txtSeatsAvailable = Atom.textView(context,defaultText);
        return txtSeatsAvailable;
    }

    public void updateSeatAvailable(Integer count) {
        txtSeatsAvailable.setText(count == null || count == 0 ? defaultText : count.toString());

    }

    private View seatsRequiredLabel() {
        return Atom.textView(context,"Seats needed");
    }
    private View seatsRequiredValue() {
        return Atom.textView(context,"1");
    }


    private View fuelLabel() {
        return Atom.textViewPrimaryBold(context,"Fuel Charge");
    }

    TextView txtFuelCharges;
    private View fuelValue() {

        txtFuelCharges = Atom.textView(context,defaultText);
        return txtFuelCharges;
    }
    public void updateFuelCharges(Integer newAmount) {

        txtFuelCharges.setText(newAmount == null || newAmount == 0 ? "To be discussed" : "Ugx. "+newAmount);
    }

    private View submitButton() {
        final FrameLayoutBasedHerbuyStateView btnHost = new FrameLayoutBasedHerbuyStateView(context);
        btnHost.render(MakeDummy.wrapContent(Atom.button(context, "Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelInfoReviewSystem.this.onUploadTrip(btnHost);
            }
        })));
        MakeDummy.wrapContent(btnHost.getView());

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,btnHost.getView());
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        return wrapper;

    }

    protected abstract void onUploadTrip(FrameLayoutBasedHerbuyStateView sender);


}
