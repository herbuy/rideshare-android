package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DialogActivity;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MinOfScreenWidthAndHeight;
import libraries.android.TextChangedListener;
import libraries.java.DoCapitalize;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;
import resources.TransitionName;

public class ChangeCarDetailsActivity extends DialogActivity {
    @Override
    protected String getTransitionName() {
        return TransitionName.carInfo;
    }

    @Override
    protected String getHeaderText() {
        return "Enter your car details";
    }

    @Override
    protected View getActivityContent() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                editCarModel.instance(),
                space(),
                editCarRegNumber.instance(),
                space(),
                actions.instance()
        );
        layout.setPadding(
                MinOfScreenWidthAndHeight.oneTenth(getContext()),
                Dp.two_em(),
                MinOfScreenWidthAndHeight.oneTenth(getContext()),
                Dp.two_em()
        );
        return layout;
    }

    private Singleton<View> actions = new Singleton<View>() {
        @Override
        protected View onCreate() {
            LinearLayout layout = MakeDummy.linearLayoutHorizontal(
                    getContext(),
                    cancelButton.instance(),
                    space(),
                    okButton.instance()
            );
            layout.setGravity(Gravity.RIGHT);
            return layout;
        }
    };

    private Singleton<View> cancelButton = new Singleton<View>() {
        @Override
        protected View onCreate() {
            Button btn = Atom.button(getContext(), "Cancel", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed(view);
                }
            });
            btn.setBackgroundResource(R.drawable.secondary_button);
            btn.setTextColor(ItemColor.primary());
            return btn;
        }
    };

    private Singleton<View> okButton = new Singleton<View>() {
        @Override
        protected View onCreate() {
            Button btn = Atom.button(getContext(), "Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CarInfo info = new CarInfo();
                    info.carModel = editCarModel.instance().getText().toString();
                    info.carRegNumber = editCarRegNumber.instance().getText().toString();
                    EventBusForTaskUploadTrip.carInfoChanged.notifyObservers(info);

                    onBackPressed(view);
                }
            });
            return btn;
        }
    };

    private View space() {
        TextView view = Atom.textView(getContext(),"h");
        view.setVisibility(View.INVISIBLE);
        return view;
    }

    private Context getContext(){
        return this;
    }

    private Singleton<EditText> editCarModel = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            return Atom.editText(
                    getContext(),
                    "Car model e.g. Subaru forester",
                    "",
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                    carModelChangedListener.instance()
            );
        }
    };

    private static abstract class TextModifier {

        //stores general workflow for modifying text. Important is to disable firing it twice if on client
        //where writes to edit text and could be fired again if text written to edit text.
        private boolean disableTextChangedNotification = false;
        public void run(String input) {
            if(disableTextChangedNotification) return;
            if(input == null) return;

            disableTextChangedNotification = true;
            String output = process(input);
            if(output == null){
                disableTextChangedNotification = false;
                return;
            }
            if(output.trim().equals(input)){
                disableTextChangedNotification = false;
                return;
            }

            writeOutput(output);
            disableTextChangedNotification = false;
        }
        protected abstract String process(String input);
        protected abstract void writeOutput(String output);

    }

    TextModifier carModelTextModifier = new TextModifier(){

        @Override
        protected void writeOutput(String output) {
            int cursorPosition = editCarModel.instance().getSelectionStart();
            editCarModel.instance().setText(output);
            editCarModel.instance().setSelection(Math.min(cursorPosition,output.length()));
        }
        @Override
        protected String process(String input) {
            input = input.trim();
            //remove extra spaces between words
            input = input.replaceAll("\\s+"," ");
            return DoCapitalize.eachWord(input,true).getNewValue();
        }
    };

    Singleton<TextChangedListener> carModelChangedListener = new Singleton<TextChangedListener>() {

        @Override
        protected TextChangedListener onCreate() {

            return new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                    carModelTextModifier.run(editable.toString().trim());
                }
            };
        }
    };

    TextModifier carRegNumberTextModifier = new TextModifier(){

        @Override
        protected void writeOutput(String output) {
            int cursorPosition = editCarRegNumber.instance().getSelectionStart();
            editCarRegNumber.instance().setText(output);
            editCarRegNumber.instance().setSelection(Math.min(cursorPosition,output.length()));
        }
        @Override
        protected String process(String input) {
            input = input.trim();
            //remove extra spaces between words
            input = input.replaceAll("\\s+"," ");
            return DoCapitalize.eachCharacter(input).getNewValue();
        }
    };

    private Singleton<EditText> editCarRegNumber = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            return Atom.editText(
                    getContext(),
                    "Car Registration Number e.g. UAX 596 G",
                    "",
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                    new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                    carRegNumberTextModifier.run(editable.toString().trim());
                }
            });
        }
    };
}