package layers.render;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skyvolt.jabber.R;

import core.businessobjects.User;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import libraries.android.TextChangedListener;
import libraries.android.ColorCalc;
import resources.Dp;
import resources.ItemColor;

public class Atom {
    public static View title(Context context, CharSequence text) {
        /*TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1.5f));
        textView.setText(text);
        return textView;*/

        String[] words = text.toString().split("\\s+");

        LinearLayout wrapper = MakeDummy.linearLayoutHorizontal(context);
        for(int i =0; i < words.length; i++){
            String word = words[i];
            TextView textView = new TextView(context);
            textView.setTextSize(Dp.scaleBy(1.5f));
            textView.setText(word + " ");
            wrapper.addView(textView);

            if(i == 0){
                textView.setTextColor(ItemColor.primary());
            }
            else{
                textView.setTextColor(Color.parseColor("#242424"));
            }
        }
        return wrapper;

    }
    public static View introduction(Context context, CharSequence text) {
        TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1f));
        textView.setText(text);
        textView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return textView;
    }
    public static View about(Context context, CharSequence text) {
        TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1f));
        textView.setText(text);
        textView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return textView;
    }

    public static View instructions(Context context, CharSequence text) {
        TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1f));
        textView.setText(text);
        textView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return textView;

    }

    public static View question(Context context, CharSequence text) {
        TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1f));
        textView.setText(text);
        textView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return textView;
    }

    public static View hint(Context context, CharSequence text) {
        TextView textView = new TextView(context);
        textView.setTextSize(Dp.scaleBy(1f));
        textView.setText(text);
        textView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return textView;
    }

    public static View userPicture(Context context,User user){
        ImageView imageView = new SquaredImageVIew(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(8), Dp.scaleBy(8)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(
                RandomUserImageResource.select()
        );
        return imageView;
    }


    public static TextView userName(Context context, String userName) {
        return MakeDummy.textColor(MakeDummy.textView(context,userName),Color.parseColor("#242424"));
    }

    public static TextView sectionLabel(Context context, String text) {
        TextView textView = MakeDummy.textView(context, text.toUpperCase());
        textView.setTextSize(Dp.scaleBy(1.5f));
        //textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(Color.parseColor("#242424"));
        //textView.setAlpha(0.5f);
        textView.setPadding(0,Dp.scaleBy(0.5f),0,Dp.scaleBy(0.5f));
        return textView;
    }

    public static Button button(Context context, String text, View.OnClickListener onClickListener){
        Button button = new Button(context);
        button.setText(Html.fromHtml(text));
        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(onClickListener);
        button.setTextSize(Dp.normal());

        button.setPadding(Dp.two_em(), Dp.normal(), Dp.two_em(), Dp.normal());
        return button;
    }

    public static TextView buttonTextView(Context context, String text, View.OnClickListener onClickListener){
        text = text.toUpperCase();
        TextView btn = Atom.textView(context,text);
        btn.setClickable(true);
        btn.setBackgroundResource(R.drawable.button);
        btn.setTextColor(Color.WHITE);
        btn.setPadding(Dp.one_point_5_em(),Dp.scaleBy(0.7f),Dp.one_point_5_em(),Dp.scaleBy(0.7f));
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setTextSize(Dp.scaleBy(0.85f));
        btn.setOnClickListener(onClickListener);
        return btn;
    }

    public static TextView textViewForError(Context context) {
        TextView textView = Atom.textView(context,"");
        textView.setTextColor(ItemColor.error());
        textView.setVisibility(View.GONE);
        textView.setAlpha(0.8f);
        textView.setTextSize(Dp.scaleBy(0.7f));
        //textView.setLineHeight(Dp.scaleBy(0.5f));
        return textView;
    }

    public static TextView textView(Context context, String text, float textSize) {
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        textView.setTextSize(textSize);

        return textView;
    }
    public static TextView textView(Context context, String text){
        return textView(context,text,Dp.normal());
    }
    private static TextView textViewPrimary(Context context, String text, int typefaceStyle){
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        textView.setTextColor(ItemColor.textPrimary());
        textView.setTypeface(null,typefaceStyle);

        //textView.setTypeface(ResourcesCompat.getFont(context,R.font.producer_regular),typefaceStyle);
        return textView;
    }
    public static TextView textViewPrimaryBold(Context context, String text){
        TextView view = textViewPrimary(context,text,Typeface.BOLD);
        return view;
    }
    public static TextView textViewPrimaryNormal(Context context, String text){
        return textViewPrimary(context, text,Typeface.NORMAL);
    }

    public static TextView textViewSecondary(Context context, String text){
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        textView.setTextSize(Dp.scaleBy(0.95f));
        textView.setTextColor(ItemColor.textSecondary());
        return textView;
    }


    public static EditText editText(Context context, String hint,String text, int inputType, TextChangedListener textChangedListener) {
        if(textChangedListener == null){
            textChangedListener = emptyTextChangedListener();
        }

        final EditText editText = new EditText(context);
        editText.setHint(Html.fromHtml(hint));
        if(text != null && !text.trim().equalsIgnoreCase("")){
            editText.setText(text);
        }
        editText.setInputType(inputType);
        editText.addTextChangedListener(textChangedListener);
        editText.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());

        final int defaultBackgroundResource = R.drawable.border_muted_color;
        editText.setBackgroundResource(defaultBackgroundResource);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    editText.setBackgroundResource(R.drawable.border_primary_color);
                }
                else{
                    editText.setBackgroundResource(defaultBackgroundResource);
                }
            }
        });
        return editText;
    }

    public static EditText editText(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text,InputType.TYPE_CLASS_TEXT,textChangedListener);
    }

    public static EditText editPersonName(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text,InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME,textChangedListener);
    }

    public static EditText editProperNoun(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_TEXT_FLAG_CAP_WORDS,textChangedListener);
    }

    public static EditText editUppercase(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text,InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS,textChangedListener);
    }

    public static EditText editText(Context context, String hint, TextChangedListener textChangedListener) {
        if(textChangedListener == null){
            textChangedListener = emptyTextChangedListener();
        }
        return editText(context,hint,"",textChangedListener);
    }

    public static TextChangedListener emptyTextChangedListener() {
        TextChangedListener textChangedListener;
        textChangedListener = new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textChangedListener;
    }

    public static EditText password(Context context, String hint,String text, TextChangedListener textChangedListener){
        EditText pass = editText(context,hint,text,InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,textChangedListener);
        return pass;
    }

    public static EditText password(Context context, String hint, TextChangedListener textChangedListener){
        return password(context,hint,"",textChangedListener);
    }

    private static View circle(Context context, View child) {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(Dp.four_em(), Dp.four_em()));
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        if(child != null){
            layout.addView(child);
        }
        return layout;
    }

    public static View circle_opaque(Context context, View child) {
        View textView = circle(context, child);
        textView.setBackgroundResource(R.drawable.circle);
        return textView;
    }

    public static View circle_transparent(Context context, View child) {
        return circle(context,child);
    }

    public static View lightBackground(View content) {
        LinearLayout linearLayout = MakeDummy.linearLayoutVertical(content.getContext());
        linearLayout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        linearLayout.setBackgroundColor(ColorCalc.mixColors(
                ItemColor.primary(),
                Color.WHITE,
                0.9f
        ));
        linearLayout.addView(content);
        return linearLayout;
    }

    public static View lightBackground(Context context, String text) {
        return lightBackground(Atom.textViewPrimaryNormal(context, text));
    }

    public static View centeredText(Context context, String text) {
        return MakeDummy.padding(
                MakeDummy.centerHorizontal(
                        Atom.textView(context, text)
                ),
                Dp.scaleBy(4)
        );
    }

    public static TextInputLayout textInputLayout(Context context, String hint, String text, boolean focusable, View.OnClickListener onClickListener, TextWatcher onTextChangedListener) {
        final TextInputEditText editText = new TextInputEditText(context);

        editText.setHint(Html.fromHtml(hint));
        editText.setText(Html.fromHtml(text));
        editText.setFocusable(focusable);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setPadding(0,0,0,0);
        editText.setOnClickListener(onClickListener);

        if(onTextChangedListener != null){
            editText.addTextChangedListener(onTextChangedListener);
        }
        else{
            editText.addTextChangedListener(new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


        TextInputLayout textInputLayout = new TextInputLayout(context);
        textInputLayout.addView(editText);
        textInputLayout.setHintTextAppearance(R.style.HintText);


        textInputLayout.setBackgroundResource(R.drawable.header_background);
        textInputLayout.setPadding(Dp.four_em(),Dp.normal(),Dp.four_em(),Dp.normal());
        textInputLayout.setOnClickListener(onClickListener);


        return textInputLayout;
    }
}
