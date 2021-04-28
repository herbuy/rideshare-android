package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetFamilyMembers;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class RateTripDialog implements HerbuyView {
    Context context;
    Family familyOrMarriage;
    FrameLayoutBasedHerbuyStateView view;


    public RateTripDialog(Context context, Family familyOrMarriage) {
        this.context = context;
        this.familyOrMarriage = familyOrMarriage;
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.getView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.getView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public View getView() {
        init();
        return view.getView();

    }

    private void init() {
        view.render(Atom.centeredText(context,"Loading..."));
        Rest2.api().getFamilyMembers(params()).enqueue(new AppCallback<List<FamilyMember>>() {
            @Override
            protected void onSuccess(Call<List<FamilyMember>> call, Response<List<FamilyMember>> response) {
                if (response.body() == null || response.body().size() < 1) {
                    view.render(Atom.centeredText(context, "Members not found"));
                } else {
                    view.render(listOfMembers(response.body()));
                }
            }

            @Override
            protected void onError(Call<List<FamilyMember>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        init();
                    }
                });
            }
        });
    }

    private View listOfMembers(List<FamilyMember> body) {
        HerbuyAbsListViewer<FamilyMember> listViewer = new HerbuyAbsListViewer<FamilyMember>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                return new ListView(context);
            }

            @Override
            protected View createItemView(final Context context, FamilyMember member) {
                return new RateFamilyMemberDialog(context,member).getView();
            }
        };
        listViewer.setContent(body);

        TextView header = Atom.textViewPrimaryNormal(context, "<b>Rate fellow travellers</b><br/><small><font color='#666666'>Rate the people you travelled with</font></small>");
        header.setTextSize(Dp.scaleBy(1.2f));
        header.setPadding(Dp.two_em(), Dp.normal(), Dp.two_em(), Dp.normal());
        header.setBackgroundResource(R.drawable.header_background);

        LinearLayout layout = MakeDummy.linearLayoutVertical(context);
        layout.addView(header);
        layout.addView(listViewer.getView());
        layout.setBackgroundColor(Color.WHITE);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return layout;
    }

    private ParamsForGetFamilyMembers params() {
        ParamsForGetFamilyMembers params = new ParamsForGetFamilyMembers();
        params.setSessionId(LocalSession.instance().getId());
        params.setFamilyId(familyOrMarriage.getFamilyId());
        params.setOnlyIfCanBeRatedByUserId(LocalSession.instance().getUserId());
        return params;
    }
}
