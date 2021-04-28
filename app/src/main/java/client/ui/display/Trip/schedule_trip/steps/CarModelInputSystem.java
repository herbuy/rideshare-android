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
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.pickers.CarModelPicker;
import layers.render.Atom;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import resources.ItemColor;

public abstract class CarModelInputSystem  extends TripSchedulingStep {
    private Context context;

    public CarModelInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Car Model";
    }

    @Override
    public String getQuestion() {
        return "What car model will you be driving?";
    }

    @Override
    public boolean isForDriversOnly() {
        return true;
    }

    TextView selectedValue;

    @Override
    public View getAnswerArea() {
        selectedValue = Atom.textView(context, "Example. Toyota RAV4");
        selectedValue.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        editBoxForCarModel();

        return MakeDummy.linearLayoutVertical(
                context,
                selectedValue,
                modelEditArea(),
                modelListContainer()
        );
    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }

    LinearLayout modelListContainer;
    private View modelListContainer() {
        modelListContainer = MakeDummy.linearLayoutVertical(context);
        mediator.refreshModelList();
        return modelListContainer;
    }

    RelativeLayout editArea;

    public RelativeLayout modelEditArea() {
        editArea = RelativeLayoutFactory.alignleftOfWidget(
                editBox,
                saveCarModelButton()
        );
        editArea.setPadding(Dp.one_point_5_em(),0,Dp.one_point_5_em(),0);
        return editArea;
    }

    EditText editBox;

    public void editBoxForCarModel() {
        editBox = Atom.editText(context, "Your Car model", new TextChangedListener() {
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

    Button saveButton;

    private View saveCarModelButton() {
        saveButton = Atom.button(context, "Save", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediator.saveModel();
            }
        });
        saveButton.setBackgroundColor(Color.TRANSPARENT);
        saveButton.setTextColor(ItemColor.primary());
        saveButton.setVisibility(View.INVISIBLE);
        return saveButton;
    }

    private interface Mediator {

        void saveModel();

        void refreshModelList();

        void selectCarModel(CarModel carModel);
    }

    private Mediator mediator = new Mediator() {

        public String getEditBoxValue() {
            return editBox.getText().toString().trim();
        }

        @Override
        public void saveModel() {
            String modelName = getEditBoxValue();
            CarModel carModel = createCarModel(modelName);
            new CarModelCache().save(carModel);
            editBox.setText("");
            HideKeyboard.run(editBox);
            refreshModelList();
            carModelPicker.selectFirstItem();
            //selectCarModel(carModel);


            //indicateSelectedValue(value);

        }

        public void refreshModelList() {
            modelListContainer.removeAllViews();
            modelListContainer.addView(cachedCarModels());
        }
        CarModelPicker carModelPicker;
        private View cachedCarModels() {
            carModelPicker = new CarModelPicker(context) {
                @Override
                protected void onItemSelected(CarModel item) {
                    mediator.selectCarModel(item);
                    carModelChanged(item);

                }

                @Override
                protected void onItemUnSelected(CarModel item, List<SelectableItem<CarModel>> selectedItems) {
                    carModelChanged(null);
                }

                @Override
                protected Comparator<CarModel> comparator() {
                    return new Comparator<CarModel>() {
                        @Override
                        public int compare(CarModel first, CarModel second) {
                            return Long.compare(second.timestampLastUpdated, first.timestampLastUpdated);
                        }
                    };
                }
            };
            return carModelPicker.getView();

        }

        private CarModel createCarModel(String modelName) {
            CarModel carModel = new CarModel();
            carModel.model = modelName;
            carModel.timestampLastUpdated = System.currentTimeMillis();
            return carModel;
        }

        @Override
        public void selectCarModel(CarModel carModel) {
            indicateSelectedValue(carModel.model);
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

    protected abstract void carModelChanged(CarModel item);

    public String getCarModel() {
        return editBox.getText().toString();
    }

}
