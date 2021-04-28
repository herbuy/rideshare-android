package libraries.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * provides a template for designing beautiful forms
 * For example, to design a login form,
 * create a class called SignInForm that extends FormDesign
 * then u can implete the abstract methods
 *
 * background - this method returns the background of the form. U can use background image or color and put some padding if necessary
 * form - this method shd create the form background. For instance, if u want a white form on a pink backround, the form shd be white and the background pink, with some padding on the background to make in visible if the form occpies entire container
 * - header -- this method shd create what u want to see in the header ofform e.g u might want a colorized image on your header and some text such as the title of the form
 * If u dont want the header, u can perhaps return an invisible view
 * -body this method shd return what u want to see in the body. For instance, the form fields u want. For a sign in form, this is where u could put the email and password fields
 * -footer - this is where u put the elements u want to see in the footer of the form. For instance, the sign in button if designing a sign in form. U can even make it a different background.
 *
 */
public abstract class FormDesign {

    public View getView(Context context) {
        ViewGroup background = getBackground(context);
        background.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        background.setPadding(5, 5, 5, 5);
        ViewGroup form = getForm(context);
        form.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        form.addView(getHeader(context));
        form.addView(getBody(context));
        form.addView(getFooter(context));

        background.addView(form);

        ScrollView scrollView = getScrollViewOrNull(context);

        if (scrollView != null) {
            scrollView.addView(background);
            return scrollView;
        } else {
            return background;
        }

    }

    protected abstract ScrollView getScrollViewOrNull(Context context);

    protected abstract View getFooter(Context context);

    protected abstract View getBody(Context context);

    protected abstract View getHeader(Context context);

    protected abstract ViewGroup getForm(Context context);

    public abstract ViewGroup getBackground(Context context);
}
