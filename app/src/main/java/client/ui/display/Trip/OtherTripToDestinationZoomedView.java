package client.ui.display.Trip;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import client.data.AppCallback;
import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTripActivities;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessobjects.Proposal;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.ColorCalc;
import libraries.android.HerbuyConservativeView;
import libraries.android.MultiStateNetworkButton;
import resources.Dp;
import resources.ItemColor;
import retrofit2.Call;
import retrofit2.Response;

public abstract class OtherTripToDestinationZoomedView implements HerbuyView {

    final Context context;

    public OtherTripToDestinationZoomedView(Context context) {
        this.context = context;
    }

    public View getView() {


        LinearLayout text = lowerPart();

        ImageView imageView = upperPart();

        RelativeLayout itemsContainer = new RelativeLayout(context);
        itemsContainer.addView(imageView);
        itemsContainer.addView(text);
        formatPretty(text, imageView, itemsContainer);

        int bgColor = ColorCalc.multiplyBrightnessBy(
                0.1f,
                ColorCalc.multiplySaturationBy(0.1f,ColorCalc.inverseColor(ItemColor.primary()))
        );
        View item = HerbuyConservativeView.create(
                context,
                bgColor,
                Dp.two_em(),
                itemsContainer
        );

        item.setTransitionName("trans1");
        return item;

    }

    private void formatPretty(LinearLayout text, ImageView imageView, RelativeLayout itemsContainer) {
        itemsContainer.setPadding(Dp.two_em(), Dp.two_em(), Dp.two_em(), Dp.two_em());
        MakeDummy.stretchAcrossParentBottom(text);
        MakeDummy.alignAbove(text, imageView);
    }

    private ImageView upperPart() {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(DummyData.randomProfilePicResource());
        return imageView;
    }

    private LinearLayout lowerPart() {
        LinearLayout text = MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.verticalSeparator(context, Dp.normal()),
                textArea(),
                sendTTRequestButton(),
                MakeDummy.lineSeparator(context, Dp.normal())
        );
        text.setGravity(Gravity.CENTER_HORIZONTAL);
        return text;
    }

    private View sendTTRequestButton() {
        MultiStateNetworkButton multiStateNetworkButton = new MultiStateNetworkButton(context) {
            @Override
            protected View defaultView() {
                return MakeDummy.wrapContent(Atom.button(context, "Request Travel together", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeViewToBusy();

                        ParamsForSendTTProposal paramsForSendTTProposal = new ParamsForSendTTProposal();
                        onSendTTRequest(paramsForSendTTProposal, new AppCallback<List<Proposal>>(){
                            @Override
                            protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                                 changeViewToSuccess();

                            }

                            @Override
                            protected void onError(Call<List<Proposal>> call, Throwable t) {
                                MessageBox.show(t.getMessage(),context);
                            }
                        });
                    }
                }));
            }

            @Override
            protected View busyView() {
                return MakeDummy.backgroundColor(MakeDummy.textColor(Atom.button(context,"Sending request...",null), Color.parseColor("#aaaaaa")),Color.TRANSPARENT);
            }

            @Override
            protected View successView() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GotoActivity.myTripLatestActivities(context,new ParamsForGetTripActivities());
                    }
                }, 1000);

                return MakeDummy.linearLayoutVertical(
                        context,
                        MakeDummy.textView(context,"Request sent successfully"),
                        MakeDummy.linearLayoutHorizontal(
                                context,
                                Atom.button(context, "View Requests", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onViewTTRequests();
                                    }
                                }),
                                MakeDummy.verticalSeparator(context,Dp.normal()),
                                Atom.button(context, "Back", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onGoBack();
                                    }
                                })
                        )


                );

            }

            @Override
            protected View errorView() {
                return MakeDummy.textView(context,"Error occured");
            }
        };

        return multiStateNetworkButton.getView();

        /*
        //======================
        return MakeDummy.wrapContent(Atom.button(context, "Request Travel together", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsForSendTTRequest paramsForSendTTRequest = new ParamsForSendTTRequest();
                onSendTTRequest(paramsForSendTTRequest, new AppCallback<List<TTRequest>>(){
                    @Override
                    protected void onSuccess(Call<List<TTRequest>> call, Response<List<TTRequest>> response) {
                        MessageBox.show("Request sent",context);
                    }

                    @Override
                    protected void onError(Call<List<TTRequest>> call, Throwable t) {
                        MessageBox.show(t.getMessage(),context);
                    }
                });
            }
        }));*/
    }

    protected abstract void onViewTTRequests();

    protected abstract void onGoBack();

    protected abstract void onSendTTRequest(ParamsForSendTTProposal paramsForSendTTProposal, AppCallback<List<Proposal>> callbackForSendTTRequest);

    private View textArea() {
        View view = MakeDummy.linearLayoutHorizontal(
                context,
                MakeDummy.paddingRight(Atom.textViewPrimaryBold(context, "Joe Allen"), Dp.two_em()),
                MakeDummy.paddingRight(Atom.textViewPrimaryNormal(context, "3PM"), Dp.two_em())

        );
        MakeDummy.wrapContent(view);
        return view;
    }


}
