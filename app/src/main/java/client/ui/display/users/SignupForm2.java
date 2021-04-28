package client.ui.display.users;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Timer;
import java.util.TimerTask;

import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import libraries.android.ColorCalc;
import libraries.android.GetParentActivity;
import libraries.android.HerbuyViewPager;
import core.businessmessages.toServer.ParamsForCreateUser;
import core.businessmessages.toServer.ParamsForRequestAccount;
import core.businessmessages.toServer.ParamsForVerifyAccount;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.RelativeLayoutFactory;
import libraries.android.ShowKeyboard;
import libraries.android.TextChangedListener;
import libraries.android.AdapterForFragmentPager;
import libraries.android.StringUnchangedListener;
import libraries.java.DoCapitalize;
import libraries.java.Validator;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;
import core.validators.ValidatorFor;

public abstract class SignupForm2 {
    private static final int PAGE_FOR_VERIFY = 1;
    private static final int PAGE_FOR_COMPLETE_SIGNUP = 2;

    private static final String DEFAULT_TEXT_FOR_REQUEST_ACCOUNT_BUTTON = "Verify";
    private static final String DEFAULT_TEXT_FOR_CREATE_ACCOUNT_BUTTON = "Create Account";

    AppCompatActivity context;
    ParamsForCreateUser paramsForCreateUser = new ParamsForCreateUser();
    TextView txtStatus;

    public SignupForm2(AppCompatActivity context) {
        this.context = context;
    }


    public View getView() {
        View view = MakeDummy.linearLayoutVertical(
                context,
                header.instance(),
                viewPagerHost.instance()
        );

        view.setBackgroundColor(Color.WHITE);
        ShowKeyboard.where(mobileNumberToVerify.instance());
        return view;

    }


    Singleton<View> header = new Singleton<View>() {
        @Override
        protected View onCreate() {
            TextView textView = Atom.textViewPrimaryBold(context, "New Account");
            //format(textView);
            textView.setBackgroundColor(ItemColor.primary());
            textView.setTextColor(Color.WHITE);
            textView.setPadding(Dp.two_em(),Dp.scaleBy(0.5f),Dp.two_em(),Dp.scaleBy(0.5f));
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(Dp.scaleBy(1.3f));
            return textView;
        }

    };

    HerbuyViewPager viewPager;

    Singleton<View> viewPagerHost = new Singleton<View>() {
        @Override
        protected View onCreate() {
            View root = LayoutInflater.from(context).inflate(R.layout.view_pager, null);
            viewPager = root.findViewById(R.id.container);
            viewPager.setPagingEnabled(false);


            viewPager.setAdapter(
                    new AdapterForFragmentPager(context.getSupportFragmentManager())
                            .addTab(
                                    "Enter your mobile telephone number",
                                    pageForEnterMobileTelephone.instance()
                            )
                            .addTab(
                                    "Verify your number",
                                    pageForVerifyMobileTelephone.instance()
                            )
                            .addTab(
                                    "Complete your sign-up",
                                    pageForCompleteSignup.instance()
                            )
            );

            viewPager.setPadding(0,0,0,Dp.normal());
            formatContainer(root);
            return root;
        }
    };

    Singleton<ViewGroup> pageForVerifyMobileTelephone = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                    context,
                    titleForVerificationPage.instance(),
                    subTitleForVerificationPage(),
                    vspaceNormal(),
                    verificationSection.instance()
            );
            formatSection(wrapper);
                return RelativeLayoutFactory.alignAboveWidget(
                        wrapper,
                        actionAreaForVerifyMobileNumber.instance()
                );
        }
    };

    Singleton<View> verificationSection = new Singleton<View>() {
        @Override
        protected View onCreate() {

            return MakeDummy.linearLayoutVertical(
                    context,
                    RelativeLayoutFactory.alignleftOfWidget(
                            verificationCode.instance(),
                            resendButton.instance()
                    ),
                    errorInVerificationCode.instance()
            );
        }
    };

    Singleton<TextView> resendButton = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            final TextView resendButton = Atom.buttonTextView(context, "Resend", new OnClickListener() {
                @Override
                public void onClick(View view) {
                    tryResendVerificationCode();
                }
            });
            resendButton.setBackgroundResource(0);
            resendButton.setTextColor(ItemColor.primary());
            return resendButton;
        }
    };

    private void tryResendVerificationCode() {
        sendOrResendVerificationCode();
    }

    Singleton<View> actionAreaForVerifyMobileNumber = new Singleton<View>() {
        @Override
        protected View onCreate() {
            RelativeLayout layout = RelativeLayoutFactory.alignleftOfWidget(
                    Atom.textView(context,""),
                    btnGotoCompleteSignup.instance()
            );
            MakeDummy.padding(layout,Dp.one_point_5_em());
            return layout;
        }
    };

    Singleton<ViewGroup> pageForCompleteSignup = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                    context,
                    titleForCompleteSignupPage(),
                    vspaceNormal(),
                    username(),
                    errorInUserName.instance(),
                    vspaceNormal(),
                    passwordSection.instance()
            );
            formatSection(wrapper);
            return RelativeLayoutFactory.alignAboveWidget(
                    wrapper,
                    actionAreaForCompleteSignup.instance()
            );
        }
    };

    private Singleton<View> passwordSection = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return MakeDummy.linearLayoutVertical(
                    context,
                    RelativeLayoutFactory.alignleftOfWidget(
                            password.instance(),
                            passwordVisibilityToggleButton.instance()
                    ),
                    errorInPassword.instance()
            );

        }
    };

    private Singleton<ImageView> passwordVisibilityToggleButton = new Singleton<ImageView>() {
        @Override
        protected ImageView onCreate() {
            ImageView btnTogglePasswordVisibility = MakeDummy.imageView(context,32,32,R.drawable.ic_status_hidden);
            btnTogglePasswordVisibility.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnTogglePasswordVisibility.setOnClickListener(listenerTogglePasswordVisibility);
            btnTogglePasswordVisibility.setAlpha(0.4f);
            btnTogglePasswordVisibility.setPadding(Dp.scaleBy(0.5f),0,0,0);
            return btnTogglePasswordVisibility;
        }
    };

    private OnClickListener listenerTogglePasswordVisibility = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(passwordIsVisible()){

                makePasswordHidden();
            }
            else{
                makePasswordVisible();
            }
            //password.setTransformationMethod(new PasswordTransformationMethod());
            //password.setTransformationMethod(null);
        }
    };

    public boolean passwordIsVisible() {
        return password.instance().getTransformationMethod() == null;
    }

    public void makePasswordVisible() {
        saveSelectionStartAndEndForPasswordField();
        password.instance().setTransformationMethod(null);
        restoreSelectionStartAndEndForPasswordField();
        passwordVisibilityToggleButton.instance().setImageResource(R.drawable.ic_status_visible);
    }

    public void makePasswordHidden() {
        saveSelectionStartAndEndForPasswordField();
        password.instance().setTransformationMethod(new PasswordTransformationMethod());
        restoreSelectionStartAndEndForPasswordField();
        passwordVisibilityToggleButton.instance().setImageResource(R.drawable.ic_status_hidden);
    }

    private void restoreSelectionStartAndEndForPasswordField() {
        password.instance().setSelection(selectionStartForPasswordField,selectionEndForPasswordField);
    }

    private int selectionStartForPasswordField;
    private int selectionEndForPasswordField;
    private void saveSelectionStartAndEndForPasswordField() {
        selectionStartForPasswordField = password.instance().getSelectionStart();
        selectionEndForPasswordField = password.instance().getSelectionEnd();
    }

    private Singleton<View> actionAreaForCompleteSignup = new Singleton<View>() {
        @Override
        protected View onCreate() {
            RelativeLayout layout = RelativeLayoutFactory.alignleftOfWidget(
                    Atom.textView(context,""),
                    btnSignup()
            );
            layout.setPadding(Dp.one_point_5_em(),Dp.scaleBy(0.5f),Dp.one_point_5_em(),Dp.scaleBy(0.5f));
            return layout;
        }
    };

    Singleton<TextView> errorInPassword = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewForError(context);
        }
    };

    private View subtitleForCompleteSignup() {
        return Atom.textView(context, "<small>Enter your name and choose a password to protect your account.");
    }

    private View titleForCompleteSignupPage() {
        return Atom.textViewPrimaryBold(context, "Complete your sign up");
    }

    Singleton<View> btnGotoCompleteSignup = new Singleton<View>() {
        @Override
        protected View onCreate() {
            Button btn = Atom.button(context, "Continue", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(PAGE_FOR_COMPLETE_SIGNUP);
                }
            });
            btn.setVisibility(View.GONE);
            return btn;
        }
    };


    private void updateTitleForVerificationPage(String mobileNumberToVerify){
        String message = String.format("Verifying <font color='#%s'>%s</font>",ColorCalc.toHexNoAlpha(ItemColor.primary()),mobileNumberToVerify);
        titleForVerificationPage.instance().setText(Html.fromHtml(message));
    }


    Singleton<TextView> titleForVerificationPage = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewPrimaryBold(context, "Verifying your number");
        }
    };


    private View subTitleForVerificationPage() {
        TextView view = Atom.textView(context, "<small>Wait for an SMS with the verification code<br/>Then enter the code to continue</small>");
        view.setBackgroundColor(ColorCalc.mixColors(ItemColor.primary(),Color.TRANSPARENT,0.95f));
        view.setPadding(Dp.normal(),Dp.scaleBy(0.5f),Dp.normal(),Dp.scaleBy(0.5f));
        return view;
    }


    Singleton<TextView> errorInMobileNumber = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView txtErrorInMobileNumber = Atom.textViewForError(context);
            return txtErrorInMobileNumber;
        }
    };

    Singleton<ViewGroup> pageForEnterMobileTelephone = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                    context,
                    titleForEnterMobileNumber(),
                    subTitleForEnterMobileNumber(),
                    vspaceNormal(),
                    mobileNumberToVerify.instance(),
                    errorInMobileNumber.instance(),
                    vspaceNormal()
            );
            formatSection(wrapper);
            return RelativeLayoutFactory.alignAboveWidget(
                    wrapper,
                    MakeDummy.padding(
                            MakeDummy.linearLayoutVertical(
                                    context,
                                    RelativeLayoutFactory.alignleftOfWidget(Atom.textView(context,""),buttonForRequestAccount())
                            ),
                            Dp.one_point_5_em()
                    )
            );
            //return wrapper;
        }
    };


    private View titleForEnterMobileNumber() {
        return Atom.textViewPrimaryBold(context, "Enter your mobile number");
    }

    private View subTitleForEnterMobileNumber() {
        return Atom.textView(context, "<small>This number will be used to identify your account</small>");
    }

    private void formatSection(LinearLayout wrapper) {
        wrapper.setPadding(Dp.two_em(), Dp.normal(), Dp.two_em(), Dp.scaleBy(0.5f));
    }

    public void formatContainer(View wrapper) {
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setBackgroundColor(Color.WHITE);
    }

    Button btnRequestAccount;

    private View buttonForRequestAccount() {
        btnRequestAccount = Atom.button(context, DEFAULT_TEXT_FOR_REQUEST_ACCOUNT_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrResendVerificationCode();
            }
        });
        btnRequestAccount.setVisibility(View.INVISIBLE);
        return btnRequestAccount;
    }

    public void sendOrResendVerificationCode() {
        onRequestAccount(SignupForm2.this, paramsForRequestAccount);
        startCountDownToResend();

    }

    private void startCountDownToResend() {

        resendButton.instance().setClickable(false);
        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int count = 10;
            @Override
            public void run() {
                count -= 1;
                GetParentActivity.fromContext(context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resendButton.instance().setText(String.valueOf(count));
                    }
                });
                if(count < 1){
                    timer.cancel();
                    GetParentActivity.fromContext(context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            endCountDownToResend();
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    public void endCountDownToResend() {
        resendButton.instance().setText("Resend");
        resendButton.instance().setClickable(true);
    }

    public void indicateRequestingAccount() {
        btnRequestAccount.setClickable(false);
        btnRequestAccount.setText("Requesting account...");

    }

    protected abstract void onRequestAccount(SignupForm2 sender, ParamsForRequestAccount paramsForRequestAccount);


    private View vspaceNormal() {
        return MakeDummy.lineSeparator(context, Dp.normal());
    }


    Button btnCreateAccount;
    FrameLayoutBasedHerbuyStateView containerForBtnCreateAccount;

    private View btnSignup() {
        containerForBtnCreateAccount = new FrameLayoutBasedHerbuyStateView(context);

        btnCreateAccount = Atom.button(context, DEFAULT_TEXT_FOR_CREATE_ACCOUNT_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickCompleteSignup(SignupForm2.this, paramsForVerifyAccount);
            }
        });
        btnCreateAccount.setBackgroundResource(R.drawable.button);

        containerForBtnCreateAccount.render(btnCreateAccount);
        return containerForBtnCreateAccount.getView();
    }

    protected abstract void onClickCompleteSignup(SignupForm2 signupForm2, ParamsForVerifyAccount paramsForVerifyAccount);

    Singleton<TextView> errorInUserName = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            return Atom.textViewForError(context);
        }
    };


    StringUnchangedListener usernameNotChangedListener = new StringUnchangedListener(1000) {
        @Override
        public void callback(String value) {

            paramsForVerifyAccount.setUserName(value);

            ValidatorFor.usernameDuringSignup().validate(new Validator.Parameters() {
                @Override
                public String getValue() {
                    return paramsForVerifyAccount.getUserName();
                }

                @Override
                public void onSuccess(String inputValue) {
                    hideErrorInUsername();
                }

                @Override
                public void onError(String error, String inputValue) {
                    showErrorInUsername(error);

                }
            });
        }
    };

    EditText editTextUsername;
    private View username() {
        editTextUsername = Atom.editProperNoun(context, "Your Name <small>E.g. Felix</small>","", new TextChangedListener() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void afterTextChanged(final Editable s) {

                DoCapitalize.eachWord(s.toString(), true).then(new DoCapitalize.Result.NextTask() {
                    int savedCursorPosition = 0;

                    @Override
                    public void ifChanged(String newValue, String oldValue) {
                        saveCursorPosition();
                        Log.e("CURSOR POSITION", savedCursorPosition +"");
                        editTextUsername.setText(newValue);
                        editTextUsername.setSelection(savedCursorPosition);
                        //editTextUsername.setPo
                    }

                    public void saveCursorPosition() {
                        savedCursorPosition = editTextUsername.getSelectionStart();
                    }

                    @Override
                    public void otherWise(String oldValue) {
                        usernameNotChangedListener.set(s.toString().trim());
                    }
                });

            }
        });
        return editTextUsername;
    }

    public void showErrorInUsername(String error) {
        TransitionManager.beginDelayedTransition(pageForCompleteSignup.instance());
        errorInUserName.instance().setText(error);
        errorInUserName.instance().setVisibility(View.VISIBLE);
    }

    public void hideErrorInUsername() {
        TransitionManager.beginDelayedTransition(pageForCompleteSignup.instance());
        errorInUserName.instance().setVisibility(View.GONE);
    }

    ParamsForRequestAccount paramsForRequestAccount = new ParamsForRequestAccount();

    Validator mobileNumberValidator = ValidatorFor.mobileNumberDuringSignup();


    Singleton<EditText> mobileNumberToVerify = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            final EditText mobile = Atom.editText(context, "E.g. 0772123456", null);
            mobile.setInputType(InputType.TYPE_CLASS_NUMBER);
            mobile.addTextChangedListener(new TextChangedListener() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    super.beforeTextChanged(s, start, count, after);
                }

                @Override
                public void afterTextChanged(final Editable s) {

                    //validate the the value and if value, set the parameters

                    mobileNumberValidator.validate(new Validator.Parameters() {
                        @Override
                        public String getValue() {

                            return s.toString();
                        }

                        @Override
                        public void onSuccess(String inputValue) {
                            paramsForRequestAccount.setMobileNumber(inputValue);
                            paramsForVerifyAccount.setMobileNumber(inputValue);
                            hideErrorInMobileNumber();
                            updateTitleForVerificationPage(inputValue);

                            TransitionManager.beginDelayedTransition(pageForEnterMobileTelephone.instance());
                            if (inputValue.length() == 10) {
                                btnRequestAccount.setVisibility(View.VISIBLE);
                            } else {
                                btnRequestAccount.setVisibility(View.INVISIBLE);
                            }

                        }

                        @Override
                        public void onError(String error, String inputValue) {
                            showErrorInMobileNumber(error);
                        }
                    });


                }
            });


            return mobile;
        }
    };

    public void showErrorInMobileNumber(String error) {
        TransitionManager.beginDelayedTransition(pageForEnterMobileTelephone.instance());
        errorInMobileNumber.instance().setText(error);
        errorInMobileNumber.instance().setVisibility(View.VISIBLE);
    }

    public void hideErrorInMobileNumber() {
        TransitionManager.beginDelayedTransition(pageForEnterMobileTelephone.instance());
        errorInMobileNumber.instance().setVisibility(View.GONE);
    }


    ParamsForVerifyAccount paramsForVerifyAccount = new ParamsForVerifyAccount();


    Singleton<TextView> errorInVerificationCode = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView textView = Atom.textViewForError(context);
            return textView;
        }
    };

    Singleton<EditText> verificationCode = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            EditText code = Atom.editText(context, "Verification code", null);
            code.setInputType(InputType.TYPE_CLASS_NUMBER);
            code.addTextChangedListener(new TextChangedListener() {
                @Override
                public void afterTextChanged(final Editable s) {
                    ValidatorFor.verificationCodeDuringSignup().validate(new Validator.Parameters() {
                        @Override
                        public String getValue() {
                            return s.toString().trim();
                        }

                        @Override
                        public void onSuccess(String inputValue) {
                            hideErrorInVerificationCode();
                            if (inputValue != null && inputValue.length() == 4) {
                                showButtonForGotoCompleteSignup();
                                paramsForVerifyAccount.setVerificationCode(s.toString());
                            } else {
                                hideButtonForGotoCompleteSignup();
                            }
                        }

                        @Override
                        public void onError(String error, String inputValue) {
                            showErrorInVerificationCode(error);
                            hideButtonForGotoCompleteSignup();
                        }
                    });

                }
            });
            return code;
        }
    };

    public void hideButtonForGotoCompleteSignup() {
        TransitionManager.beginDelayedTransition(pageForVerifyMobileTelephone.instance());
        btnGotoCompleteSignup.instance().setVisibility(View.GONE);
    }

    public void showButtonForGotoCompleteSignup() {
        TransitionManager.beginDelayedTransition(pageForVerifyMobileTelephone.instance());
        btnGotoCompleteSignup.instance().setVisibility(View.VISIBLE);
    }

    public void showErrorInVerificationCode(String error) {
        errorInVerificationCode.instance().setText(error);
        TransitionManager.beginDelayedTransition(pageForVerifyMobileTelephone.instance());
        errorInVerificationCode.instance().setVisibility(View.VISIBLE);
    }

    public void hideErrorInVerificationCode() {
        TransitionManager.beginDelayedTransition(pageForVerifyMobileTelephone.instance());
        errorInVerificationCode.instance().setVisibility(View.GONE);
    }

    public void hideErrorInPassword() {
        TransitionManager.beginDelayedTransition(pageForCompleteSignup.instance());
        errorInPassword.instance().setVisibility(View.GONE);
    }

    public void showErrorInPassword(String error) {
        TransitionManager.beginDelayedTransition(pageForCompleteSignup.instance());
        errorInPassword.instance().setText(error);
        errorInPassword.instance().setVisibility(View.VISIBLE);
    }

    public void updateVerificationCode(String verificationCode) {
        this.verificationCode.instance().setText(verificationCode);
        this.verificationCode.instance().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(PAGE_FOR_COMPLETE_SIGNUP);
            }
        }, 400);
    }

    StringUnchangedListener passwordNotChangedListener = new StringUnchangedListener(1000) {
        @Override
        protected void callback(final String value) {
            ValidatorFor.password().validate(new Validator.Parameters() {
                @Override
                public String getValue() {
                    return value;
                }

                @Override
                public void onSuccess(String inputValue) {
                    paramsForVerifyAccount.setPassword(inputValue);
                    hideErrorInPassword();
                }

                @Override
                public void onError(String error, String inputValue) {
                    showErrorInPassword(error);
                }
            });

        }
    };

    Singleton<EditText> password = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            EditText password = Atom.password(context, "Password", new TextChangedListener() {
                @Override
                public void afterTextChanged(final Editable s) {
                    passwordNotChangedListener.set(s.toString().trim());
                }
            });

            password.post(new Runnable() {
                @Override
                public void run() {
                    makePasswordHidden();
                }
            });

            return password;
        }
    };

    public void onAccountRequestSuccessful() {
        viewPager.setCurrentItem(PAGE_FOR_VERIFY);

    }

    public void onAccountRequestFailed(String message) {
        btnRequestAccount.setClickable(true);
        btnRequestAccount.setText(DEFAULT_TEXT_FOR_REQUEST_ACCOUNT_BUTTON);
        MessageBox.show(message, context); //we might later use a snack bar
    }

    protected void indicateCompletingSignup() {
        containerForBtnCreateAccount.render(Atom.textView(context,"Completing sign-up..."));

    }

    public void indicateAccountCreatedSuccessfully() {
        containerForBtnCreateAccount.render(Atom.textView(context,"SUCCESSFUL!!"));
    }

    public void indicateErrorCreatingAccount(String message) {
        containerForBtnCreateAccount.render(btnCreateAccount);
        MessageBox.show("Could not create account. " + message, context);

    }


}
