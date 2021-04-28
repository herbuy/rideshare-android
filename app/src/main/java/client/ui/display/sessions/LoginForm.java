package client.ui.display.sessions;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import client.data.DummyData;
import core.businessmessages.toServer.ParamsForLogin;
import core.validators.ValidatorForLoginParams;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.RelativeLayoutFactory;
import libraries.android.TextChangedListener;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class LoginForm {
    AppCompatActivity context;
    ParamsForLogin paramsForLogin = new ParamsForLogin();
    TextView txtStatus;

    public LoginForm(AppCompatActivity context) {
        this.context = context;
    }

    public View getView() {
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                header(),
                body()

        );

        View finalLayout = RelativeLayoutFactory.alignAboveWidget(
                wrapper,
                actionAreaForLogin.instance()
        );
        return finalLayout;
    }

    private int horizontalPadding = Dp.scaleBy(3);
    private View body(){
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                mobileNumber(),
                vspaceNormal(),
                password()
        );
        layout.setPadding(horizontalPadding,Dp.two_em(),horizontalPadding,0);
        return layout;
    }

    private View header(){
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                image(),
                title()
        );
        layout.setGravity(Gravity.CENTER_VERTICAL |Gravity.CENTER_HORIZONTAL);
        layout.setBackgroundColor(ItemColor.primary());
        layout.setPadding(horizontalPadding,Dp.normal(),horizontalPadding,Dp.normal());
        return layout;
    }

    private Singleton<View> actionAreaForLogin = new Singleton<View>() {
        @Override
        protected View onCreate() {
            RelativeLayout layout = RelativeLayoutFactory.alignleftOfWidget(
                    status(),
                    btnLogin()
            );
            View finalLayout = MakeDummy.linearLayoutVertical(
                    context,
                    layout

            );
            finalLayout.setPadding(horizontalPadding,Dp.normal(),horizontalPadding,Dp.normal());
            return finalLayout;
        }
    };

    private View title() {
        TextView view = Atom.textViewPrimaryBold(context, "<big>Login</big>");
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        view.setTextColor(Color.WHITE);
        return view;
    }

    private View vspaceNormal() {
        return MakeDummy.lineSeparator(context,Dp.normal());
    }

    private View image() {
        return MakeDummy.wrapContent(
                MakeDummy.linearLayoutVertical(
                        context,
                        DummyData.randomCircleImageView(context,Dp.scaleBy(5))
                )
        );
    }

    private View status() {
        txtStatus = Atom.textViewForError(context);
        txtStatus.setVisibility(View.VISIBLE);
        txtStatus.setPadding(0,0,Dp.normal(),0);
        return txtStatus;
    }

    private View btnLogin() {
        return Atom.button(context, "Login", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    validator.run(paramsForLogin);
                }
                catch (Exception ex){
                    MessageBox.show(ex.getMessage(),context);
                }
            }
        });
    }

    private ValidatorForLoginParams validator = new ValidatorForLoginParams() {
        @Override
        protected void parametersNotProvided() {
            super.parametersNotProvided();
        }

        @Override
        protected void mobileNumberNotProvided() {
            super.mobileNumberNotProvided();
        }

        @Override
        protected void passwordNotProvided() {
            super.passwordNotProvided();
        }

        @Override
        protected void onSuccess(ParamsForLogin params) {
            onLoginClick(params);
        }
    };

    protected abstract void onLoginClick(ParamsForLogin paramsForLogin);

    private View mobileNumber() {
        EditText editText = Atom.editText(context, "Mobile Number <small>   E.g. 0772123456</small>", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                paramsForLogin.setMobileNumber(s.toString());
            }
        });
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        return editText;
    }

    private View password() {
        return Atom.password(context, "Password", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                paramsForLogin.setPassword(s.toString());
            }
        });
    }

    public void renderBusy() {
        txtStatus.setText("Logging In...");
    }

    public void renderSuccess() {
        txtStatus.setText("Login Successful");
    }
    public void renderError(String message) {
        txtStatus.setText(message);
    }
}
