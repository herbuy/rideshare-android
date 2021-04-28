package com.skyvolt.jabber;

import android.app.Application;
import android.test.ApplicationTestCase;

import libraries.android.MessageBox;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        MessageBox.show("Testing one two",getContext());
    }
}