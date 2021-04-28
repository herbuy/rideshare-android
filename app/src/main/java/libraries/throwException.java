package libraries;

import java.util.List;

public class throwException {

    public static void saying(String message) {
        throw new RuntimeException(message);
    }

    public static void ifTrue(boolean condition, String message) {
        if (condition) {
            throw new RuntimeException(message);
        }
    }

    public static void ifNot(boolean condition, String message) {
        ifTrue(!condition, message);
    }

    public static void ifNull(Object object, String message) {
        throwException.ifTrue(null == object, message);
    }

    public static void ifNotNull(Object object, String message) {
        throwException.ifTrue(null != object, message);
    }

    public static void ifNullOrEmpty(String object, String message) {
        throwException.ifTrue(null == object || object.trim().equalsIgnoreCase(""), message);
    }
    public static void ifNotNullOrEmpty(String object, String message){
        throwException.ifNot(null == object || object.trim().equalsIgnoreCase(""), message);
    }

    public static void ifNotInList(String item, List<String> list, String message) {
        if(item != null){
            item = item.trim();
            for(String foundItem: list){
                if(foundItem != null){
                    foundItem = foundItem.trim();
                    if(foundItem.equalsIgnoreCase(item)){
                        return;
                    }
                }
            }
        }
        throwException.saying(message);

    }
}
