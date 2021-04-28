package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.textfield.TextInputLayout;

import layers.render.Atom;
import libraries.android.SetKeyboardTypeToAlphaNumeric;
import libraries.android.ShowKeyboard;

public abstract class TextInputDialog {
    private Context context;

    final TextInputLayout inputLayout;

    public TextInputDialog(Context context) {
        this.context = context;
        inputLayout = Atom.textInputLayout(context, getHintText(), "", true, null, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        dialog.setTitle(getTitleText());
        dialog.setView(inputLayout);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onClickOk(inputLayout.getEditText().getText().toString());
            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        dialog.show();

        //on set keyboard type [on show/after show]
        SetKeyboardTypeToAlphaNumeric.where(inputLayout.getEditText());
        ShowKeyboard.where(inputLayout.getEditText());
    }

    protected abstract void onClickOk(String newValue);

    protected abstract String getTitleText();

    protected abstract String getHintText();

    public String getText(){
        if(inputLayout.getEditText().getText() == null){
            return "";
        }
        return inputLayout.getEditText().getText().toString();
    }
}
