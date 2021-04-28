package client.ui.display.users;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import layers.render.Atom;
import libraries.CodeBlock;
import libraries.android.MakeDummy;
import resources.Dp;
import shared.BaseActivity;

public class FindPeopleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Find People");
        setContentView(contentView());
    }

    private View contentView() {
        final Context context = this;
        return new CodeBlock(){

            private View peopleCategory(String text,View.OnClickListener onClickListener){
                TextView textView = Atom.textViewPrimaryNormal(context,text);
                textView.setOnClickListener(onClickListener);
                textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
                return MakeDummy.linearLayoutVertical(
                        context,
                        textView,
                        MakeDummy.lineSeparator(context,1, Color.parseColor("#eeeeee"))
                );
            }

            @Override
            public View getView() {
                LinearLayout layout = MakeDummy.linearLayoutVertical(
                        context,
                        peopleCategory("With Lifts",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Seeking Lifts",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Going To",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Been To",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Currently In",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Living In",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Working In",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Coming From",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("In My Location",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Travelled With In",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Recommeded/Endorsed",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        }),
                        peopleCategory("Chatted With",new View.OnClickListener(

                        ){
                            @Override
                            public void onClick(View v) {

                            }
                        })


                );

                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                layout.setBackgroundColor(Color.WHITE);
                return layout;
            }
        }.getView();
    }
}
