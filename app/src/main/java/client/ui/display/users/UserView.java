package client.ui.display.users;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import core.businessobjects.User;
import client.data.DummyData;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;

public class UserView implements HerbuyView {
    private Context context;
    private User item;

    public UserView(Context context, User user) {
        this.context = context;
        this.item = user;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public View getView() {
        View linearLayout = MakeDummy.linearLayoutVertical(
                getContext(),
                image(item),
                name(item)

        );

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.userProfile(context,item);
            }
        });

        return linearLayout;
    }


    private TextView name(User item) {
        return MakeDummy.textView(getContext(), item.getUserName());
    }

    private ImageView image(User item) {
        ImageView imageView = new SquaredImageVIew(getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(DummyData.randomProfilePicResource());
        return imageView;
    }
}
