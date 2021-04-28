package libraries.android;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class HerbuyTimePickerDialog {
    Context context;
    TimePickerDialog timePickerDialog;

    public HerbuyTimePickerDialog(Context context) {
        init(context);

    }

    private void init(Context context) {
        this.context = context;

        final Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                HerbuyTimePickerDialog.this.onTimeSet(hourOfDay,minute);
            }
        },hour,minute,true);

    }

    protected abstract void onTimeSet(int hourOfDay, int minute);

    public void show(){

        timePickerDialog.show();
    }

}
