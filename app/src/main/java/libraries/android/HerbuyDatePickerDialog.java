package libraries.android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class HerbuyDatePickerDialog {
    Context context;

    public HerbuyDatePickerDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.context = context;

    }

    public void show(){

        final Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                onDateSelected(year,monthOfYear,dayOfMonth);
            }
        },year,month,day);

        datePickerDialog.show();
    }

    protected abstract void onDateSelected(int year, int monthOfTheYear, int dayOfMonth);
}
