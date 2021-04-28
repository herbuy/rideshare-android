package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.data.DummyData;
import client.ui.GotoActivity;
import client.ui.display.files.LoadImage;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MakeStateListDrawable;
import libraries.UnixTimestampInMillis;
import libraries.HerbuyCalendar;
import libraries.android.MinOfScreenWidthAndHeight;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import resources.ItemColor;

public class FamilyListItem implements HerbuyView {
    Context context;
    Family item;
    LinearLayout finalView;

    public FamilyListItem(final Context context, final Family item) {
        this.context = context;
        this.item = item;
        finalView = MakeDummy.linearLayoutVertical(context);
        //finalView.setBackgroundResource(R.drawable.selectable_item);
        finalView.setBackground(MakeStateListDrawable.where(Color.WHITE, ItemColor.highlight()));
        finalView.setClickable(true);
        finalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GotoActivity.familyChat(context, item);

            }
        });
        refreshUI();

    }

    private String getClassName() {
        return getClass().getSimpleName();
    }


    private View travellers() {
        TextView driver = Atom.textView(context,String.format("Driver: %s",item.getDriverName()));
        TextView passengers = Atom.textView(context,String.format("Passengers: %s",item.getPassengersCSV()));
        passengers.setTextSize(Dp.scaleBy(0.8f));
        return MakeDummy.linearLayoutVertical(context,driver,passengers);
    }

    private String travelDateTimeString() {
        String dateString = getOnTraveDate();
        String tripTimeTwelveHour = UnixTimestampInMillis.toTimeTwelveHour(UnixTimestampInMillis.fromDateTime(item.getTripTime(),"HH:mm:ss"));
        return String.format("%s - %s", dateString, tripTimeTwelveHour);
    }

    private String getOnTraveDate() {
        HerbuyCalendar calendar = new HerbuyCalendar(UnixTimestampInMillis.fromDate(item.getTripDate()));

        return calendar.isToday() || calendar.isYesterday() || calendar.isTomorrow() ?
                calendar.getFriendlyDate() :
                "On "+calendar.getFriendlyDate();
    }

    private View icon(int resourceId){
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(resourceId);
        imageView.setLayoutParams(new TableRow.LayoutParams(Dp.two_em(), Dp.two_em()));
        imageView.setAlpha(0.2f);
        return imageView;

    }
    private View route() {
        String viaString =
                item.getPassingViaCSV() == null || item.getPassingViaCSV().trim().equalsIgnoreCase("") ?
                        "" : "<br/>Via "+item.getPassingViaCSV();

        TextView from = Atom.textViewPrimaryNormal(context, String.format(
                "<b>%s</b> %s To %s%s<br>%s",
                leaveStatusText(),
                item.getOriginShort(),
                item.getDestinationShort(),
                viaString,
                travelDateTimeString()
        ));
        MakeDummy.wrapContent(from);
        from.setTextSize(Dp.scaleBy(0.8f));
        from.setGravity(Gravity.LEFT);
        from.setAlpha(0.7f);

        LinearLayout wrapper = MakeDummy.linearLayoutHorizontal(
                context,
                from
        );
        wrapper.setGravity(Gravity.LEFT);
        return wrapper;
        //return from;
    }

    private String leaveStatusText() {
        long tripDateTimestamp = UnixTimestampInMillis.fromDate(item.getTripDate()) + UnixTimestampInMillis.fromTime(item.getTripTime());
        return tripDateTimestamp < System.currentTimeMillis() ? "Left" : "Leaves";

    }

    private View left() {

        int imageSize = MinOfScreenWidthAndHeight.oneFifth(context);

        ImageView imageView = DummyData.randomCircleImageView(context, imageSize);
        LoadImage.fromFileShortName(imageView,item.getDriverProfilePic());
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                imageView,
                vspaceHalf(),
                vspaceHalf(),
                vspaceHalf(),
                vspaceHalf(),
                completed()
        );
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        return wrapper;

    }

    private View completed() {
        String text = item.isCompleted() ? "Rate Trip" : "End Trip";

        TextView view = Atom.textView(context,text);
        view.setTextSize(Dp.scaleBy(0.8f));
        view.setClickable(true);
        view.setBackgroundResource(R.drawable.selectable_item);
        MakeDummy.wrapContent(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCompleted()) {
                    GotoActivity.rateTrip(context, item);
                } else {
                    GotoActivity.reportArrived(context, item);
                }
            }
        });
        return view;
    }

    private View vspaceHalf() {
        ImageView imageView = new ImageView(context);
        imageView.setPadding(4, 4, 4, 4);
        MakeDummy.wrapContent(imageView);
        imageView.setVisibility(View.INVISIBLE);
        return imageView;
        //return MakeDummy.wrapContent(MakeDummy.invisible(MakeDummy.padding(Atom.textView(context,""),Dp.scaleBy(0.25f))));
    }


    protected View notificationBadge() {
        return NotificationBadge.where(context,item.getUnseenMessageCount()).getView();
    }


    private void refreshUI() {

        View item = MakeDummy.linearLayoutVertical(context);
        item.setLayoutParams(new TableRow.LayoutParams(80,40));
        item.setBackgroundColor(Color.parseColor("#ffffcc"));

        TableLayout tableLayout = MakeDummy.table(context,
                MakeDummy.row(
                        context,
                        left(),
                        right()
                ));
        tableLayout.setColumnStretchable(1, true);

        RelativeLayout relativeLayout = RelativeLayoutFactory.alignleftOfWidget(tableLayout,MakeDummy.linearLayoutVertical(context,notificationBadge()), RelativeLayout.ALIGN_PARENT_TOP);

        finalView.removeAllViews();
        finalView.addView(relativeLayout);
        finalView.setPadding(Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em());



    }

    private View right() {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setColumnStretchable(1, true);
        tableLayout.addView(rightRow(MakeDummy.invisible(icon(R.drawable.ic_menu_location_black)), car()));
        tableLayout.addView(rightRow(MakeDummy.invisible(icon(R.drawable.ic_menu_location_black)), travellers()));
        tableLayout.addView(rightRow(MakeDummy.invisible(icon(R.drawable.ic_menu_location_black)), route()));

        return tableLayout;
    }

    private View car() {
        TextView carModel = Atom.textViewPrimaryBold(context,item.getCarModel());
        carModel.setAlpha(0.8f);
        TextView carRegNum = Atom.textView(context,item.getCarRegNumber());
        carRegNum.setTypeface(null, Typeface.BOLD);

        return MakeDummy.linearLayoutVertical(
                context, carModel,carRegNum

        );
    }

    private TableRow rightRow(View icon, View text) {
        return rightRow(icon,text,null);
    }
    private TableRow rightRow(View icon, View text, View right) {
        TableRow row = new TableRow(context);
        row.addView(icon);
        row.addView(text);
        if(right == null){
            right = MakeDummy.invisible(Atom.textView(context,"-"));
        }
        row.addView(right);
        row.setGravity(Gravity.CENTER_VERTICAL);
        return row;
    }


    protected View getActionBar() {
        return CreateFamilyClickOptions.where(context, item);
    }

    @Override
    public View getView() {
        return finalView;
    }
}
