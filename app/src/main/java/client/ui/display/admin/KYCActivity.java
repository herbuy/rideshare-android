package client.ui.display.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.Map;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyStateView;
import resources.Dp;
import resources.ItemColor;
import retrofit2.Call;
import retrofit2.Response;
import shared.BaseActivity;

public class KYCActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
    }

    private View contentView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                header(),
                vspaceNormal(),
                sections()
        );
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
        return layout;
    }

    private View vspaceNormal() {
        return MakeDummy.lineSeparator(this, Dp.normal());
    }

    private View sections() {
        final FrameLayoutBasedHerbuyStateView view = new FrameLayoutBasedHerbuyStateView(this);
        //get the data
        view.render(Atom.centeredText(this,"Loading..."));
        Rest.api().getTravelAnalytics().enqueue(new AppCallback<Map<String, Map<String, Integer>>>() {
            @Override
            protected void onSuccess(Call<Map<String, Map<String, Integer>>> call, Response<Map<String, Map<String, Integer>>> response) {
                if(response.body() == null || response.body().size() < 1){
                    view.render(Atom.centeredText(KYCActivity.this,"No content, will appear here"));
                }
                else{
                    view.render(analytics(response.body()));
                }


            }

            @Override
            protected void onError(Call<Map<String, Map<String, Integer>>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        sections();
                    }
                });

            }
        });
        //present the data
        return MakeDummy.scrollView(view.getView());
    }

    private View analytics(Map<String, Map<String, Integer>> body) {
        LinearLayout layout = MakeDummy.linearLayoutVertical(this);
        for(String key: body.keySet()){
            layout.addView(metric(key, body.get(key)));
        }
        return layout;
    }

    private View metric(String key, Map<String, Integer> stringIntegerMap) {
        TextView title = Atom.textViewPrimaryBold(this, key);
        title.setTextColor(ItemColor.primary());
        title.setTextSize(Dp.scaleBy(1.2f));

        LinearLayout layout = MakeDummy.linearLayoutVertical(this,title/*, details(stringIntegerMap)*/, barchart(stringIntegerMap));
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }

    private View barchart(Map<String, Integer> stringIntegerMap) {

        BarChart barChart = new BarChart(this);

        barChart.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                256
        ));
        barChart.setPadding(10, 10, 10, 10);
        barChart.setFixedBarWidth(true);
        barChart.setBarWidth(20);
        barChart.setLegendHeight(Dp.scaleBy(2));


        for(String key: stringIntegerMap.keySet()){
            barChart.addBar(new BarModel(key,stringIntegerMap.get(key),Color.parseColor("#bbbbbb")));
        }



        //barChart.addBar(new BarModel("Mon",300,ItemColor.primary()));
        //barChart.addBar(new BarModel("Tue",200,ItemColor.primary()));
        barChart.startAnimation();
        return barChart;
    }

    private View details(Map<String, Integer> stringIntegerMap) {
        LinearLayout layout = MakeDummy.linearLayoutVertical(this);
        for(String key: stringIntegerMap.keySet()){
            layout.addView(Atom.textViewPrimaryNormal(this,String.format("%s: %d",key,stringIntegerMap.get(key))));
        }
        return layout;
    }

    private View header() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                title(),
                introduction()
        );
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }


    private View introduction() {
        TextView textView = Atom.textViewPrimaryNormal(this, "This panel allows you to gain a better understanding of your customers in order to server them better");
        textView.setTextSize(Dp.scaleBy(0.8f));
        return textView;
    }

    private View title() {
        TextView textView = Atom.textViewPrimaryBold(this,"Know your Customers");
        textView.setTextSize(Dp.scaleBy(1.5f));
        textView.setTextColor(ItemColor.primary());
        return textView;
    }
}
