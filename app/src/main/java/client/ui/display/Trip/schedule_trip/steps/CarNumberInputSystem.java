package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.pickers.CarRegNumPicker;
import layers.render.Atom;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import resources.ItemColor;

public abstract class CarNumberInputSystem extends TripSchedulingStep {
    private Context context;

    public CarNumberInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Car Number";
    }

    @Override
    public String getQuestion() {
        return "Enter the registration number of the car you will be driving";
    }

    @Override
    public boolean isForDriversOnly() {
        return true;
    }

    TextView selectedValue;
    LinearLayout modelListContainer;
    RelativeLayout editArea;
    EditText editBox;
    Button saveButton;
    @Override
    public View getAnswerArea() {
        selectedValue = Atom.textView(context, "Example. Toyota RAV4");
        selectedValue.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        modelListContainer = MakeDummy.linearLayoutVertical(context);
        editBox();
        saveButton();
        editArea();

        mediator.refreshModelList();

        return MakeDummy.linearLayoutVertical(
                context,
                selectedValue,
                editArea,
                modelListContainer
        );
    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }


    private View editArea() {


        editArea = RelativeLayoutFactory.alignleftOfWidget(
                editBox,
                saveButton
        );
        editArea.setPadding(Dp.one_point_5_em(),0,Dp.one_point_5_em(),0);
        return editArea;
    }


    private View saveButton() {
        saveButton = Atom.button(context, "Save", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediator.save();
            }
        });
        saveButton.setBackgroundColor(Color.TRANSPARENT);
        saveButton.setTextColor(ItemColor.primary());
        saveButton.setVisibility(View.INVISIBLE);
        return saveButton;
    }

    private void editBox() {
        editBox = Atom.editText(context, "Car Reg. Number", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString().trim();
                typedValueChanged(value);
            }
        });
    }

    private void typedValueChanged(String value) {
        toggleSaveButtonVisibility(value);
    }

    private void toggleSaveButtonVisibility(String value) {
        if (value == null || value.trim().equalsIgnoreCase("") || value.trim().length() < 3) {
            hideSaveButton();
        } else {
            showSaveButton();
        }
    }

    private void showSaveButton() {
        TransitionManager.beginDelayedTransition(editArea);
        saveButton.setVisibility(View.VISIBLE);
    }

    private void hideSaveButton() {
        TransitionManager.beginDelayedTransition(editArea);
        saveButton.setVisibility(View.INVISIBLE);
    }


    public String getCarRegNumber() {
        return editBox.getText().toString();
    }

    private interface Mediator {

        void save();

        void refreshModelList();

        void selectItem(CarRegNumber carModel);

        void clearSelection();
    }

    private Mediator mediator = new Mediator() {

        @Override
        public void save() {
            String value = editBox.getText().toString().trim();
            CarRegNumber carRegNum = createNew(value);
            new CarRegNumCache().save(carRegNum.regNumber,carRegNum);
            editBox.setText("");
            HideKeyboard.run(editBox);
            refreshModelList();
            carRegNumPicker.selectFirstItem();
            //selectCarModel(carModel);


            //indicateSelectedValue(value);

        }

        public void refreshModelList() {
            modelListContainer.removeAllViews();
            modelListContainer.addView(cachedCarModels());
        }



        CarRegNumPicker carRegNumPicker;
        private View cachedCarModels() {
            carRegNumPicker = new CarRegNumPicker(context) {
                @Override
                protected void onItemSelected(CarRegNumber item) {
                    mediator.selectItem(item);
                    onCarRegistrationNumberChanged(item.regNumber);
                }

                @Override
                protected void onItemUnSelected(CarRegNumber item, List<SelectableItem<CarRegNumber>> selectedItems) {
                    mediator.clearSelection();
                    onCarRegistrationNumberChanged(null);
                }

                @Override
                protected Comparator<CarRegNumber> comparator() {
                    return new Comparator<CarRegNumber>() {
                        @Override
                        public int compare(CarRegNumber first, CarRegNumber second) {
                            return Long.compare(second.timestampLastUpdated, first.timestampLastUpdated);
                        }
                    };
                }
            };
            return carRegNumPicker.getView();

        }

        private CarRegNumber createNew(String carRegNum) {
            CarRegNumber item = new CarRegNumber();
            item.regNumber = carRegNum;
            item.timestampLastUpdated = System.currentTimeMillis();
            return item;
        }

        CarRegNumber selectedItem = null;
        @Override
        public void selectItem(CarRegNumber carModel) {
            selectedItem = carModel;
            indicateSelectedValue(carModel.regNumber);
        }

        @Override
        public void clearSelection() {
            selectedItem = null;
            indicateSelectedValue(null);
        }

        private void indicateSelectedValue(String value) {
            if (value == null || value.equalsIgnoreCase("")) {
                selectedValue.setText("Not specified");
            } else {
                selectedValue.setText(Html.fromHtml(
                        String.format(Locale.ENGLISH,
                                "Selected: <font color='#ff0000'>%s</font>", value
                        )
                ));
            }
        }
    };

    protected abstract void onCarRegistrationNumberChanged(String value);


}
