package libraries;


/** this library is useful for complex condition checking where multiple conditions have to be checked
 * and they can be combined in a complex way
 * such as checking that all conditions are true, but within a single conditions, you check that any is true, or only one true, or none true
 *
 * It make the code more readable and grouped than using the inbuilt operators that would make the code diffocult to read if the conditions being checked are many
 *
 *  */
public class IsTrue {
    public static boolean thatNull(Object object) {
        return null == object;
    }

    public static boolean thatNotNull(Object object) {
        return null != object;
    }

    public static boolean thatNullOrEmpty(String object) {
        return null == object || object.trim().equalsIgnoreCase("");
    }
    public static boolean thatNullOrEmptyForAny(String... objects){
        for(String obj : objects){
            if(IsTrue.thatNullOrEmpty(obj)){
                return true;
            }
        }
        return false;
    }

    public static boolean thatEqualsIgnoreCase(String object1, String object2) {
        return object1 != null && object2 != null && object1.equalsIgnoreCase(object2);
    }

    public static boolean forOneOnly(boolean... conditionList) {
        throwExceptionIfNoInput(conditionList);
        int totalTrues = 0;
        for (boolean condition : conditionList) {
            if (condition) {
                totalTrues += 1;
                if (totalTrues > 1) {
                    return false;
                }
            }
        }
        return totalTrues == 1;
    }

    public static boolean forAny(boolean... conditionList) {
        throwExceptionIfNoInput(conditionList);
        for (boolean condition : conditionList) {
            if (condition) {
                return true;
            }
        }
        return false;
    }

    public static boolean forNone(boolean... conditionList) {
        return !forAny(conditionList);
    }


    public static boolean forAll(boolean... conditionList) {
        throwExceptionIfNoInput(conditionList);
        for (boolean condition : conditionList) {
            if (!condition) {
                return false;
            }
        }
        return true;
    }

    private static void throwExceptionIfNoInput(boolean[] conditionList) {
        if (conditionList == null || conditionList.length == 0) {
            throw new RuntimeException("Conditions to evaluate not provided or is null or empty array");
        }

    }


}
