package libraries.english;

public class SingularPlural {
    private static String get(Integer count, String labelIfOne, String labelOtherWise, boolean attachNumber){
        if(count == 1){
            return String.format("%s%s", (attachNumber ? count.toString()+" " : ""), labelIfOne);
        }
        else{
            return String.format("%s%s", (attachNumber ? count.toString()+" " : ""), labelOtherWise);
        }
    }
    public static String getLabel(Integer count, String labelIfOne, String labelOtherWise){
        return get(count,labelIfOne,labelOtherWise,false);
    }

    public static String getNumberPlusLabel(Integer count, String labelIfOne, String labelOtherWise){
        return get(count,labelIfOne,labelOtherWise,true);
    }
}
