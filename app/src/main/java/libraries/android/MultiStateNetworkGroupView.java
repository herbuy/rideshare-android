package libraries.android;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import client.ui.libraries.HerbuyView;


public abstract class MultiStateNetworkGroupView implements HerbuyView {
    Context context;
    ViewGroup viewGroup;

    public MultiStateNetworkGroupView(Context context) {
        this.context = context;
        viewGroup = createViewGroup();
        refresh();

    }

    @Override
    public View getView() {
        return viewGroup;
    }

    private void changeViewTo(View view){
        viewGroup.removeAllViews();
        viewGroup.addView(view);
    }

    public void changeViewToBusy(){
        changeViewTo(getBusyView());
    }

    public void changeViewToErrorView(){
        changeViewTo(getErrorView());
    }



    public void changeViewToSucessView(){
        viewGroup.removeAllViews();
        List<View> successView = getSuccessView();
        for(View item: successView ){
            viewGroup.addView(item);
        }
    }


    protected abstract ViewGroup createViewGroup();
    protected abstract void refresh();
    protected abstract View getBusyView();
    protected abstract View getErrorView();
    protected abstract List<View> getSuccessView();


}
