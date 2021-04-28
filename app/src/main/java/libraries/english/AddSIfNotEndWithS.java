package libraries.english;

public class AddSIfNotEndWithS {
    public static String run(String text){
        if(text == null || text.trim().equalsIgnoreCase("")){
            return text;
        }

        text = text.trim();

        if(text.endsWith("s")){
            return text + "'";
        }
        return text + "'s";
    }
}
