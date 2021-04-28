package client.data;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skyvolt.jabber.R;

import java.util.Random;
import java.util.UUID;

import core.businessobjects.FamilyMessage;
import core.businessobjects.Gender;
import core.businessobjects.Location;
import core.businessobjects.MatePurpose;
import core.businessobjects.Subjects;
import core.businessobjects.TargetGroup;
import core.businessobjects.Trip;
import core.businessobjects.TripActivity;
import core.businessobjects.User;
import de.hdodenhof.circleimageview.CircleImageView;
import libraries.android.SquaredImageVIew;

public class DummyData {
    public static ImageView randomSquareImageView(Context context){
        ImageView imageView = new SquaredImageVIew(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(DummyData.randomProfilePicResource());
        return imageView;
    }

    public static CircleImageView randomCircleImageView(Context context){
        return randomCircleImageView(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static CircleImageView randomCircleImageView(Context context, int width){
        return randomCircleImageView(context, width, width);
    }
    public static CircleImageView randomCircleImageView(Context context, int width, int height){
        CircleImageView image = new CircleImageView(context);
        image.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageResource(DummyData.randomProfilePicResource());
        return image;
    }
    public static int randomProfilePicResource() {

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

    public static String randomName() {
        String[] availableNames = getAvailableUserNames();
        int indexOfChosenName = Math.abs(new Random().nextInt()) % availableNames.length;
        return availableNames[indexOfChosenName];
    }

    @NonNull
    private static String[] getAvailableUserNames() {
        //BufferedInputStream stream = new BufferedInputStream(new InputStreamReader(getRe))
        return new String[]{
                "Joe Bless",
                "Julius",
                "Amanda",
                "Ketra",
                "Lee",
                "Agaba"
        };
    }

    public static Trip randomTraveller(HerbuyApi herbuyApi) {
        Trip trip = new Trip();

        return trip;

    }

    public static String randomDate() {
        String year = randomYear();
        String month = randomMonth();
        String day = randomDay(month);

        return String.format("%s-%s-%s",year,month,day);
    }

    public static  String randomYear(){
        String[] years = new String[]{"2019","2020","2021"};
        int chosenIndex = Math.abs(new Random().nextInt()) % years.length;
        return years[chosenIndex];
    }

    public static  String randomMonth(){
        String[] months = arrayMerge(
                new String[]{"2"},
                arrayMerge(getOddMonths(), getEvenMonthsExceptFeb())
        );
        int chosenIndex = Math.abs(new Random().nextInt()) % months.length;
        return months[chosenIndex];
    }

    public static String randomDay(String month){

        if(contains(month,getOddMonths())){
            return Integer.valueOf((1 + Math.abs(new Random().nextInt()) % 30)).toString();
        }
        else if(contains(month,getEvenMonthsExceptFeb())){
            return Integer.valueOf((1 + Math.abs(new Random().nextInt()) % 31)).toString();
        }
        else{
            return Integer.valueOf((1 + Math.abs(new Random().nextInt()) % 28)).toString();
        }

    }

    public static String randomTime(){
        String hour = randomHour();
        String minute = randomMinute();
        String seconds = randomSeconds();
        return String.format("%s:%s:%s", hour, minute, seconds);
    }

    public static String randomHour(){
        return Integer.valueOf(Math.abs(new Random().nextInt()) % 24).toString();
    }

    public static String randomMinute(){
        return Integer.valueOf(Math.abs(new Random().nextInt()) % 60).toString();
    }

    public static String randomSeconds(){
        return Integer.valueOf(Math.abs(new Random().nextInt()) % 60).toString();
    }

    public static String randomAge(){
        return Integer.valueOf((Math.abs(new Random().nextInt()) % 80) + 18 ).toString();
    }

    public static  Gender randomGender(){
        Gender gender = new Gender();
        gender.setId(UUID.randomUUID().toString());
        gender.setDescription(randomGenderDescription());
        return gender;
    }

    public static String randomGenderDescription(){
        String[] genderDescriptions = getGenderDescriptions();
        int chosenIndex = Math.abs(new Random().nextInt()) % genderDescriptions.length;
        return genderDescriptions[chosenIndex];
    }

    public static String[] getGenderDescriptions(){
        return new String[]{"Male","Female"};
    }

    public static MatePurpose randomMatePurpose(){
        MatePurpose matePurpose = new MatePurpose();
        matePurpose.setId(UUID.randomUUID().toString());
        matePurpose.setDescription(randomMatePurposeDescription());
        return matePurpose;
    }

    public static String randomMatePurposeDescription(){
        String[] matePurposeDescriptions = getMatePurposeDescriptions();
        int chosenIndex = Math.abs(new Random().nextInt()) % matePurposeDescriptions.length;
        return matePurposeDescriptions[chosenIndex];

    }

    public static String[] getMatePurposeDescriptions(){
        return new String[]{"Share costs","Meet new people","Have group fun","Other reason"};
    }

    public static String randomBoolean(){
        String[] possibleValues = new String[]{"1","0"};
        int chosenIndex = Math.abs(new Random().nextInt()) % possibleValues.length;
        return possibleValues[chosenIndex];
    }

    public static TargetGroup randomTargetGroup(){
        TargetGroup targetGroup = new TargetGroup();
        targetGroup.setId(UUID.randomUUID().toString());
        targetGroup.setDescription(randomGroupName());
        return targetGroup;
    }

    public static String randomGroupName(){
        String[] groupNames = getGroupNames();
        int chosenIndex = Math.abs(new Random().nextInt()) % groupNames.length;
        return groupNames[chosenIndex];
    }

    public static String[] getGroupNames(){
        return new String[]{"Family","Friends","Work Mates"};
    }

    public static String randomLocationName(){
        String[] locationNames = getLocationNames();
        int chosenIndex = Math.abs(new Random().nextInt()) % locationNames.length;
        return locationNames[chosenIndex];
    }

    public static Location randomLocation(){
        Location location = new Location();
        location.setId(UUID.randomUUID().toString());
        location.setName(randomLocationName());
        return location;
    }

    public static String[] getLocationNames() {
        String[] locationNames = new String[]{
                "Kampala",
                "Fort Portal",
                "Soroti",
                "Mbale",
                "Arua",
                "Gulu"
        };
        return locationNames;
    }

    public static String randomSubject() {
        String[] supportedSubjectList =  Subjects.getArray();
        int indexOfChosenSubject = Math.abs(new Random().nextInt()) % supportedSubjectList.length;
        return supportedSubjectList[indexOfChosenSubject];
    }

    public static User randomUser() {
        User randomUser = new User();
        randomUser.setUserId(randomUUID());
        randomUser.setUserName(randomName());
        randomUser.setProfilePic(randomProfilePic());
        return randomUser;
    }

    public static String randomProfilePic() {

        return UUID.randomUUID().toString() + ".jpg";
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean contains(String item, String[] array){
        for(String itemInArray: array){
            if(itemInArray.trim().equalsIgnoreCase(item)){
                return true;
            }
        }
        return false;
    }

    public static String[] getOddMonths(){
        return new String[]{"1","3","5","7","9","11"};
    }

    public static String[] getEvenMonthsExceptFeb(){
        return new String[]{"4","6","8","10","12"};
    }

    public static String[] arrayMerge(String[] array1, String[] array2){
        String[] resultingList = new String[array1.length + array2.length];
        int counter = -1;
        for(String item : array1){
            counter+=1;
            resultingList[counter] = item;
        }
        for(String item : array2){
            counter+=1;
            resultingList[counter] = item;
        }
        return resultingList;
    }

    public static Integer randomInt(int min, int max) {
        int result = (int)(Math.random() * (max - min)) + min;
        return result;
    }

    public static TripActivity randomTripActivity() {
        String actorId = randomUUID();
        String targetId = randomUUID();
        String viewerId = randomInt(0,10) < 5 ? actorId : targetId;

        TripActivity tripActivity = new TripActivity(
                randomUUID(),
                randomTripActivityType(),
                randomDate(),
                randomTime(),
                randomUUID(),
                "TRIP",
                randomUUID(),
                actorId,
                randomName(),
                "",
                targetId,
                randomName(),
                "",
                viewerId

        );

        return tripActivity;
    }

    private static String randomTripActivityType() {
        String[] tripActivities = new String[]{
                //TripActivity.Type.Trip.RECIPIENT_SCHEDULED_TRIP,
                TripActivity.Type.Trip.ACTOR_SCHEDULED_TRIP,

                //TripActivity.Type.Trip.ACTOR_SENT_TT_REQUEST,
                //TripActivity.Type.Trip.RECIPIENT_SENT_TT_REQUEST,
        };
        int chosenIndex = Math.abs(new Random().nextInt()) % tripActivities.length;
        return tripActivities[chosenIndex];
    }

    public static String randomChoice(String... items) {
        int chosenIndex = Math.abs(new Random().nextInt()) % items.length;
        return items[chosenIndex];
    }

    public static FamilyMessage randomFamilyMessage() {
        FamilyMessage message = new FamilyMessage();
        message.setCorrelationId("");
        message.setFromUserId(randomInt(0, 10) < 5 ? LocalSession.instance().getUserId() : "");
        message.setFromUserName(randomName());
        message.setMessageType(FamilyMessage.Type.TEXT_PLAIN);
        message.setMessageText(randomChoice("Hello","How are you","How is Kampala","Am leaving 4pm","Where do I pick you","Lets meet at Cafe Javas"));
        return message;
    }
}
