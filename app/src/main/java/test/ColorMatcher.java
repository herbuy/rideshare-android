package test;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

import layers.render.Atom;
import libraries.ObserverList;
import libraries.android.ColorCalc;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.underscore.Singleton;

public class ColorMatcher {
    public static class EventBus{
        public final ObserverList<Integer> colorChanged = new ObserverList<>();

    }

    private abstract static class ColorOutPutDevice{
        private Context context;
        private EventBus eventBus;

        public ColorOutPutDevice(Context context, EventBus eventBus) {
            this.context = context;
            this.eventBus = eventBus;
            init();
        }

        private void init() {
            eventBus.colorChanged.add(new ObserverList.Observer<Integer>() {
                @Override
                public void notifyEvent(Integer eventArgs) {
                    eventArgs = interceptColor(eventArgs);
                    updateUI(eventArgs);
                }
            });
        }

        protected abstract void updateUI(Integer eventArgs);

        protected void updateBackgroundColor(Integer eventArgs) {
            view.instance().setBackgroundColor(eventArgs);
        }

        protected void updateText(String eventArgs) {
            view.instance().setTextColor(Color.parseColor("#242424"));
            view.instance().setText(eventArgs);
        }

        protected Integer interceptColor(Integer eventArgs) {
            return eventArgs;
        }

        public View getView(){
            return view.instance();
        }

        private Singleton<TextView> view = new Singleton<TextView>() {
            @Override
            protected TextView onCreate() {
                TextView view = new TextView(context);
                view.setText("Hello");
                view.setTextColor(Color.TRANSPARENT);
                view.setBackgroundColor(Color.WHITE);

                return view;
            }
        };
    }

    private static class ColorOutputAsBackground extends ColorOutPutDevice {
        public ColorOutputAsBackground(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        @Override
        protected void updateUI(Integer eventArgs) {
            updateBackgroundColor(eventArgs);
        }
    }

    private static class ColorOutputAsRelativeLuminance extends ColorOutPutDevice {
        public ColorOutputAsRelativeLuminance(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        @Override
        protected void updateUI(Integer eventArgs) {
            updateText(String.format(
                    Locale.ENGLISH,
                    "RL: %2f",
                    ColorCalc.getRelativeLuminance(eventArgs)
            ));
        }
    }

    public static class GrayScaleColorOutput extends ColorOutputAsBackground {
        public GrayScaleColorOutput(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        @Override
        protected Integer interceptColor(Integer eventArgs) {
            return ColorCalc.getGrayscale(eventArgs);
        }
    }

    private abstract static class ColorInputDevice {
        protected final Context context;
        protected final EventBus eventBus;

        public ColorInputDevice(Context context, EventBus eventBus) {
            this.context = context;
            this.eventBus = eventBus;
            eventBus.colorChanged.add(observer.instance());

        }

        public View getView(){
            return view.instance();
        }
        private Singleton<View> view = new Singleton<View>() {
            @Override
            protected View onCreate() {
                return makeView();
            }
        };

        protected abstract View makeView();

        protected boolean notificationEnabled = true;



        protected final Singleton<ObserverList.Observer<Integer>> observer = new Singleton<ObserverList.Observer<Integer>>() {
            @Override
            protected ObserverList.Observer<Integer> onCreate() {
                return new ObserverList.Observer<Integer>() {
                    @Override
                    public void notifyEvent(Integer eventArgs) {
                        notificationEnabled = false;
                        doUpdateUI(eventArgs);
                        notificationEnabled = true;
                    }
                };
            }
        };

        protected abstract void doUpdateUI(Integer eventArgs);
    }

    private static class HexColorInputDeviceUsingEditBox extends ColorInputDevice {
        public HexColorInputDeviceUsingEditBox(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        private EditText editText;
        protected View makeView() {
            editText = doMakeEditText();
            return editText;
        }

        private EditText doMakeEditText() {
            EditText editText = new EditText(context);
            editText.setHint("Hex");
            editText.addTextChangedListener(new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!notificationEnabled){
                        return;
                    }

                    try{
                        int newColor = Color.parseColor("#"+editable.toString().trim());
                        eventBus.colorChanged.notifyObserversExcept(observer.instance(),newColor);
                    }
                    catch (Exception ex){

                    }

                }
            });
            return editText;
        }


        protected void doUpdateUI(Integer eventArgs) {
            editText.setText(ColorCalc.toHexNoAlpha(eventArgs));
        }
    }

    private static abstract class EditTextBased3PartColorInputDevice extends ColorInputDevice {



        public EditTextBased3PartColorInputDevice(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        private Singleton<EditText> part1EditText = new Singleton<EditText>() {
            @Override
            protected EditText onCreate() {
                return Atom.editText(context, part1Hint(),textChangedListener);
            }
        };

        private Singleton<EditText> part2EditText = new Singleton<EditText>() {
            @Override
            protected EditText onCreate() {
                return Atom.editText(context, part2Hint(),textChangedListener);
            }
        };

        private Singleton<EditText> part3EditText = new Singleton<EditText>() {
            @Override
            protected EditText onCreate() {
                return Atom.editText(context, part3Hint(),textChangedListener);
            }
        };


        @Override
        protected final View makeView() {
            LinearLayout layout = MakeDummy.linearLayoutVertical(
                    context,
                    part1EditText.instance(),
                    part2EditText.instance(),
                    part3EditText.instance()
            );
            return layout;
        }

        protected abstract String part1Hint();
        protected abstract String part2Hint();
        protected abstract String part3Hint();

        private TextChangedListener textChangedListener = new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(!notificationEnabled){
                    return;
                }
                try{
                    int newColor = combinePartsIntoColor(
                            part1EditText.instance().getText().toString().trim(),
                            part2EditText.instance().getText().toString().trim(),
                            part3EditText.instance().getText().toString().trim()
                    );
                    eventBus.colorChanged.notifyObserversExcept(observer.instance(),newColor);
                }
                catch (Exception ex){
                    Log.e("Combine Color Failed: ",ex.getMessage());
                }
            }
        };

        protected abstract int combinePartsIntoColor(String part1, String part2, String part3);

        @Override
        protected final void doUpdateUI(Integer eventArgs) {
            part1EditText.instance().setText(extractValueForPart1(eventArgs));
            part2EditText.instance().setText(extractValueForPart2(eventArgs));
            part3EditText.instance().setText(extractValueForPart3(eventArgs));
        }

        protected abstract String extractValueForPart1(Integer eventArgs);
        protected abstract String extractValueForPart2(Integer eventArgs);
        protected abstract String extractValueForPart3(Integer eventArgs);


    }





    private static class EditTextBasedRGBColorInputDevice extends EditTextBased3PartColorInputDevice {
        public EditTextBasedRGBColorInputDevice(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        @Override
        protected String part1Hint() {
            return "R";
        }

        @Override
        protected String part2Hint() {
            return "G";
        }

        @Override
        protected String part3Hint() {
            return "B";
        }

        @Override
        protected int combinePartsIntoColor(String part1, String part2, String part3) {
            return ColorCalc.makeColorFromRGB(
                    Integer.parseInt(part1),
                    Integer.parseInt(part2),
                    Integer.parseInt(part3)
            );
        }

        @Override
        protected String extractValueForPart1(Integer eventArgs) {
            return String.valueOf(ColorCalc.getRed(eventArgs));
        }

        @Override
        protected String extractValueForPart2(Integer eventArgs) {
            return String.valueOf(ColorCalc.getGreen(eventArgs));
        }

        @Override
        protected String extractValueForPart3(Integer eventArgs) {
            return String.valueOf(ColorCalc.getBlue(eventArgs));
        }
    }

    private static class EditTextBasedHSBColorInputDevice extends EditTextBased3PartColorInputDevice {
        public EditTextBasedHSBColorInputDevice(Context context, EventBus eventBus) {
            super(context, eventBus);
        }

        @Override
        protected String part1Hint() {
            return "H";
        }

        @Override
        protected String part2Hint() {
            return "S";
        }

        @Override
        protected String part3Hint() {
            return "B";
        }

        @Override
        protected int combinePartsIntoColor(String part1, String part2, String part3) {
            return ColorCalc.makeColorFromHSB(
                    Float.parseFloat(part1),
                    Float.parseFloat(part2),
                    Float.parseFloat(part3)
            );
        }

        @Override
        protected String extractValueForPart1(Integer eventArgs) {
            return String.valueOf(ColorCalc.getHue(eventArgs));
        }

        @Override
        protected String extractValueForPart2(Integer eventArgs) {
            return String.valueOf(ColorCalc.getSaturation(eventArgs));
        }

        @Override
        protected String extractValueForPart3(Integer eventArgs) {
            return String.valueOf(ColorCalc.getBrightness(eventArgs));
        }
    }

    private static abstract class ColorPairView{
        protected Context context;
        private EventBus eventBusForBgColor;
        private EventBus eventBusForAccentColor;

        public ColorPairView(Context context, EventBus eventBusForBgColor, EventBus eventBusForAccentColor) {
            this.context = context;
            this.eventBusForBgColor = eventBusForBgColor;
            this.eventBusForAccentColor = eventBusForAccentColor;
            init();
            updateUI();
        }

        protected int bgColor;
        protected int accentColor;

        private void init() {

            eventBusForBgColor.colorChanged.add(new ObserverList.Observer<Integer>() {
                @Override
                public void notifyEvent(Integer eventArgs) {
                    bgColor = eventArgs;
                    updateUI();
                }
            });
            eventBusForAccentColor.colorChanged.add(new ObserverList.Observer<Integer>() {
                @Override
                public void notifyEvent(Integer eventArgs) {
                    accentColor = eventArgs;
                    updateUI();
                }
            });

            additionalInit();

        }

        protected abstract void additionalInit();

        protected abstract void updateUI();
        public abstract View getView();
    }

    private static class ViewForColorContrast extends ColorPairView{
        public ViewForColorContrast(Context context, EventBus eventBusForBgColor, EventBus eventBusForAccentColor) {
            super(context, eventBusForBgColor, eventBusForAccentColor);
        }

        private TextView textView;
        @Override
        protected void additionalInit() {
            textView = new TextView(context);
            textView.setText("Contrast Ratio");
        }

        @Override
        public View getView() {
            return textView;
        }

        @Override
        protected void updateUI() {
            double contrastRatio = ColorCalc.getContrastRatio(bgColor,accentColor);
            textView.setText(String.format(Locale.ENGLISH,"%2f",contrastRatio));
        }
    }


    private static class ViewForTextOnBackground extends ColorPairView{
        public ViewForTextOnBackground(Context context, EventBus eventBusForBgColor, EventBus eventBusForAccentColor) {
            super(context, eventBusForBgColor, eventBusForAccentColor);
        }

        private TextView textView;
        @Override
        protected void additionalInit() {
            textView = new TextView(context);
            textView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sd diam nonmumy eiusmod rom tempor incidunt ut lsbore et dolore magna aliquam erat volupat");
            textView.setPadding(24,24,24,24);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        @Override
        public View getView() {
            return textView;
        }

        @Override
        protected void updateUI() {
            textView.setBackgroundColor(bgColor);
            textView.setTextColor(accentColor);
        }
    }

    //=====================
    Context context;

    public ColorMatcher(Context context) {
        this.context = context;
    }

    public View getView(){
        View view = MakeDummy.scrollView(table.instance());
        eventBusForBgColor.colorChanged.notifyObservers(Color.parseColor("#0086BF"));
        eventBusForAccentColor.colorChanged.notifyObservers(Color.parseColor("#FFBB00"));
        return view;
    }


    Singleton<TableLayout> table = new Singleton<TableLayout>() {
        @Override
        protected TableLayout onCreate() {
            TableLayout tableLayout = MakeDummy.table(
                    context,
                    MakeDummy.row(
                            context,
                            Atom.textView(context,"Bg Color"),
                            Atom.textView(context,"Accent Color")
                    ),
                    MakeDummy.row(
                            context,
                            new ColorOutputAsBackground(context,eventBusForBgColor).getView(),
                            new ColorOutputAsBackground(context,eventBusForAccentColor).getView()
                    ),
                    MakeDummy.row(
                            context,
                            new GrayScaleColorOutput(context,eventBusForBgColor).getView(),
                            new GrayScaleColorOutput(context,eventBusForAccentColor).getView()
                    ),
                    MakeDummy.row(
                            context,
                            new ColorOutputAsRelativeLuminance(context,eventBusForBgColor).getView(),
                            new ColorOutputAsRelativeLuminance(context,eventBusForAccentColor).getView()
                    ),
                    MakeDummy.row(
                            context,
                            Atom.textView(context,"Contrast Ratio:  "),
                            new ViewForColorContrast(context,eventBusForBgColor,eventBusForAccentColor).getView()
                    ),
                    MakeDummy.row(
                            context,
                            Atom.textView(context,"Text on background:  "),
                            new ViewForTextOnBackground(context,eventBusForBgColor,eventBusForAccentColor).getView()
                    ),
                    MakeDummy.row(
                            context,
                            MakeDummy.textView(context,"Text on Accent:  "),
                            new ViewForTextOnBackground(context,eventBusForAccentColor,eventBusForBgColor).getView()
                    ),
                    makeRowForSectionLabel("HEX (no hash)"),
                    MakeDummy.row(
                            context,
                            hexEditorForBgColor.instance().getView(),
                            hexEditorForAccentColor.instance().getView()
                    ),
                    makeRowForSectionLabel("RGB"),
                    MakeDummy.row(
                            context,
                            new EditTextBasedRGBColorInputDevice(context,eventBusForBgColor).getView(),
                            new EditTextBasedRGBColorInputDevice(context,eventBusForAccentColor).getView()
                    ),
                    makeRowForSectionLabel("HSB"),
                    MakeDummy.row(
                            context,
                            new EditTextBasedHSBColorInputDevice(context,eventBusForBgColor).getView(),
                            new EditTextBasedHSBColorInputDevice(context,eventBusForAccentColor).getView()
                    )


            );
            return tableLayout;
        }
    };

    private TableRow makeRowForSectionLabel(String label) {
        return MakeDummy.row(
                context,
                MakeDummy.paddingTop(MakeDummy.textView(context,label),32),
                MakeDummy.textView(context,"")

        );
    }

    EventBus eventBusForBgColor = new EventBus();
    EventBus eventBusForAccentColor = new EventBus();

    Singleton<HexColorInputDeviceUsingEditBox> hexEditorForBgColor = new Singleton<HexColorInputDeviceUsingEditBox>() {
        @Override
        protected HexColorInputDeviceUsingEditBox onCreate() {
            return new HexColorInputDeviceUsingEditBox(context,eventBusForBgColor);
        }
    };

    Singleton<HexColorInputDeviceUsingEditBox> hexEditorForAccentColor = new Singleton<HexColorInputDeviceUsingEditBox>() {
        @Override
        protected HexColorInputDeviceUsingEditBox onCreate() {
            return new HexColorInputDeviceUsingEditBox(context,eventBusForAccentColor);
        }
    };





    Singleton<View> topBar = new Singleton<View>() {
        @Override
        protected View onCreate() {
            LinearLayout topBar = MakeDummy.linearLayoutHorizontal(
                    context,
                    home.instance(),
                    about.instance(),
                    accentButton.instance()
            );
            topBar.setBackgroundColor(backgroundColor);
            return topBar;
        }
    };

    private Singleton<View> home = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TextView view = Atom.textView(context,"Home");
            view.setTextColor(Color.parseColor("#ffffff"));
            view.setPadding(24,24,24,24);
            return view;
        }
    };

    private Singleton<View> about = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TextView view = Atom.textView(context,"About");
            view.setTextColor(Color.parseColor("#ffffff"));
            view.setPadding(24,24,24,24);
            return view;
        }
    };

    private Singleton<View> accentButton = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TextView view = Atom.textView(context,"Donate");
            view.setTextColor(Color.parseColor("#ffffff"));
            view.setBackgroundColor(accentColor);
            view.setPadding(24,24,24,24);
            return view;
        }
    };


    private int backgroundColor = Color.parseColor("#0086BF");
    private int accentColor = Color.parseColor("#FFBB00");

    private void updateUI() {
        topBar.instance().setBackgroundColor(backgroundColor);
        accentButton.instance().setBackgroundColor(accentColor);

        backgroundColorBrightness.instance().setText(
                String.valueOf(ColorCalc.getBrightness(backgroundColor))
        );

        accentColorBrightness.instance().setText(
                String.valueOf(ColorCalc.getBrightness(accentColor))
        );
    }


    private Singleton<View> results = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TableLayout tableLayout = MakeDummy.table(
                    context,
                    MakeDummy.row(
                            context,
                            editBackgroundColor.instance(),
                            backgroundColorBrightness.instance()
                    ),
                    MakeDummy.row(
                            context,
                            editAccentColor.instance(),
                            accentColorBrightness.instance()
                    )
            );
            return tableLayout;
        }
    };

    private Singleton<TextView> backgroundColorBrightness = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            EditText editText = Atom.editText(context,"Bg Bright",null);
            editText.setFocusable(false);
            return editText;
        }
    };

    private Singleton<TextView> accentColorBrightness = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            EditText editText = Atom.editText(context,"Acc Bright",null);
            editText.setFocusable(false);
            return editText;
        }
    };

    private Singleton<View> editBackgroundColor = new Singleton<View>() {
        @Override
        protected View onCreate() {
            EditText view = Atom.editText(context, "Bg Color", "", new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                    backgroundColorChanged(editable.toString());
                }
            });

            return view;
        }
    };


    private void backgroundColorChanged(String newColor) {
        try{
            handleBackgroundColorChanged(newColor);
        }
        catch (Exception ex){

        }
    }

    private void handleBackgroundColorChanged(String strColor) {
        backgroundColor = Color.parseColor(strColor);
        updateUI();

    }

    private Singleton<View> editAccentColor = new Singleton<View>() {
        @Override
        protected View onCreate() {
            EditText view = Atom.editText(context, "Accent Color", "", new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                   accentChanged(editable.toString());
                }
            });

            return view;
        }
    };

    private void accentChanged(String strNewAccentColor) {
        try{
            handleAccentColorChanged(strNewAccentColor);
        }
        catch (Exception ex){

        }
    }

    private void handleAccentColorChanged(String strNewAccentColor) {
        accentColor = Color.parseColor(strNewAccentColor);
        updateUI();

    }


}
