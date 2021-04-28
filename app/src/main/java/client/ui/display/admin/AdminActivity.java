package client.ui.display.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;
import java.util.List;
import java.util.Map;

import client.data.AppCallback;
import client.data.Rest;
import client.data.Rest2;
import client.ui.AppBarGraph;
import client.ui.GotoActivity;
import core.businessobjects.admin.PerformanceData;
import core.businessobjects.admin.ProgressData;
import layers.render.Atom;
import libraries.android.HerbuyBarGraph;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.HerbuyCalendar;
import resources.Dp;
import resources.ItemColor;
import retrofit2.Call;
import retrofit2.Response;
import shared.BaseActivity;

public class AdminActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Admin Panel");
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
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                monitorProgress(),
                vspaceNormal(),
                evaluatePerformance(),
                vspaceNormal(),
                leaderboard(),
                vspaceNormal(),
                knowYourCustomers(),
                vspaceNormal()
        );
        return MakeDummy.scrollView(layout);
    }



    private View knowYourCustomers() {

        TextView title = Atom.textViewPrimaryBold(this, "Know Your Customers");
        title.setTextColor(ItemColor.primary());
        title.setTextSize(Dp.scaleBy(1.2f));


        Button customerAnalytics = Atom.button(this, "<small>Customer Analytics</small>", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.customerAnalytics(AdminActivity.this);
            }
        });
        customerAnalytics.setBackgroundResource(R.drawable.button_accent);



        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this, title,vspaceNormal(),
                customerAnalytics,
                vspaceNormal()
        );
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);

        //=========================

        //==============================
        return layout;
    }

    private View evaluatePerformance() {
        final TextView title = Atom.textViewPrimaryBold(this, "Performance Report");
        title.setTextColor(ItemColor.primary());
        title.setTextSize(Dp.scaleBy(1.2f));

        final LinearLayout reportList = MakeDummy.linearLayoutVertical(this);
        Rest2.api().getPerformanceReport().enqueue(new AppCallback<List<PerformanceData>>() {
            @Override
            protected void onSuccess(Call<List<PerformanceData>> call, Response<List<PerformanceData>> response) {
                if(response == null || response.body().size() == 0){
                    reportList.addView(Atom.centeredText(AdminActivity.this,"No data yet"));
                }
                else{


                    for(PerformanceData data: response.body()){

                        HerbuyBarGraph performanceBarGraph = new AppBarGraph()
                                .addBar("Accounts created",data.getAccountsCreated())
                                .addBar("Searches conducted", data.getSearchesConducted())
                                .addBar("Proposals sent", data.getProposalsSent())
                                .addBar("Proposals approved", data.getProposalsApproved())
                                .addBar("Rides shared", data.getRidesShared())
                                .addBar("TripsCompleted", data.getTripsCompleted());

                        reportList.addView(Atom.textView(AdminActivity.this,data.getReportDescription()));
                        reportList.addView(performanceBarGraph.getView(AdminActivity.this));
                    }
                }

            }

            @Override
            protected void onError(Call<List<PerformanceData>> call, Throwable t) {
                MessageBox.show(t.getMessage(),AdminActivity.this);
            }
        });


        LinearLayout layout = MakeDummy.linearLayoutVertical(this, title, reportList);
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }


    private View leaderboard() {
        TextView title = Atom.textViewPrimaryBold(this, "Leaderboard");
        title.setTextColor(ItemColor.primary());
        title.setTextSize(Dp.scaleBy(1.2f));

        final LinearLayout reportList = MakeDummy.linearLayoutVertical(this);
        Rest.api().getTop10Users().enqueue(new AppCallback<Map<String, Map<String, Integer>>>() {
            @Override
            protected void onSuccess(Call<Map<String, Map<String, Integer>>> call, Response<Map<String, Map<String, Integer>>> response) {

                if(response == null || response.body().size() == 0){
                    reportList.addView(Atom.centeredText(AdminActivity.this,"No data yet"));
                }
                else{
                    for(String criteria: response.body().keySet()) {

                        Map<String, Integer> item = response.body().get(criteria);
                        HerbuyBarGraph leaderboard = new AppBarGraph();
                        for (String userName : item.keySet()) {
                            leaderboard.addBar(userName,item.get(userName));
                        }
                        reportList.addView(leaderboard.getView(AdminActivity.this));
                    }
                }
            }

            @Override
            protected void onError(Call<Map<String, Map<String, Integer>>> call, Throwable t) {
                MessageBox.show(t.getMessage(), AdminActivity.this);
            }
        });


        LinearLayout layout = MakeDummy.linearLayoutVertical(this, title, reportList);
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }

    private View monitorProgress() {
        TextView title = Atom.textViewPrimaryBold(this, "Monitor Your Progress");
        title.setTextColor(ItemColor.primary());
        title.setTextSize(Dp.scaleBy(1.2f));

        LinearLayout layout = MakeDummy.linearLayoutVertical(this, title, progressReport());
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }

    private View progressReport() {
        final LinearLayout layout = MakeDummy.linearLayoutVertical(this);

        Rest2.api().getProgressReport().enqueue(new AppCallback<List<ProgressData>>() {
            @Override
            protected void onSuccess(Call<List<ProgressData>> call, Response<List<ProgressData>> response) {
                for(ProgressData item: response.body()){
                    layout.addView(makeViewForProgressIndicator(item));
                }
            }

            @Override
            protected void onError(Call<List<ProgressData>> call, Throwable t) {
                MessageBox.show(t.getMessage(),AdminActivity.this);
            }
        });
        return layout;
    }

    private View makeViewForProgressIndicator(ProgressData item) {
        TextView details = Atom.textViewPrimaryNormal(this,String.format(
                "First:%s; Most Recent: %s; Per day: %d; Per Month: %d",
                new HerbuyCalendar(item.getTimestampFirst()).getFriendlyDate(),
                new HerbuyCalendar(item.getTimestampMostRecent()).getFriendlyDate(),
                item.getPerDay(),item.getPerMonth()
        ));
        details.setTextSize(Dp.scaleBy(0.7f));

        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                Atom.textViewPrimaryBold(this, item.getIndicatorDescription()),
                Atom.textViewPrimaryNormal(this, String.format("Overall: %d", item.getOverall())),
                new AppBarGraph()
                        .addBar("Today",item.getToday())
                        .addBar("Yesterday", item.getYesterday())
                        .addBar("This Month", item.getThisMonth())
                        .addBar("Last Month", item.getLastMonth())
                        .getView(this),
                details


        );
        layout.setPadding(0, Dp.normal(), 0, Dp.normal());
        return layout;
    }

    private View header() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                title(),
                introduction(),
                prompt()
        );
        layout.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        layout.setBackgroundResource(R.drawable.header_background);
        return layout;
    }

    private View prompt() {
        TextView textView = Atom.textViewPrimaryNormal(this, "Choose what you want to do");
        textView.setTextSize(Dp.scaleBy(0.8f));
        return textView;
    }

    private View introduction() {
        TextView textView = Atom.textViewPrimaryNormal(this, "This admin panel will help you monitor your daily and monthly progress, manage users, as well as moderate the use of your app");
        textView.setTextSize(Dp.scaleBy(0.8f));
        return textView;
    }

    private View title() {
        TextView textView = Atom.textViewPrimaryBold(this,"Admin Panel");
        textView.setTextSize(Dp.scaleBy(1.5f));
        textView.setTextColor(ItemColor.primary());
        return textView;
    }
}
