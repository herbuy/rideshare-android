package libraries.java;

import java.util.ArrayList;
import java.util.Arrays;

public class DoCapitalize {


    /**
     * returns true of the string has been affected in any way.
     */
    public static class Result {

        private String newValue;
        private String oldValue;

        public Result(String newValue, String oldValue) {
            this.newValue = newValue;
            this.oldValue = oldValue;
        }

        /** some times you dont want to invoke the next task but to just return the new value */
        public String getNewValue(){
            return newValue;
        }
        public String getOldValue(){
            return oldValue;
        }

        public final void then(NextTask nextTask) {
            if (nextTask == null) {
                return;
            }
            if (valueChanged()) {
                nextTask.ifChanged(newValue, oldValue);
            } else {
                nextTask.otherWise(oldValue);
            }
        }

        private boolean valueChanged() {
            return newValue != null && oldValue != null && !newValue.equals(oldValue);
        }

        /**
         * will be called after performing the transformation
         */
        public interface NextTask {
            /**
             * will be called if the string was changed
             */
            void ifChanged(String newValue, String oldValue);

            /**
             * will be called if nothing was changed. Can be useful if things stop changing/stabilize
             */
            void otherWise(String oldValue);

        }
    }

    private static String doCapitalizeFirstLetter(String originalString, boolean makeRestLowerCase) {
        if (originalString == null) {
            return originalString;
        }
        if (originalString.length() < 1) {
            return originalString;
        }

        if (originalString.length() == 1) {
            return originalString.toUpperCase();
        }

        return originalString.substring(0, 1).toUpperCase().concat(
                (
                        makeRestLowerCase ? originalString.substring(1).toLowerCase() :
                                originalString.substring(1)
                )
        );
    }

    public static Result eachCharacter(String originalString) {
        if (originalString == null) {
            return new Result(originalString, originalString);
        }
        if (originalString.length() < 1) {
            return new Result(originalString, originalString);
        }
        return new Result(originalString.toUpperCase(), originalString);

    }

    public static Result firstLetter(String originalString, boolean makeRestLowerCase) {
        return new Result(doCapitalizeFirstLetter(originalString, makeRestLowerCase), originalString);
    }

    public static Result eachWord(String originalString, boolean makeRestLowerCase) {
        if (originalString == null || originalString.length() < 1) {
            return new Result(originalString, originalString);
        }
        if (originalString.length() == 1) {
            return new Result(originalString.toUpperCase(), originalString);
        }

        //
        //while text has next char
        //start with capitalize on
        //if capitalize is on, then capitalize, turn off capitalize, and go to next
        //else if is space, then turn on capitalize and go to next
        //else if lowercase for other letters turned on, then make this lower case and go to next


        boolean capitalizeOn = true;
        char[] input = originalString.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            String currentChar = String.valueOf(input[i]);
            if (capitalizeOn) {
                builder.append(currentChar.toUpperCase());
                capitalizeOn = false;
            } else if (currentChar.matches("\\s+")) {
                builder.append(currentChar);
                capitalizeOn = true;

            } else if (makeRestLowerCase) {
                builder.append(currentChar.toLowerCase());
            } else {
                builder.append(currentChar);
            }

        }
        return new Result(builder.toString(),originalString);


        /*String[] words = originalString.split("\\s+");
        StringBuilder builder = new StringBuilder();
        String delimiter = "";

        if(words.length < 1){
            return firstLetter(originalString,makeRestLowerCase);
        }
        for(int i = 0; i < words.length;i++){
            builder.append(delimiter);
            builder.append(doCapitalizeFirstLetter(words[i],makeRestLowerCase));
            delimiter = " ";
        }


        return new Result(builder.toString(),originalString);*/
    }
}
