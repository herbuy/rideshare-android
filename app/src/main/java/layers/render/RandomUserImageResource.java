package layers.render;

import com.skyvolt.jabber.R;

import java.util.Random;

public class RandomUserImageResource {

    public static int select() {
        int[] userImages = new int[]{
                R.drawable.user_1,
                R.drawable.user_2,
                R.drawable.user_3,
                R.drawable.user_4,
                R.drawable.user_5,
                R.drawable.user_6,
                R.drawable.user_7,
                R.drawable.user_8,
                R.drawable.user_9,
                R.drawable.user_10,
                R.drawable.user_11,
        };

        int indexOfChosenImage = Math.abs(new Random().nextInt()) % userImages.length;
        return userImages[indexOfChosenImage];

    }
}
