package com.skyvolt.jabber;

import android.app.Activity;
import android.os.Bundle;

import client.ui.display.users.SignupWizard;
import libraries.android.MessageBox;
import libraries.android.OTPRetriever;
import shared.BaseActivity;

public class SignUpActivity extends BaseActivity {
    SignupWizard signupWizard;
    OTPRetriever otpRetriever;
    OTPRetriever.Parameters otpRetrieverParameters = new OTPRetriever.Parameters() {
        @Override
        public Activity activity() {
            return SignUpActivity.this;
        }

        @Override
        public void onFindOtp(String otp) {
            signupWizard.updateOTP(otp);
        }

        @Override
        public void otpNotFoundInMessage() {
            MessageBox.show("OTP not found in the message",SignUpActivity.this);

        }

        @Override
        public void onMessageNullOrEmpty() {
            MessageBox.show("Text message empty",SignUpActivity.this);
        }

        @Override
        public void onStatusNull() {
            MessageBox.show("Status is null",SignUpActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupWizard = new SignupWizard(this);
        otpRetriever = new OTPRetriever(otpRetrieverParameters);
        setContentView(signupWizard.getView());
        setTitle("Sign Up");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        otpRetriever.onDestroy();
    }
}
